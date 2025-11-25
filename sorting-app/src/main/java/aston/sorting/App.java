package aston.sorting;

import aston.sorting.model.Student;
import aston.sorting.service.provider.DataProvider;
import aston.sorting.service.provider.DataProviderFactory;
import aston.sorting.service.provider.DataProviderFactory.DataSourceType;
import aston.sorting.service.provider.FileDataProvider;
import aston.sorting.service.sorting.BubbleSortStrategy;
import aston.sorting.service.sorting.SortingStrategy;
import aston.sorting.service.sorting.students.StudentSortField;
import aston.sorting.service.sorting.students.StudentSorter;
import aston.sorting.service.writer.SortedCollectionWriter;
import aston.sorting.service.sorting.evenodd.EvenOddBubbleSortStrategy;
import aston.sorting.service.sorting.evenodd.EvenOddInsertionSortStrategy;
import aston.sorting.service.sorting.evenodd.EvenOddMergeSortStrategy;
import aston.sorting.service.sorting.evenodd.EvenOddQuickSortStrategy;
import aston.sorting.service.sorting.evenodd.EvenOddSelectionSortStrategy;
import aston.sorting.service.sorting.evenodd.NumericFieldExtractor;
import aston.sorting.service.counter.ElementCounter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public final class App {
    private final Scanner scanner;
    private Student[] sortedStudents; // Field to store sorted students

    private App() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("=== Программа сортировки студентов ===");
        boolean working = true;

        while (working) {
            displayMainMenu();
            int choice = getIntInput("Выберите действие: ", 0, 5);

            switch (choice) {
                case 1 -> processSortingFlow();
                case 2 -> viewSortAlgorithmInfo();
                case 3 -> writeSortedCollectionToFile();
                case 4 -> countElementOccurrences();
                case 5 -> viewHelp();
                case 0 -> working = false;
                default -> System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
        System.out.println("Программа завершена.");
    }

    private void displayMainMenu() {
        System.out.println("\nГлавное меню:");
        System.out.println("1. Сортировка студентов");
        System.out.println("2. Информация об алгоритмах сортировки");
        System.out.println("3. Запись отсортированной коллекции в файл");
        System.out.println("4. Подсчет вхождений элемента");
        System.out.println("5. Справка");
        System.out.println("0. Выход");
    }

    private void processSortingFlow() {
        // Выбор алгоритма сортировки
        SortingStrategy<Student> sortingStrategy = selectSortingStrategy();
        if (sortingStrategy == null) return;
    
        // Проверка, является ли алгоритм сортировки четно-нечетным
        boolean isEvenOddSorting = sortingStrategy instanceof EvenOddBubbleSortStrategy
                || sortingStrategy instanceof EvenOddSelectionSortStrategy
                || sortingStrategy instanceof EvenOddInsertionSortStrategy
                || sortingStrategy instanceof EvenOddQuickSortStrategy
                || sortingStrategy instanceof EvenOddMergeSortStrategy;
    
        // Для четно-нечетных алгоритмов используем специальный файл
        DataProvider dataProvider;
        List<Student> studentList;
    
        if (isEvenOddSorting) {
            // Используем специальный файл с целыми значениями среднего балла
            System.out.println("\nИспользуется файл students_integer_grades.txt с целыми значениями среднего балла");
            dataProvider = new FileDataProvider() {
                @Override
                protected String getFilePath() {
                    return "students_integer_grades.txt";
                }
            };
            // Выбор размера массива
            int arraySize = getIntInput("Введите размер массива (1-100): ", 1, 100);
            studentList = dataProvider.provideData(arraySize);            
        } else {
            // Для обычных алгоритмов - стандартный процесс
            // Выбор способа заполнения данных
            DataSourceType dataSourceType = selectDataSourceType();
            if (dataSourceType == null) return;
    
            // Выбор размера массива
            int arraySize = getIntInput("Введите размер массива (1-100): ", 1, 100);
    
            // Получение данных через выбранный провайдер
            dataProvider = DataProviderFactory.createProvider(dataSourceType);
            studentList = dataProvider.provideData(arraySize);
        }
    
        if (studentList.isEmpty()) {
            System.out.println("Не удалось получить данные. Возврат в главное меню.");
            return;
        }
    
        // Преобразование списка в массив для сортировки
        Student[] students = studentList.toArray(new Student[0]);
        System.out.println("\nПолучен массив студентов размером " + students.length);
    
        // Для обычных алгоритмов - выбор поля сортировки, для четно-нечетных - используем средний балл
        StudentSortField sortField;
        if (isEvenOddSorting) {
            System.out.println("Для четно-нечетного алгоритма используется поле 'Средний балл'");
            sortField = StudentSortField.AVERAGE_GRADE;
        } else {
            // Выбор поля для сортировки
            sortField = selectSortField();
            if (sortField == null) return;
        }
        System.out.println("\nДо сортировки:");
        Arrays.stream(students).forEach(System.out::println);
        // Сортировка
        StudentSorter sorter = new StudentSorter(sortingStrategy);
        sorter.sort(students, sortField);
    
        // Вывод результата
        System.out.println("\nРезультат сортировки:");
        Arrays.stream(students).forEach(System.out::println);
        
        // Сохраняем отсортированный массив для возможной записи в файл
        this.sortedStudents = students;
    }

    private DataSourceType selectDataSourceType() {
        System.out.println("\nВыберите способ заполнения массива:");
        System.out.println("1. Из файла");
        System.out.println("2. Случайные данные");
        System.out.println("3. Ввод вручную");
        System.out.println("0. Вернуться в меню");

        int choice = getIntInput("Ваш выбор: ", 0, 3);
        return switch (choice) {
            case 1 -> DataSourceType.FILE;
            case 2 -> DataSourceType.RANDOM;
            case 3 -> DataSourceType.MANUAL;
            default -> null;
        };
    }

    private SortingStrategy<Student> selectSortingStrategy() {
        System.out.println("\nВыберите алгоритм сортировки:");
        System.out.println("1. Пузырьковая сортировка");
        System.out.println("2. Пузырьковая сортировка (четные/нечетные)");
        System.out.println("3. Сортировка выбором (четные/нечетные)");
        System.out.println("4. Сортировка вставками (четные/нечетные)");
        System.out.println("5. Быстрая сортировка (четные/нечетные)");
        System.out.println("6. Сортировка слиянием (четные/нечетные)");
        System.out.println("0. Вернуться в меню");
    
        int choice = getIntInput("Ваш выбор: ", 0, 10);
        return switch (choice) {
            case 1 -> new BubbleSortStrategy<>();
            case 2 -> {
                NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
                yield new EvenOddBubbleSortStrategy<>(extractor);
            }
            case 3 -> {
                NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
                yield new EvenOddSelectionSortStrategy<>(extractor);
            }
            case 4 -> {
                NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
                yield new EvenOddInsertionSortStrategy<>(extractor);
            }
            case 5 -> {
                NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
                yield new EvenOddQuickSortStrategy<>(extractor);
            }
            case 6 -> {
                NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
                yield new EvenOddMergeSortStrategy<>(extractor);
            }
            default -> null;
        };
    }

    private StudentSortField selectSortField() {
        System.out.println("\nВыберите поле для сортировки:");
        System.out.println("1. По номеру группы");
        System.out.println("2. По среднему баллу");
        System.out.println("3. По номеру зачетной книжки");
        System.out.println("0. Вернуться в меню");

        int choice = getIntInput("Ваш выбор: ", 0, 3);
        return switch (choice) {
            case 1 -> StudentSortField.GROUP_NUMBER;
            case 2 -> StudentSortField.AVERAGE_GRADE;
            case 3 -> StudentSortField.RECORD_BOOK_NUMBER;
            default -> null;
        };
    }

    private void viewSortAlgorithmInfo() {
        System.out.println("\nИнформация об алгоритмах сортировки:");
        System.out.println("1. Пузырьковая сортировка (Bubble Sort)");
        System.out.println("\nАлгоритмы четно-нечетной сортировки:");
        System.out.println("2-6. Четно-нечетные алгоритмы - сортируют только элементы с четными значениями числового поля,");
        System.out.println("     оставляя элементы с нечетными значениями на исходных позициях.");
    }

    private void writeSortedCollectionToFile() {
        if (sortedStudents == null || sortedStudents.length == 0) {
            System.out.println("\nСначала необходимо выполнить сортировку студентов (пункт 1).");
            return;
        }
    
        System.out.println("\nЗапись отсортированной коллекции в файл:");
        System.out.print("Введите имя файла для сохранения: ");
        String fileName = scanner.nextLine();
    
        List<Student> studentList = Arrays.asList(sortedStudents);
        SortedCollectionWriter.writeSortedCollectionToFile(studentList, fileName+".txt");
    
        System.out.println("Коллекция успешно записана в файл: " + fileName);
    }

    private void countElementOccurrences() {
        if (sortedStudents == null || sortedStudents.length == 0) {
            System.out.println("\nСначала необходимо выполнить сортировку студентов (пункт 1).");
            return;
        }
    
        System.out.println("\nПодсчет студентов с одинаковым средним баллом:");
        System.out.print("Введите средний балл для поиска: ");
    
        double searchGrade = -1;
        try {
            searchGrade = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода. Пожалуйста, введите корректное число.");
            return;
        }
    
        // Создаем коллекции для работы с ElementCounter
        final double finalSearchGrade = searchGrade;
    
        // Создаем коллекцию с средними баллами
        List<Double> grades = new ArrayList<>();
        for (Student student : sortedStudents) {
            grades.add(student.getAverageGrade());
        }
    
        // Создаем коллекцию студентов с заданным средним баллом
        List<Student> studentsWithSearchGrade = new ArrayList<>();
        for (Student student : sortedStudents) {
            if (Math.abs(student.getAverageGrade() - finalSearchGrade) < 0.0001) {
                studentsWithSearchGrade.add(student);
            }
        }
    
        // Используем ElementCounter для подсчета вхождений
        int count = ElementCounter.countOccurrencesMultiThreaded(
                grades, 
                finalSearchGrade);
    
        if (!studentsWithSearchGrade.isEmpty()) {
            System.out.println("\nСтуденты с средним баллом " + searchGrade + ":");
            studentsWithSearchGrade.forEach(System.out::println);
            System.out.println("\nВсего найдено студентов: " + studentsWithSearchGrade.size() + 
                           "\nКоличество вхождений " + searchGrade + 
                           " по данным ElementCounter: " + count);
        } else {
            System.out.println("Студентов с средним баллом " + searchGrade + " не найдено.");
        }
    }

    private void viewHelp() {
        System.out.println("\nСправка по использованию программы:");
        System.out.println("Эта программа позволяет сортировать объекты класса Student разными алгоритмами.");
        System.out.println("1. Для сортировки выберите пункт 1 в главном меню.");
        System.out.println("2. Выберите способ заполнения массива (из файла, случайно или вручную).");
        System.out.println("3. Укажите размер массива студентов.");
        System.out.println("4. Выберите алгоритм сортировки.");
        System.out.println("5. Выберите поле, по которому нужно отсортировать студентов.");
    }

    private int getIntInput(String prompt, int min, int max) {
        int input = min - 1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            try {
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                    if (input >= min && input <= max) {
                        valid = true;
                    } else {
                        System.out.println("Введите число от " + min + " до " + max);
                    }
                } else {
                    System.out.println("Пожалуйста, введите целое число.");
                    scanner.next(); // очистка буфера
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
                scanner.nextLine(); // очистка буфера
            }
        }
        scanner.nextLine(); // очистка буфера после nextInt()
        return input;
    }
}
