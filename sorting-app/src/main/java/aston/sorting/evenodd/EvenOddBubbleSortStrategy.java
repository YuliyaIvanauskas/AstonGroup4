package aston.sorting.evenodd;

import aston.sorting.service.sorting.SortingStrategy;
import java.util.Comparator;

/**
 * Modified BubbleSort that sorts objects by a numeric field:
 * - Objects with even values are sorted in natural order
 * - Objects with odd values remain in their original positions
 */
public class EvenOddBubbleSortStrategy<T> implements SortingStrategy<T> {
    private final NumericFieldExtractor<T> fieldExtractor;

    public EvenOddBubbleSortStrategy(NumericFieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return;
        }

        // Extract even elements with their positions, sort them using BubbleSort, then place back
        java.util.List<T> evenElements = new java.util.ArrayList<>();
        java.util.List<Integer> evenPositions = new java.util.ArrayList<>();
        
        for (int i = 0; i < array.length; i++) {
            if (isEven(array[i])) {
                evenElements.add(array[i]);
                evenPositions.add(i);
            }
        }
        
        // Sort even elements using BubbleSort algorithm
        if (!evenElements.isEmpty()) {
            bubbleSort(evenElements, comparator);
            
            // Place sorted even elements back at their original positions
            for (int i = 0; i < evenElements.size(); i++) {
                array[evenPositions.get(i)] = evenElements.get(i);
            }
        }
        // Odd elements remain unchanged in their original positions
    }

    private void bubbleSort(java.util.List<T> list, Comparator<T> comparator) {
        int size = list.size();
        boolean flag;
        
        for (int i = 0; i < size - 1; i++) {
            flag = false;
            for (int j = 0; j < size - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    flag = true;
                }
            }
            if (!flag) {
                break;
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

