package aston.sorting.model;

import java.util.regex.Pattern;
import aston.sorting.exception.ValidationException;


public final class StudentValidator {
    private static final Pattern GROUP_PATTERN = Pattern.compile("[A-Z]\\d");
    private static final double MIN_GRADE = 0.0;
    private static final double MAX_GRADE = 10.0;
    private static final Pattern RECORD_BOOK_PATTERN = Pattern.compile("RB\\d{4}");

    private StudentValidator() {
    }

    public static void validateGroupNumber(String groupNumber) {
        if (groupNumber == null || !GROUP_PATTERN.matcher(groupNumber).matches()) {
            throw new ValidationException("Ошибка: Неверный формат номера группы! Пример: A1, B2, C1");
        }
    }

    public static void validateAverageGrade(Double averageGrade) {
        if (averageGrade == null) {
            throw new ValidationException("Средний балл не может быть пустым.");
        }
        if (averageGrade < MIN_GRADE || averageGrade > MAX_GRADE) {
            throw new ValidationException(
                "Средний балл должен быть в диапазоне " + MIN_GRADE + " - " + MAX_GRADE + '.'
            );
        }
    }

    public static void validateRecordBookNumber(String recordBookNumber) {
        if (recordBookNumber == null || !RECORD_BOOK_PATTERN.matcher(recordBookNumber).matches()) {
            throw new ValidationException("Ошибка: Неверный формат номера зачетной книжки! Пример: RB1001");
        }
    }

    public static void validateLength(int length) {
        if (length <= 0 || length > 1000) {
            throw new ValidationException("Размер массива должен быть в диапазоне от 1 до 1000.");
        }
    }
}

