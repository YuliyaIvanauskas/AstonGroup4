package aston.sorting.service;

import aston.sorting.model.Student;
import aston.sorting.service.FileDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileDataProviderTest {

    @TempDir
    File tempDir;
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = new File(tempDir, "students.txt");
    }

    @Test
    void testProvideDataWithValidFile() throws IOException {
        // Тестовые данные
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("A1,4.5,RB1001\n");
            writer.write("B2,3.8,RB1002\n");
            writer.write("C1,4.2,RB1003\n");
        }

        // Создаем анонимный класс для тестирования
        FileDataProvider provider = new FileDataProvider() {
            @Override
            protected String getFilePath() {
                return testFile.getAbsolutePath();
            }
        };

        List<Student> students = provider.provideData(3);

        assertEquals(3, students.size());
        assertEquals("A1", students.get(0).getGroupNumber());
        assertEquals(4.5, students.get(0).getAverageGrade(), 0.001);
        assertEquals("RB1001", students.get(0).getRecordBookNumber());
    }

    @Test
    void testProvideDataWithInvalidData() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("A1,6.0,RB1001\n"); // Неверный балл
            writer.write("Invalid,3.8,RB1002\n"); // Неверная группа
            writer.write("B2,3.8,Invalid\n"); // Неверный номер зачетки
            writer.write("C1,4.2,RB1003\n"); // Верная строка
        }

        FileDataProvider provider = new FileDataProvider() {
            @Override
            protected String getFilePath() {
                return testFile.getAbsolutePath();
            }
        };

        List<Student> students = provider.provideData(4);

        // Должна быть создана только одна валидная запись
        assertEquals(1, students.size());
        assertEquals("C1", students.get(0).getGroupNumber());
    }

    @Test
    void testProvideDataWithSizeLimit() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            for (int i = 0; i < 10; i++) {
                writer.write("A" + (i % 3 + 1) + ",4." + i + ",RB100" + i + "\n");
            }
        }

        FileDataProvider provider = new FileDataProvider() {
            @Override
            protected String getFilePath() {
                return testFile.getAbsolutePath();
            }
        };

        List<Student> students = provider.provideData(5);

        assertEquals(5, students.size());
    }

    @Test
    void testProvideDataWithNonExistentFile() {
        FileDataProvider provider = new FileDataProvider() {
            @Override
            protected String getFilePath() {
                return "non_existent_file.txt";
            }
        };

        List<Student> students = provider.provideData(5);

        assertTrue(students.isEmpty());
    }

    @Test
    void testProvideDataWithEmptyFile() throws IOException {
        testFile.createNewFile(); // Создаем пустой файл

        FileDataProvider provider = new FileDataProvider() {
            @Override
            protected String getFilePath() {
                return testFile.getAbsolutePath();
            }
        };

        List<Student> students = provider.provideData(5);

        assertTrue(students.isEmpty());
    }
}