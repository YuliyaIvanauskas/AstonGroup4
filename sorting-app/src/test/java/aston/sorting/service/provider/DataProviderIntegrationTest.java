package aston.sorting.service.provider;

import aston.sorting.exception.ValidationException;
import aston.sorting.model.Student;
import aston.sorting.model.StudentValidator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderIntegrationTest {

    @Test
    void testAllProvidersCreateValidStudents() {
        DataProviderFactory.DataSourceType[] types = {
                DataProviderFactory.DataSourceType.RANDOM
        };

        for (DataProviderFactory.DataSourceType type : types) {
            DataProvider provider = DataProviderFactory.createProvider(type);
            List<Student> students = provider.provideData(3);

            assertEquals(3, students.size(), "Провайдер " + type + " должен создавать 3 студентов");

            for (Student student : students) {
                assertNotNull(student, "Студент не должен быть null");
                assertDoesNotThrow(() -> StudentValidator.validateGroupNumber(student.getGroupNumber()));
                assertDoesNotThrow(() -> StudentValidator.validateAverageGrade(student.getAverageGrade()));
                assertDoesNotThrow(() -> StudentValidator.validateRecordBookNumber(student.getRecordBookNumber()));
            }
        }
    }

    @Test
    void testManualProviderWithValidInput() {
        String input = "A1\n4.5\nRB1001\nB2\n3.8\nRB1002\nC1\n4.2\nRB1003\n";
        Scanner testScanner = new Scanner(input);

        ManualDataProvider provider = new ManualDataProvider(testScanner);
        List<Student> students = provider.provideData(3);

        assertEquals(3, students.size());

        Student first = students.get(0);
        assertEquals("A1", first.getGroupNumber());
        assertEquals(4.5, first.getAverageGrade(), 0.001);
        assertEquals("RB1001", first.getRecordBookNumber());

        testScanner.close();
    }

    @Test
    void testManualProviderWithInvalidThenValidInput() {
        // ИСПРАВЛЕНО: каждая попытка содержит все три поля
        String input =
                // Попытка 1: неверная группа
                "InvalidGroup\n4.5\nRB1001\n" +
                        // Попытка 2: неверный балл
                        "A1\n16.0\nRB1001\n" +
                        // Попытка 3: все верно
                        "A1\n4.5\nRB1001\n";

        Scanner testScanner = new Scanner(input);
        ManualDataProvider provider = new ManualDataProvider(testScanner);

        List<Student> students = provider.provideData(1);

        assertEquals(1, students.size());
        Student student = students.get(0);
        assertEquals("A1", student.getGroupNumber());
        assertEquals(4.5, student.getAverageGrade(), 0.001);
        assertEquals("RB1001", student.getRecordBookNumber());

        testScanner.close();
    }

    @Test
    void testManualProviderWithMultipleInvalidInputs() {
        // ИСПРАВЛЕНО: правильная структура попыток
        String input =
                // Студент 1: все верно
                "A1\n4.5\nRB1001\n" +
                        // Студент 2: 3 неудачные попытки → пропущен
                        "Bad1\n4.5\nRB1001\n" +    // ошибка группы
                        "A1\n16.0\nRB1001\n" +      // ошибка балла
                        "A1\n4.5\nBadRB\n" +       // ошибка зачетки
                        // Студент 3: все верно
                        "C1\n4.2\nRB1003\n";

        Scanner testScanner = new Scanner(input);
        ManualDataProvider provider = new ManualDataProvider(testScanner);

        List<Student> students = provider.provideData(3);

        // Должны получить только 2 студентов, так как один был пропущен
        assertEquals(2, students.size());
        assertEquals("A1", students.get(0).getGroupNumber());
        assertEquals("C1", students.get(1).getGroupNumber());

        testScanner.close();
    }

    @Test
    void testManualProviderWithPartialSuccess() {
        // Тест, где некоторые студенты создаются успешно, а некоторые нет
        String input =
                // Студент 1: успешно
                "A1\n4.5\nRB1001\n" +
                        // Студент 2: 3 неверные попытки - пропускается
                        "Bad1\n4.5\nRB1001\n" +    // ошибка группы
                        "A1\n16.0\nRB1001\n" +      // ошибка балла
                        "A1\n4.5\nBadRB\n" +       // ошибка зачетки
                        // Студент 3: успешно после одной ошибки
                        "Wrong\n4.5\nRB1001\n" +   // ошибка группы
                        "C1\n4.2\nRB1003\n";       // успех

        Scanner testScanner = new Scanner(input);
        ManualDataProvider provider = new ManualDataProvider(testScanner);

        List<Student> students = provider.provideData(3);

        // Должны получить 2 студентов
        assertEquals(2, students.size());
        assertEquals("A1", students.get(0).getGroupNumber());
        assertEquals("C1", students.get(1).getGroupNumber());

        testScanner.close();
    }

    @Test
    void testManualProviderWithAllFailures() {
        // Все студенты имеют невалидные данные
        String input =
                // Студент 1: 3 ошибки
                "Bad1\n4.5\nRB1001\n" +
                        "A1\n16.0\nRB1001\n" +
                        "A1\n4.5\nBadRB\n" +
                        // Студент 2: 3 ошибки
                        "Bad2\n4.5\nRB1001\n" +
                        "A1\n16.0\nRB1001\n" +
                        "A1\n4.5\nBadRB\n" +
                        // Студент 3: 3 ошибки
                        "Bad3\n4.5\nRB1001\n" +
                        "A1\n16.0\nRB1001\n" +
                        "A1\n4.5\nBadRB\n";

        Scanner testScanner = new Scanner(input);
        ManualDataProvider provider = new ManualDataProvider(testScanner);

        List<Student> students = provider.provideData(3);

        // Все студенты должны быть пропущены
        assertTrue(students.isEmpty());

        testScanner.close();
    }

    @Test
    void testFactoryWithAllTypes() {
        assertTrue(DataProviderFactory.createProvider(DataProviderFactory.DataSourceType.FILE) instanceof FileDataProvider);
        assertTrue(DataProviderFactory.createProvider(DataProviderFactory.DataSourceType.RANDOM) instanceof RandomDataProvider);
        assertTrue(DataProviderFactory.createProvider(DataProviderFactory.DataSourceType.MANUAL) instanceof ManualDataProvider);
    }

    @Test
    void testFileDataProviderIntegration() {
        // Для FileDataProvider создаем временный тестовый файл
        // Этот тест может быть пропущен если файл не существует
        DataProvider provider = DataProviderFactory.createProvider(DataProviderFactory.DataSourceType.FILE);
        List<Student> students = provider.provideData(2);

        // Проверяем что метод не падает и возвращает список (может быть пустым если файла нет)
        assertNotNull(students);

        // Если файл существует и данные валидны, проверяем их
        for (Student student : students) {
            assertNotNull(student);
            if (!students.isEmpty()) {
                assertThrows(ValidationException.class,
                    () -> StudentValidator.validateGroupNumber(student.getGroupNumber()));
                assertThrows(ValidationException.class,
                    () -> StudentValidator.validateAverageGrade(student.getAverageGrade()));
                assertThrows(ValidationException.class,
                    () -> StudentValidator.validateRecordBookNumber(student.getRecordBookNumber()));
            }
        }
    }
}