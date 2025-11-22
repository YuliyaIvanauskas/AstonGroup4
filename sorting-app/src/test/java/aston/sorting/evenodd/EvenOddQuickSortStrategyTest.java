package aston.sorting.evenodd;

import aston.sorting.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddQuickSortStrategyTest {

    private EvenOddQuickSortStrategy<Integer> integerSortStrategy;
    private EvenOddQuickSortStrategy<Student> studentSortStrategy;

    @BeforeEach
    void setUp() {
        integerSortStrategy = new EvenOddQuickSortStrategy<>(x -> (double) x);
        studentSortStrategy = new EvenOddQuickSortStrategy<>(Student::getAverageGrade);
    }

    @Test
    void sort_emptyArray_shouldRemainEmpty() {
        Integer[] array = new Integer[0];
        integerSortStrategy.sort(array, Integer::compareTo);
        assertEquals(0, array.length);
    }

    @Test
    void sort_nullArray_shouldNotThrowException() {
        assertDoesNotThrow(() -> integerSortStrategy.sort(null, Integer::compareTo));
    }

    @Test
    void sort_singleElement_shouldRemainSame() {
        Integer[] array = new Integer[]{5};
        Integer[] expected = new Integer[]{5};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_allEvenValues_shouldSortCorrectly() {
        Integer[] array = new Integer[]{8, 2, 6, 4, 10};
        Integer[] expected = new Integer[]{2, 4, 6, 8, 10};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_allOddValues_shouldRemainUnchanged() {
        Integer[] array = new Integer[]{5, 3, 9, 1, 7};
        Integer[] expected = new Integer[]{5, 3, 9, 1, 7};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_mixedEvenAndOdd_evenShouldSort_oddShouldRemain() {
        Integer[] array = new Integer[]{8, 3, 6, 5, 4, 7};
        Integer[] expected = new Integer[]{4, 3, 6, 5, 8, 7};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
        assertEquals(3, array[1]);
        assertEquals(5, array[3]);
        assertEquals(7, array[5]);
    }

    @Test
    void sort_mixedEvenAndOdd_complexCase() {
        Integer[] array = new Integer[]{10, 1, 8, 3, 2, 9, 6};
        Integer[] expected = new Integer[]{2, 1, 6, 3, 8, 9, 10};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
        assertEquals(1, array[1]);
        assertEquals(3, array[3]);
        assertEquals(9, array[5]);
    }

    @Test
    void sort_evenValuesAlreadySorted_shouldRemainSorted() {
        Integer[] array = new Integer[]{2, 5, 4, 7, 6, 9};
        Integer[] expected = new Integer[]{2, 5, 4, 7, 6, 9};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_evenValuesReverseOrder_shouldSortCorrectly() {
        Integer[] array = new Integer[]{10, 3, 8, 5, 6, 7, 2};
        Integer[] expected = new Integer[]{2, 3, 6, 5, 8, 7, 10};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_withStudents_evenAverageGrades_shouldSort() {
        Student[] students = new Student[]{
            Student.builder().groupNumber("A1").averageGrade(8.0).recordBookNumber("RB1456").build(),
            Student.builder().groupNumber("A2").averageGrade(4.0).recordBookNumber("RB2567").build(),
            Student.builder().groupNumber("A3").averageGrade(6.0).recordBookNumber("RB3678").build()
        };
        
        studentSortStrategy.sort(students, Comparator.comparing(Student::getAverageGrade));
        
        assertEquals(4.0, students[0].getAverageGrade());
        assertEquals(6.0, students[1].getAverageGrade());
        assertEquals(8.0, students[2].getAverageGrade());
    }

    @Test
    void sort_withStudents_oddAverageGrades_shouldRemainUnchanged() {
        Student student1 = Student.builder().groupNumber("A1").averageGrade(7.0).recordBookNumber("RB1789").build();
        Student student2 = Student.builder().groupNumber("A2").averageGrade(5.0).recordBookNumber("RB2678").build();
        Student student3 = Student.builder().groupNumber("A3").averageGrade(9.0).recordBookNumber("RB3678").build();
        Student[] students = new Student[]{student1, student2, student3};
        Student[] expected = new Student[]{student1, student2, student3};
        
        studentSortStrategy.sort(students, Comparator.comparing(Student::getAverageGrade));
        
        assertArrayEquals(expected, students);
        assertEquals(7.0, students[0].getAverageGrade());
        assertEquals(5.0, students[1].getAverageGrade());
        assertEquals(9.0, students[2].getAverageGrade());
    }

    @Test
    void sort_withStudents_mixedEvenAndOdd_shouldSortEvenOnly() {
        Student student1 = Student.builder().groupNumber("A1").averageGrade(8.0).recordBookNumber("RB1767").build();
        Student student2 = Student.builder().groupNumber("A2").averageGrade(5.0).recordBookNumber("RB2777").build();
        Student student3 = Student.builder().groupNumber("A3").averageGrade(6.0).recordBookNumber("RB3778").build();
        Student student4 = Student.builder().groupNumber("A4").averageGrade(7.0).recordBookNumber("RB4677").build();
        Student student5 = Student.builder().groupNumber("A5").averageGrade(4.0).recordBookNumber("RB5455").build();
        
        Student[] students = new Student[]{student1, student2, student3, student4, student5};
        
        studentSortStrategy.sort(students, Comparator.comparing(Student::getAverageGrade));
        
        assertEquals(4.0, students[0].getAverageGrade());
        assertEquals(5.0, students[1].getAverageGrade());
        assertEquals(6.0, students[2].getAverageGrade());
        assertEquals(7.0, students[3].getAverageGrade());
        assertEquals(8.0, students[4].getAverageGrade());
    }

    @Test
    void sort_duplicateEvenValues_shouldSortCorrectly() {
        Integer[] array = new Integer[]{8, 3, 4, 5, 8, 7, 4};
        Integer[] expected = new Integer[]{4, 3, 4, 5, 8, 7, 8};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_withNullElements_shouldHandleGracefully() {
        Integer[] array = new Integer[]{8, null, 6, null, 4};
        
        assertDoesNotThrow(() -> integerSortStrategy.sort(array, Integer::compareTo));
    }

    @Test
    void sort_withLargeDataset_performanceTest() {
        // Создание большого набора данных для проверки производительности
        Integer[] array = new Integer[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 1000);
        }
        
        // Выполнение
        long startTime = System.currentTimeMillis();
        integerSortStrategy.sort(array, Integer::compareTo);
        long endTime = System.currentTimeMillis();
        
        // Проверка - только проверяем, что даже большой массив обрабатывается за разумное время
        assertTrue(endTime - startTime < 5000, "Сортировка должна выполняться менее чем за 5 секунд");
        
        // Проверка, что четные элементы отсортированы
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] != null && array[i + 1] != null && array[i] % 2 == 0 && array[i + 1] % 2 == 0 &&
                array[i] > array[i + 1]) {
                fail("Четные элементы не отсортированы");
            }
        }
    }

    @Test
    void sort_zeroValue_shouldBeTreatedAsEven() {
        Integer[] array = new Integer[]{8, 0, 6, 3, 4};
        Integer[] expected = new Integer[]{0, 4, 6, 3, 8};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_negativeNumbers_shouldBeHandledCorrectly() {
        Integer[] array = new Integer[]{-8, -3, -6, -5, -4};
        // Четные: -8, -6, -4 -> должны стать: -8, -6, -4 (уже отсортированы)
        Integer[] expected = new Integer[]{-8, -3, -6, -5, -4};
        
        integerSortStrategy.sort(array, Integer::compareTo);
        
        assertArrayEquals(expected, array);
    }
}