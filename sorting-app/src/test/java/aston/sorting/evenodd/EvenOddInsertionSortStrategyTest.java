package aston.sorting.evenodd;

import aston.sorting.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddInsertionSortStrategyTest {

    private EvenOddInsertionSortStrategy<Integer> integerSortStrategy;
    private EvenOddInsertionSortStrategy<Student> studentSortStrategy;

    @BeforeEach
    void setUp() {
        integerSortStrategy = new EvenOddInsertionSortStrategy<>(x -> (double) x);
        studentSortStrategy = new EvenOddInsertionSortStrategy<>(Student::getAverageGrade);
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
    void sort_allEvenValues_shouldSortCorrectly() {
        // Подготовка - все четные значения должны быть отсортированы
        Integer[] array = new Integer[]{8, 2, 6, 4, 10};
        Integer[] expected = new Integer[]{2, 4, 6, 8, 10};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_allOddValues_shouldRemainUnchanged() {
        // Подготовка - все нечетные значения должны остаться на своих позициях
        Integer[] array = new Integer[]{5, 3, 9, 1, 7};
        Integer[] expected = new Integer[]{5, 3, 9, 1, 7};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_mixedEvenAndOdd_evenShouldSort_oddShouldRemain() {
        // Подготовка - четные сортируются, нечетные остаются на позициях
        // Позиции: 0(8-чет), 1(3-нечет), 2(6-чет), 3(5-нечет), 4(4-чет), 5(7-нечет)
        Integer[] array = new Integer[]{8, 3, 6, 5, 4, 7};
        // Ожидаемый результат: четные сортируются (4, 6, 8), нечетные остаются (3, 5, 7)
        // Позиции четных: 0, 2, 4 -> должны стать 4, 6, 8
        Integer[] expected = new Integer[]{4, 3, 6, 5, 8, 7};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
        // Проверяем, что нечетные остались на своих позициях
        assertEquals(3, array[1]);
        assertEquals(5, array[3]);
        assertEquals(7, array[5]);
    }

    @Test
    void sort_mixedEvenAndOdd_complexCase() {
        // Подготовка - более сложный случай
        // Позиции: 0(10-чет), 1(1-нечет), 2(8-чет), 3(3-нечет), 4(2-чет), 5(9-нечет), 6(6-чет)
        Integer[] array = new Integer[]{10, 1, 8, 3, 2, 9, 6};
        // Четные: 10, 8, 2, 6 -> должны быть отсортированы: 2, 6, 8, 10
        // Нечетные остаются: 1, 3, 9
        Integer[] expected = new Integer[]{2, 1, 6, 3, 8, 9, 10};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
        // Проверяем нечетные на своих позициях
        assertEquals(1, array[1]);
        assertEquals(3, array[3]);
        assertEquals(9, array[5]);
    }

    @Test
    void sort_evenValuesAlreadySorted_shouldRemainSorted() {
        // Подготовка - четные уже отсортированы
        Integer[] array = new Integer[]{2, 5, 4, 7, 6, 9};
        Integer[] expected = new Integer[]{2, 5, 4, 7, 6, 9};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка
        assertArrayEquals(expected, array);
    }

    @Test
    void sort_withStudents_evenAverageGrades_shouldSort() {
        // Подготовка - студенты с четными средними баллами
        Student[] students = new Student[]{
            Student.builder().groupNumber("A1").averageGrade(8.0).recordBookNumber("RB1456").build(),
            Student.builder().groupNumber("A2").averageGrade(4.0).recordBookNumber("RB2567").build(),
            Student.builder().groupNumber("A3").averageGrade(6.0).recordBookNumber("RB3678").build()
        };
        
        // Выполнение
        studentSortStrategy.sort(students, Comparator.comparing(Student::getAverageGrade));
        
        // Проверка - должны быть отсортированы по возрастанию среднего балла
        assertEquals(4.0, students[0].getAverageGrade());
        assertEquals(6.0, students[1].getAverageGrade());
        assertEquals(8.0, students[2].getAverageGrade());
    }

    @Test
    void sort_withStudents_oddAverageGrades_shouldRemainUnchanged() {
        // Подготовка - студенты с нечетными средними баллами
        Student student1 = Student.builder().groupNumber("A1").averageGrade(7.0).recordBookNumber("RB1789").build();
        Student student2 = Student.builder().groupNumber("A2").averageGrade(5.0).recordBookNumber("RB2678").build();
        Student student3 = Student.builder().groupNumber("A3").averageGrade(9.0).recordBookNumber("RB3678").build();
        Student[] students = new Student[]{student1, student2, student3};
        Student[] expected = new Student[]{student1, student2, student3};
        
        // Выполнение
        studentSortStrategy.sort(students, Comparator.comparing(Student::getAverageGrade));
        
        // Проверка - должны остаться на своих позициях
        assertArrayEquals(expected, students);
        assertEquals(7.0, students[0].getAverageGrade());
        assertEquals(5.0, students[1].getAverageGrade());
        assertEquals(9.0, students[2].getAverageGrade());
    }

    @Test
    void sort_withStudents_mixedEvenAndOdd_shouldSortEvenOnly() {
        // Подготовка - смешанные четные и нечетные средние баллы
        Student student1 = Student.builder().groupNumber("A1").averageGrade(8.0).recordBookNumber("RB1767").build();
        Student student2 = Student.builder().groupNumber("A2").averageGrade(5.0).recordBookNumber("RB2777").build();
        Student student3 = Student.builder().groupNumber("A3").averageGrade(6.0).recordBookNumber("RB3778").build();
        Student student4 = Student.builder().groupNumber("A4").averageGrade(7.0).recordBookNumber("RB4677").build();
        Student student5 = Student.builder().groupNumber("A5").averageGrade(4.0).recordBookNumber("RB5455").build();
        
        Student[] students = new Student[]{student1, student2, student3, student4, student5};
        
        // Выполнение
        studentSortStrategy.sort(students, Comparator.comparing(Student::getAverageGrade));
        
        // Проверка - четные (8.0, 6.0, 4.0) должны быть отсортированы: 4.0, 6.0, 8.0
        // Нечетные (5.0, 7.0) остаются на позициях 1 и 3
        assertEquals(4.0, students[0].getAverageGrade()); // четное, отсортировано
        assertEquals(5.0, students[1].getAverageGrade()); // нечетное, осталось
        assertEquals(6.0, students[2].getAverageGrade()); // четное, отсортировано
        assertEquals(7.0, students[3].getAverageGrade()); // нечетное, осталось
        assertEquals(8.0, students[4].getAverageGrade()); // четное, отсортировано
    }

    @Test
    void sort_withNullElements_shouldHandleGracefully() {
        // Подготовка - массив с null элементами
        Integer[] array = new Integer[]{8, null, 6, null, 4};
        
        // Выполнение - не должно выбросить исключение
        assertDoesNotThrow(() -> integerSortStrategy.sort(array, Integer::compareTo));
    }

    @Test
    void sort_zeroValue_shouldBeTreatedAsEven() {
        // Подготовка - ноль считается четным
        Integer[] array = new Integer[]{8, 0, 6, 3, 4};
        // Четные на позициях 0(8), 1(0), 2(6), 4(4) -> должны быть отсортированы: 0, 4, 6, 8
        Integer[] expected = new Integer[]{0, 4, 6, 3, 8};
        
        // Выполнение
        integerSortStrategy.sort(array, Integer::compareTo);
        
        // Проверка - ноль должен быть отсортирован как четное
        assertArrayEquals(expected, array);
        assertEquals(0, array[0]);
        assertEquals(4, array[1]);
        assertEquals(6, array[2]);
        assertEquals(3, array[3]); // нечетное осталось
        assertEquals(8, array[4]);
    }
}