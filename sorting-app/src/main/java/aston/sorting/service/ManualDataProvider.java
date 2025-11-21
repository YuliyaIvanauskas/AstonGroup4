package aston.sorting.service;

import aston.sorting.exception.ValidationException;
import aston.sorting.model.Student;
import aston.sorting.model.StudentValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        log.info("Введите данные для {} студентов:", size);

        for (int i = 0; i < size; i++) {
            log.info("\nСтудент {}:", (i + 1));
            Student student = readStudentWithRetry();
            if (student != null) {
                students.add(student);
                log.info("Студент добавлен");
            } else {
                log.warn("Не удалось добавить студента после 3 попыток. Пропускаем.");
            }
        }
        return students;
    }

    private Student readStudentWithRetry() {
        int attempts = 0;
        final int maxAttempts = 3;

        while (attempts < maxAttempts) {
            try {
                log.info("Попытка {} из {}", (attempts + 1), maxAttempts);
                Student student = readCompleteStudent();
                if (student != null) {
                    return student;
                }
            } catch (Exception e) {
                log.error("Ошибка: {}", e.getMessage());
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
        log.info("Номер группы (формат: A1, B2, C1): ");
        String groupNumber = readLine();
        if (groupNumber == null) return null;
    
        try {
            StudentValidator.validateGroupNumber(groupNumber);
            return groupNumber;
        } catch (ValidationException e) {
            log.error("Ошибка: {}", e.getMessage());
            return null;
        }
    }

    private Double readGrade() {
        log.info("Средний балл (0.0-10.0): ");
        String gradeInput = readLine();
    
        try {
            Double averageGrade = gradeInput != null ? Double.valueOf(gradeInput) : null;
            StudentValidator.validateAverageGrade(averageGrade);
            return averageGrade;
        } catch (NumberFormatException e) {
            log.error("Ошибка: Средний балл должен быть числом! Пример: 4.5");
            return null;
        } catch (ValidationException e) {
            log.error("Ошибка: {}", e.getMessage());
            return null;
        }
    }

    private String readRecordBookNumber() {
        log.info("Номер зачетной книжки (формат: 4-12 букв и цифр): ");
        String recordBookNumber = readLine();
        if (recordBookNumber == null) return null;
    
        try {
            StudentValidator.validateRecordBookNumber(recordBookNumber);
            return recordBookNumber;
        } catch (ValidationException e) {
            log.error("Ошибка: {}", e.getMessage());
            return null;
        }
    }

    private String readLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return null;
    }
}