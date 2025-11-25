package aston.sorting.service.counter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElementCounter {

    private ElementCounter() {
        // Конструктор приватный, чтобы предотвратить создание экземпляров класса
    }

    /**
     * Подсчитывает количество вхождений указанного элемента в коллекцию, используя многопоточность.
     *
     * @param <T>        тип элементов в коллекции
     * @param collection коллекция для поиска
     * @param element    элемент для подсчета
     * @return количество вхождений элемента в коллекции
     */
    public static <T> int countOccurrencesMultiThreaded(Collection<T> collection, T element) {
        return countOccurrencesMultiThreaded(collection, element, 
                Runtime.getRuntime().availableProcessors());
    }

    /**
     * Подсчитывает количество вхождений указанного элемента в коллекцию, используя указанное
     * количество потоков.
     *
     * @param <T>        тип элементов в коллекции
     * @param collection коллекция для поиска
     * @param element    элемент для подсчета
     * @param threads    число потоков для использования
     * @return количество вхождений элемента в коллекции
     */
    public static <T> int countOccurrencesMultiThreaded(
            Collection<T> collection, T element, int threads) {
        if (collection == null || collection.isEmpty()) {
            return 0;
        }

        // Ограничиваем количество потоков размером коллекции и доступными процессорами
        int actualThreads = Math.min(threads, collection.size());
        actualThreads = Math.min(actualThreads, Runtime.getRuntime().availableProcessors());

        if (actualThreads <= 1) {
            // Выполняем в однопоточном режиме если только 1 поток
            return countElementOccurrences(collection, element);
        }

        // Преобразуем коллекцию в список для доступа по индексу
        List<T> elements = collection.stream().toList();
        int collectionSize = elements.size();
        int partSize = collectionSize / actualThreads;
        
        ExecutorService executor = Executors.newFixedThreadPool(actualThreads);
        CountDownLatch latch = new CountDownLatch(actualThreads);
        AtomicInteger totalCount = new AtomicInteger(0);

        // Запускаем задачи для каждого потока
        for (int i = 0; i < actualThreads; i++) {
            final int startIndex = i * partSize;
            final int endIndex;
            
            // Последнему потоку отдаем все оставшиеся элементы
            if (i == actualThreads - 1) {
                endIndex = collectionSize;
            } else {
                endIndex = startIndex + partSize;
            }

            executor.execute(() -> {
                try {
                    int count = 0;
                    for (int j = startIndex; j < endIndex; j++) {
                        if (Objects.equals(elements.get(j), element)) {
                            count++;
                        }
                    }
                    totalCount.addAndGet(count);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            // Ждем завершения всех потоков
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Прерывание при ожидании завершения потоков: {}", e.getMessage());
        } finally {
            executor.shutdown();
        }

        int result = totalCount.get();
        log.info("Найдено {} вхождений элемента '{}' в коллекции", result, element);
        return result;
    }

    /**
     * Однопоточный метод для подсчета вхождений элемента в коллекцию.
     *
     * @param <T>        тип элементов в коллекции
     * @param collection коллекция для поиска
     * @param element    элемент для подсчета
     * @return количество вхождений элемента
     */
    private static <T> int countElementOccurrences(Collection<T> collection, T element) {
        int count = 0;
        for (T item : collection) {
            if (Objects.equals(item, element)) {
                count++;
            }
        }
        log.info("Найдено {} вхождений элемента '{}' в коллекции (однопоточный режим)", 
                count, element);
        return count;
    }
}