package aston.sorting.service.sorting.evenodd;

import aston.sorting.service.sorting.SortingStrategy;
import java.util.Comparator;

/**
 * Modified SelectionSort that sorts objects by a numeric field:
 * - Objects with even values are sorted in natural order
 * - Objects with odd values remain in their original positions
 */
public class EvenOddSelectionSortStrategy<T> implements SortingStrategy<T> {
    private final NumericFieldExtractor<T> fieldExtractor;

    public EvenOddSelectionSortStrategy(NumericFieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            // Skip odd elements
            if (!isEven(array[i])) {
                continue;
            }

            int minIndex = i;
            // Find minimum even element
            for (int j = i + 1; j < length; j++) {
                if (isEven(array[j]) && comparator.compare(array[j], array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            
            if (minIndex != i) {
                T temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    private boolean isEven(T element) {
        if (element == null) {
            return false;
        }
        double value = fieldExtractor.extractValue(element);
        return ((int) value) % 2 == 0;
    }
}

