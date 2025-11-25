package aston.sorting.service.sorting;

import java.util.Comparator;

public class InsertionSortStrategy<T> implements SortingStrategy<T> {

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;
        for (int i = 1; i < length; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(array[j], key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}

