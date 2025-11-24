package aston.sorting.service.counter;

import aston.sorting.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementCounterTest {

    @Test
    void testCountOccurrencesMultiThreaded_emptyCollection() {
        List<String> list = new ArrayList<>();
        int count = ElementCounter.countOccurrencesMultiThreaded(list, "test");
        assertEquals(0, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_elementNotPresent() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        int count = ElementCounter.countOccurrencesMultiThreaded(list, 10);
        assertEquals(0, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_singleOccurrence() {
        List<String> list = Arrays.asList("apple", "banana", "orange", "grape");
        int count = ElementCounter.countOccurrencesMultiThreaded(list, "apple");
        assertEquals(1, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_multipleOccurrences() {
        List<String> list = Arrays.asList("apple", "banana", "apple", "orange", "apple", "grape");
        int count = ElementCounter.countOccurrencesMultiThreaded(list, "apple");
        assertEquals(3, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_allElementsMatch() {
        List<String> list = Arrays.asList("apple", "apple", "apple", "apple");
        int count = ElementCounter.countOccurrencesMultiThreaded(list, "apple");
        assertEquals(4, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_withNullElement() {
        List<String> list = Arrays.asList("apple", null, "banana", null, "orange");
        int count = ElementCounter.countOccurrencesMultiThreaded(list, null);
        assertEquals(2, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_withNullCollection() {
        int count = ElementCounter.countOccurrencesMultiThreaded(null, "test");
        assertEquals(0, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_withCustomThreads() {
        List<Integer> list = Arrays.asList(1, 2, 3, 1, 4, 1, 5, 1);
        int count = ElementCounter.countOccurrencesMultiThreaded(list, 1, 2); // Using 2 threads
        assertEquals(4, count);
    }

    @Test
    void testCountOccurrencesMultiThreaded_withStudentObjects() {
        Student student1 = Student.builder()
                .groupNumber("A1")
                .averageGrade(4.5)
                .recordBookNumber("RB1001")
                .build();

        Student student2 = Student.builder()
                .groupNumber("B2")
                .averageGrade(4.0)
                .recordBookNumber("RB1002")
                .build();

        Student studentDuplicate = Student.builder()
                .groupNumber("A1")
                .averageGrade(4.5)
                .recordBookNumber("RB1001")
                .build();

        List<Student> students = Arrays.asList(student1, student2, studentDuplicate, student2);
        
        // Поиск student1 (и его дубликата studentDuplicate)
        int count = ElementCounter.countOccurrencesMultiThreaded(students, student1);
        assertEquals(2, count);
        
        // Поиск student2
        count = ElementCounter.countOccurrencesMultiThreaded(students, student2);
        assertEquals(2, count);
    }
}