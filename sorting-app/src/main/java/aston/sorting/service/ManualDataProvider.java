package aston.sorting.service;

import aston.sorting.model.Student;
import aston.sorting.util.ValidationUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualDataProvider implements DataProvider {
    private final Scanner scanner;

    public ManualDataProvider() {
        this.scanner = new Scanner(System.in);
    }

    // Конструктор для тестирования
    public ManualDataProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Student> provideData(int size) {
        List<Student> students = new ArrayList<>();
        if (size <= 0) {
            return students;
        }

        System.out.println("Введите данные для " + size + " студентов:");

        for (int i = 0; i < size; i++) {
            System.out.println("\nСтудент " + (i + 1) + ":");
            Student student = readStudentWithRetry();
            if (student != null) {
                students.add(student);
                System.out.println("Студент добавлен");
            } else {
                System.out.println("Не удалось добавить студента после 3 попыток. Пропускаем.");
            }
        }
        return students;
    }

    private Student readStudentWithRetry() {
        int attempts = 0;
        final int maxAttempts = 3;

        while (attempts < maxAttempts) {
            try {
                System.out.println("Попытка " + (attempts + 1) + " из " + maxAttempts);
                Student student = readCompleteStudent();
                if (student != null) {
                    return student;
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            attempts++;
        }
        return null;
    }

    private Student readCompleteStudent() {
        String groupNumber = readGroupNumber();
        Double averageGrade = readGrade();
        String recordBookNumber = readRecordBookNumber();

        if (groupNumber != null && averageGrade != null && recordBookNumber != null) {
            return Student.builder()
                    .groupNumber(groupNumber)
                    .averageGrade(averageGrade)
                    .recordBookNumber(recordBookNumber)
                    .build();
        }
        return null;
    }

    private String readGroupNumber() {
        System.out.print("Номер группы (формат: A1, B2, C1): ");
        String groupNumber = readLine();
        if (groupNumber == null) return null;

        if (!ValidationUtil.isValidGroupNumber(groupNumber)) {
            System.out.println("Ошибка: Неверный формат номера группы! Пример: A1, B2, C1");
            return null;
        }
        return groupNumber;
    }

    private Double readGrade() {
        System.out.print("Средний балл (2.0-5.0): ");
        String gradeInput = readLine();
        if (gradeInput == null) return null;

        try {
            double averageGrade = Double.parseDouble(gradeInput);
            if (!ValidationUtil.isValidGrade(averageGrade)) {
                System.out.println("Ошибка: Средний балл должен быть от 2.0 до 5.0!");
                return null;
            }
            return averageGrade;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Средний балл должен быть числом! Пример: 4.5");
            return null;
        }
    }

    private String readRecordBookNumber() {
        System.out.print("Номер зачетной книжки (формат: RBXXXX): ");
        String recordBookNumber = readLine();
        if (recordBookNumber == null) return null;

        if (!ValidationUtil.isValidRecordBookNumber(recordBookNumber)) {
            System.out.println("Ошибка: Неверный формат номера зачетной книжки! Пример: RB1001");
            return null;
        }
        return recordBookNumber;
    }

    private String readLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return null;
    }
}