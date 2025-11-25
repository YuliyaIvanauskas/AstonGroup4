package aston.sorting.service.provider.stream;

import aston.sorting.model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionStreamFillerTest {

    @Test
    void testFillWithRandomStudentsWithValidInput() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        List<Student> students = filler.fillWithRandomStudents(5);

        assertEquals(5, students.size());

        // Проверяем, что все студенты имеют корректные данные
        for (Student student : students) {
            assertNotNull(student.getGroupNumber());
            assertTrue(student.getGroupNumber().matches("^[A-F][1-5]$"));
            assertTrue(student.getAverageGrade() >= 3.0 && student.getAverageGrade() <= 5.0);
            assertNotNull(student.getRecordBookNumber());
            assertTrue(student.getRecordBookNumber().startsWith("RB"));
        }
    }

    @Test
    void testFillWithRandomStudentsWithZeroCount() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        List<Student> students = filler.fillWithRandomStudents(0);

        assertEquals(0, students.size());
        assertTrue(students.isEmpty());
    }

    @Test
    void testFillStudentsByGroupWithValidInput() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        String targetGroup = "A1";
        List<Student> students = filler.fillStudentsByGroup(3, targetGroup);

        assertEquals(3, students.size());

        // Проверяем, что все студенты имеют указанную группу
        for (Student student : students) {
            assertEquals(targetGroup, student.getGroupNumber());
            assertTrue(student.getAverageGrade() >= 3.0 && student.getAverageGrade() <= 5.0);
            assertTrue(student.getRecordBookNumber().startsWith("RB"));
        }
    }

    @Test
    void testFillStudentsByGroupWithDifferentGroups() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        List<Student> studentsA1 = filler.fillStudentsByGroup(2, "A1");
        List<Student> studentsB2 = filler.fillStudentsByGroup(2, "B2");

        assertEquals(2, studentsA1.size());
        assertEquals(2, studentsB2.size());

        // Проверяем, что группы соответствуют ожидаемым
        for (Student student : studentsA1) {
            assertEquals("A1", student.getGroupNumber());
        }
        for (Student student : studentsB2) {
            assertEquals("B2", student.getGroupNumber());
        }
    }

    @Test
    void testFillStudentsByGradeRangeWithValidInput() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        double minGrade = 4.0;
        double maxGrade = 5.0;
        List<Student> students = filler.fillStudentsByGradeRange(4, minGrade, maxGrade);

        assertEquals(4, students.size());

        // Проверяем, что все оценки в указанном диапазоне
        for (Student student : students) {
            assertTrue(student.getAverageGrade() >= minGrade && student.getAverageGrade() <= maxGrade);
            assertNotNull(student.getGroupNumber());
            assertTrue(student.getRecordBookNumber().startsWith("RB"));
        }
    }

    @Test
    void testFillStudentsByGradeRangeWithBoundaryValues() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        List<Student> students = filler.fillStudentsByGradeRange(3, 4.5, 4.5);

        assertEquals(3, students.size());

        // Все оценки должны быть 4.5
        for (Student student : students) {
            assertEquals(4.5, student.getAverageGrade(), 0.001);
        }
    }

    @Test
    void testFillWithSequentialRecordBooksWithValidInput() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        int startNumber = 1001;
        List<Student> students = filler.fillWithSequentialRecordBooks(5, startNumber);

        assertEquals(5, students.size());

        // Проверяем последовательные номера зачеток
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String expectedRecordBook = "RB" + (startNumber + i);
            assertEquals(expectedRecordBook, student.getRecordBookNumber());
            assertNotNull(student.getGroupNumber());
            assertTrue(student.getAverageGrade() >= 3.0 && student.getAverageGrade() <= 5.0);
        }
    }

    @Test
    void testFillWithSequentialRecordBooksWithLargeSequence() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        int startNumber = 5000;
        List<Student> students = filler.fillWithSequentialRecordBooks(10, startNumber);

        assertEquals(10, students.size());

        // Проверяем корректность последовательности
        for (int i = 0; i < students.size(); i++) {
            assertEquals("RB" + (startNumber + i), students.get(i).getRecordBookNumber());
        }
    }

    @Test
    void testAllMethodsProduceValidStudents() {
        CollectionStreamFiller filler = new CollectionStreamFiller();

        // Тестируем все методы на создание валидных студентов
        List<Student> randomStudents = filler.fillWithRandomStudents(2);
        List<Student> groupStudents = filler.fillStudentsByGroup(2, "C3");
        List<Student> gradeStudents = filler.fillStudentsByGradeRange(2, 3.5, 4.5);
        List<Student> sequentialStudents = filler.fillWithSequentialRecordBooks(2, 2001);

        // Проверяем все списки на валидность данных
        assertAllStudentsValid(randomStudents);
        assertAllStudentsValid(groupStudents);
        assertAllStudentsValid(gradeStudents);
        assertAllStudentsValid(sequentialStudents);
    }

    @Test
    void testGroupNamesFormat() {
        CollectionStreamFiller filler = new CollectionStreamFiller();
        List<Student> students = filler.fillWithRandomStudents(20);

        // Проверяем формат групп для всех студентов
        for (Student student : students) {
            String group = student.getGroupNumber();
            assertTrue(group.matches("^[A-F][1-5]$"),
                    "Формат названий групп должен быть: A1, B2, но был получен следующий формат: " + group);
        }
    }

    @Test
    void testGradeRangesAreRespected() {
        CollectionStreamFiller filler = new CollectionStreamFiller();

        // Тестируем разные диапазоны оценок
        List<Student> highGrades = filler.fillStudentsByGradeRange(5, 4.5, 5.0);
        List<Student> mediumGrades = filler.fillStudentsByGradeRange(5, 3.5, 4.0);
        List<Student> lowGrades = filler.fillStudentsByGradeRange(5, 3.0, 3.5);

        for (Student student : highGrades) {
            assertTrue(student.getAverageGrade() >= 4.5 && student.getAverageGrade() <= 5.0);
        }
        for (Student student : mediumGrades) {
            assertTrue(student.getAverageGrade() >= 3.5 && student.getAverageGrade() <= 4.0);
        }
        for (Student student : lowGrades) {
            assertTrue(student.getAverageGrade() >= 3.0 && student.getAverageGrade() <= 3.5);
        }
    }

    // Вспомогательный метод для проверки валидности всех студентов в списке
    private void assertAllStudentsValid(List<Student> students) {
        for (Student student : students) {
            assertNotNull(student.getGroupNumber());
            assertTrue(student.getGroupNumber().matches("^[A-F][1-5]$"));
            assertTrue(student.getAverageGrade() >= 3.0 && student.getAverageGrade() <= 5.0);
            assertNotNull(student.getRecordBookNumber());
            assertTrue(student.getRecordBookNumber().startsWith("RB"));
        }
    }
}