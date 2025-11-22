package aston.sorting.evenodd;

import aston.sorting.service.sorting.SortingStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Modified InsertionSort that sorts objects by a numeric field:
 * - Objects with even values are sorted in natural order
 * - Objects with odd values remain in their original positions
 */
public class EvenOddInsertionSortStrategy<T> implements SortingStrategy<T> {
    private final NumericFieldExtractor<T> fieldExtractor;

    public EvenOddInsertionSortStrategy(NumericFieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return;
        }

        // First collect all even elements
        List<T> evenElements = new ArrayList<>();
        for (T element : array) {
            if (element != null && isEven(element)) {
                evenElements.add(element);
            }
        }
        
        // Sort the even values
        evenElements.sort(comparator);
        
        // Put them back in the original array in order
        int evenIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && isEven(array[i])) {
                array[i] = evenElements.get(evenIndex++);
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