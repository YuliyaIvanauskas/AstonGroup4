package aston.sorting.strategy;

import aston.sorting.StudentSortField;
import aston.sorting.StudentSorter;
import aston.sorting.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortStrategyTest {

    private BubbleSortStrategy<Integer> integerSortStrategy;
    private BubbleSortStrategy<String> stringSortStrategy;

    @BeforeEach
    void setUp() {
        integerSortStrategy = new BubbleSortStrategy<>();
        stringSortStrategy = new BubbleSortStrategy<>();
    }

    @Test
    void bubbleSortOrdersByGroupNumber() {
        Student[] students = new Student[]{
            Student.builder().groupNumber("A5").averageGrade(7.2).recordBookNumber("RB1234").build(),
            Student.builder().groupNumber("Z1").averageGrade(8.1).recordBookNumber("RB1235").build(),
            Student.builder().groupNumber("C3").averageGrade(6.5).recordBookNumber("RB1236").build()
        };
        StudentSorter studentSorter = new StudentSorter(new BubbleSortStrategy<>());
        studentSorter.sort(students, StudentSortField.GROUP_NUMBER);
        assertEquals("A5", students[0].getGroupNumber());
        assertEquals("C3", students[1].getGroupNumber());
        assertEquals("Z1", students[2].getGroupNumber());
    }

    @Test
    void sort_emptyArray_shouldRemainEmpty() {
        // Подготовка
        Integer[] array = new Integer[0];
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertEquals(0, array.length);
    }

    @Test
    void sort_nullArray_shouldNotThrowException() {
        // Выполнение и проверка
        assertDoesNotThrow(() -> integerSortStrategy.sort(null, Integer::compareTo));
    }

    @Test
    void sort_singleElement_shouldRemainSame() {
        // Подготовка
        Integer[] array = new Integer[]{5};
        Integer[] expected = new Integer[]{5};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_integersAscending_shouldSortCorrectly() {
        // Подготовка
        Integer[] array = new Integer[]{5, 2, 9, 1, 7};
        Integer[] expected = new Integer[]{1, 2, 5, 7, 9};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_integersDescending_shouldSortCorrectly() {
        // Подготовка
        Integer[] array = new Integer[]{5, 2, 9, 1, 7};
        Integer[] expected = new Integer[]{9, 7, 5, 2, 1};
        
        // Выполнение
        integerSortStrategy.sort(array, Comparator.reverseOrder());
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_alreadySortedArray_shouldRemainSorted() {
        // Подготовка
        Integer[] array = new Integer[]{1, 2, 3, 4, 5};
        Integer[] expected = new Integer[]{1, 2, 3, 4, 5};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_reverseSortedArray_shouldSortCorrectly() {
        // Подготовка
        Integer[] array = new Integer[]{5, 4, 3, 2, 1};
        Integer[] expected = new Integer[]{1, 2, 3, 4, 5};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_duplicateElements_shouldSortCorrectly() {
        // Подготовка
        Integer[] array = new Integer[]{5, 2, 9, 5, 2, 1, 9};
        Integer[] expected = new Integer[]{1, 2, 2, 5, 5, 9, 9};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_strings_shouldSortAlphabetically() {
        // Подготовка
        String[] array = new String[]{"banana", "apple", "orange", "grape"};
        String[] expected = new String[]{"apple", "banana", "grape", "orange"};
        
        // Выполнение
        stringSortStrategy.sort(array, String::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_stringsIgnoreCase_shouldSortCorrectly() {
        // Подготовка
        String[] array = new String[]{"Banana", "apple", "Orange", "grape"};
        String[] expected = new String[]{"apple", "Banana", "grape", "Orange"};
        
        // Выполнение
        stringSortStrategy.sort(array, String::compareToIgnoreCase);
        
        // Проверка
        assertArrayEquals(expected, array);
    }
}