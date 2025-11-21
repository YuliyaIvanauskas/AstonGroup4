package aston.sorting.model;

import java.util.regex.Pattern;


public final class StudentValidator {
    private static final Pattern GROUP_PATTERN = Pattern.compile("^[A-Za-z0-9\\-]{2,10}$");
    private static final double MIN_GRADE = 0.0;
    private static final double MAX_GRADE = 10.0;
    private static final Pattern RECORD_BOOK_PATTERN = Pattern.compile("^[A-Za-z0-9]{4,12}$");

    private StudentValidator() {
    }

    public static void validateGroupNumber(String groupNumber) {
        if (groupNumber == null || !GROUP_PATTERN.matcher(groupNumber).matches()) {
            throw new IllegalArgumentException("Номер группы должен содержать 2-10 символов (буквы, цифры, дефис).");
        }
    }

    public static void validateAverageGrade(Double averageGrade) {
        if (averageGrade == null) {
            throw new IllegalArgumentException("Средний балл не может быть пустым.");
        }
        if (averageGrade < MIN_GRADE || averageGrade > MAX_GRADE) {
            throw new IllegalArgumentException(
                "Средний балл должен быть в диапазоне " + MIN_GRADE + " - " + MAX_GRADE + '.'
            );
        }
    }

    public static void validateRecordBookNumber(String recordBookNumber) {
        if (recordBookNumber == null || !RECORD_BOOK_PATTERN.matcher(recordBookNumber).matches()) {
            throw new IllegalArgumentException("Номер зачетной книжки должен содержать 4-12 символов (буквы и цифры).");
        }
    }

    public static void validateLength(int length) {
        if (length <= 0 || length > 1000) {
            throw new IllegalArgumentException("Размер массива должен быть в диапазоне от 1 до 1000.");
        }
    }
}

