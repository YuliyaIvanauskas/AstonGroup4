package aston.sorting.service.sorting.evenodd;

import aston.sorting.service.sorting.SortingStrategy;
import java.util.Comparator;

/**
 * Modified MergeSort that sorts objects by a numeric field:
 * - Objects with even values are sorted in natural order
 * - Objects with odd values remain in their original positions
 */
public class EvenOddMergeSortStrategy<T> implements SortingStrategy<T> {
    private final NumericFieldExtractor<T> fieldExtractor;

    public EvenOddMergeSortStrategy(NumericFieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        // Extract even elements, sort them, then place back at their original positions
        java.util.List<T> evenElements = new java.util.ArrayList<>();
        java.util.List<Integer> evenPositions = new java.util.ArrayList<>();
        
        for (int i = 0; i < array.length; i++) {
            if (isEven(array[i])) {
                evenElements.add(array[i]);
                evenPositions.add(i);
            }
        }
        
        // Sort even elements by their values
        evenElements.sort(comparator);
        
        // Place sorted even elements back at their original positions
        for (int i = 0; i < evenElements.size(); i++) {
            array[evenPositions.get(i)] = evenElements.get(i);
        }
        // Odd elements remain unchanged in their original positions
    }

    private boolean isEven(T element) {
        if (element == null) {
            return false;
        }
        double value = fieldExtractor.extractValue(element);
        return ((int) value) % 2 == 0;
    }
}

