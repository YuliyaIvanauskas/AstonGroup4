package aston.sorting.evenodd;

import aston.sorting.service.sorting.SortingStrategy;
import java.util.Comparator;

/**
 * Modified QuickSort that sorts objects by a numeric field:
 * - Objects with even values are sorted in natural order
 * - Objects with odd values remain in their original positions
 */
public class EvenOddQuickSortStrategy<T> implements SortingStrategy<T> {
    private final NumericFieldExtractor<T> fieldExtractor;

    public EvenOddQuickSortStrategy(NumericFieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        // Extract even elements with their positions, sort them using QuickSort, then place back
        java.util.List<T> evenElements = new java.util.ArrayList<>();
        java.util.List<Integer> evenPositions = new java.util.ArrayList<>();
        
        for (int i = 0; i < array.length; i++) {
            if (isEven(array[i])) {
                evenElements.add(array[i]);
                evenPositions.add(i);
            }
        }
        
        // Sort even elements using QuickSort algorithm
        if (!evenElements.isEmpty()) {
            @SuppressWarnings("unchecked")
            T[] evenArray = evenElements.toArray((T[]) new Object[evenElements.size()]);
            quickSort(evenArray, 0, evenArray.length - 1, comparator);
            
            // Place sorted even elements back at their original positions
            for (int i = 0; i < evenArray.length; i++) {
                array[evenPositions.get(i)] = evenArray[i];
            }
        }
        // Odd elements remain unchanged in their original positions
    }

    private void quickSort(T[] array, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, comparator);
            quickSort(array, low, pivotIndex - 1, comparator);
            quickSort(array, pivotIndex + 1, high, comparator);
        }
    }

    private int partition(T[] array, int low, int high, Comparator<T> comparator) {
        T pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private boolean isEven(T element) {
        if (element == null) {
            return false;
        }
        double value = fieldExtractor.extractValue(element);
        return ((int) value) % 2 == 0;
    }
}

