package aston.sorting.service;

import aston.sorting.model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomDataProviderTest {

    private RandomDataProvider randomDataProvider = new RandomDataProvider();

    @Test
    void testProvideDataWithValidSize() {
        List<Student> students = randomDataProvider.provideData(5);

        assertEquals(5, students.size());

        // Проверяем, что все студенты имеют валидные данные
        for (Student student : students) {
            assertNotNull(student.getGroupNumber());
            assertTrue(student.getGroupNumber().matches("[A-C][1-2]"));

            assertTrue(student.getAverageGrade() >= 0.0 && student.getAverageGrade() <= 10.0);

            assertNotNull(student.getRecordBookNumber());
            assertTrue(student.getRecordBookNumber().matches("RB\\d{4}"));
        }
    }

    @Test
    void testProvideDataWithZeroSize() {
        List<Student> students = randomDataProvider.provideData(0);

        assertTrue(students.isEmpty());
    }

    @Test
    void testProvideDataWithLargeSize() {
        List<Student> students = randomDataProvider.provideData(1000);

        assertEquals(1000, students.size());

        // Проверяем, что все объекты уникальны (разные ссылки)
        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) {
                assertNotSame(students.get(i), students.get(j));
            }
        }
    }

    @Test
    void testRandomDataDistribution() {
        List<Student> students = randomDataProvider.provideData(100);

        // Проверяем, что оценки в допустимом диапазоне
        long validGrades = students.stream()
                .filter(s -> s.getAverageGrade() >= 0.0 && s.getAverageGrade() <= 10.0)
                .count();
        assertEquals(100, validGrades);

        // Проверяем формат номеров зачетных книжек
        long validRecordBooks = students.stream()
                .filter(s -> s.getRecordBookNumber().matches("RB\\d{4}"))
                .count();
        assertEquals(100, validRecordBooks);
    }
}