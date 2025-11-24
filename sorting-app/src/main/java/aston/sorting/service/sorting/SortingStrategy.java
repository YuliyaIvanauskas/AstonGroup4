package aston.sorting.service.sorting;

import java.util.Comparator;

public interface SortingStrategy <T>{
    void sort(T[] array, Comparator<T> comparator);
}