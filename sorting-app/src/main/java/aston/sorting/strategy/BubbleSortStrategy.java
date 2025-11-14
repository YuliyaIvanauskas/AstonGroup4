package aston.sorting.strategy;

import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortingStrategy<T>
{

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if(array.equals(null) || array.length <= 1)
            return;

        int lenght = array.length;
        boolean flag;

        for(int i = 0; i < lenght-1; i++)
        {
            flag = false;
            for (int j = 0; j < lenght - i - 1; j++)
            {
                if(comparator.compare(array[j], array[j+1]) > 0)
                {
                    T temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                    flag = true;
                }
            }

            if(!flag)
                break;
        }
    }

}