package aston.sorting.service;

import aston.sorting.model.Student;
import aston.sorting.service.ManualDataProvider;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ManualDataProviderTest {

    @Test
    void testProvideDataWithValidInput() {
        String input = "A1\n4.5\nRB1001\nB2\n3.8\nRB1002\n";
        Scanner testScanner = new Scanner(input);

        ManualDataProvider provider = new ManualDataProvider(testScanner);
        List<Student> students = provider.provideData(2);

        assertEquals(2, students.size());

        Student first = students.get(0);
        assertEquals("A1", first.getGroupNumber());
        assertEquals(4.5, first.getAverageGrade(), 0.001);
        assertEquals("RB1001", first.getRecordBookNumber());

        testScanner.close();
    }

    @Test
    void testProvideDataWithOneErrorThenSuccess() {
        // одна ошибка, затем успех
        String input =
                "Invalid\n4.5\nRB1001\n" +  // попытка 1: ошибка группы
                        "A1\n4.5\nRB1001\n";        // попытка 2: успех

        Scanner testScanner = new Scanner(input);
        ManualDataProvider provider = new ManualDataProvider(testScanner);

        List<Student> students = provider.provideData(1);

        assertEquals(1, students.size());
        testScanner.close();
    }

    @Test
    void testProvideDataWithZeroSize() {
        Scanner testScanner = new Scanner("");
        ManualDataProvider provider = new ManualDataProvider(testScanner);

        List<Student> students = provider.provideData(0);

        assertTrue(students.isEmpty());
        testScanner.close();
    }

    @Test
    void testProvideDataWithMaxAttemptsExceeded() {
        // Три попытки с ошибками
        String input =
                "Bad1\n4.5\nRB1001\n" +    // попытка 1
                        "Bad2\n4.5\nRB1001\n" +    // попытка 2
                        "Bad3\n4.5\nRB1001\n";     // попытка 3

        Scanner testScanner = new Scanner(input);
        ManualDataProvider provider = new ManualDataProvider(testScanner);

        List<Student> students = provider.provideData(1);

        assertTrue(students.isEmpty());
        testScanner.close();
    }
}