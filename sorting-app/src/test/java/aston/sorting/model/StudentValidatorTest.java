package aston.sorting.model;

import aston.sorting.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"A1", "G2", "Z3", "B9", "S1"})
    void validateGroupNumber_withValidValues_shouldNotThrowException(String groupNumber) {
        // Выполнение и проверка
        assertDoesNotThrow(() -> StudentValidator.validateGroupNumber(groupNumber));
    }

    @Test
    void validateGroupNumber_withNull_shouldThrowException() {
        // Выполнение и проверка
        Exception exception = assertThrows(ValidationException.class,
                () -> StudentValidator.validateGroupNumber(null));
        assertTrue(exception.getMessage().contains("Неверный формат номера группы!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "A", "TooLongGroupNumber123"})
    void validateGroupNumber_withInvalidValues_shouldThrowException(String groupNumber) {
        // Выполнение и проверка
        Exception exception = assertThrows(ValidationException.class,
                () -> StudentValidator.validateGroupNumber(groupNumber));
        assertTrue(exception.getMessage().contains("Неверный формат номера группы!"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 5.0, 9.9, 10.0})
    void validateAverageGrade_withValidValues_shouldNotThrowException(double grade) {
        // Выполнение и проверка
        assertDoesNotThrow(() -> StudentValidator.validateAverageGrade(grade));
    }

    @Test
    void validateAverageGrade_withNull_shouldThrowException() {
        // Выполнение и проверка
        Exception exception = assertThrows(ValidationException.class,
                () -> StudentValidator.validateAverageGrade(null));
        assertTrue(exception.getMessage().contains("Средний балл"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, 10.1, 11.0, -5.0})
    void validateAverageGrade_withInvalidValues_shouldThrowException(double grade) {
        // Выполнение и проверка
        Exception exception = assertThrows(ValidationException.class,
                () -> StudentValidator.validateAverageGrade(grade));
        assertTrue(exception.getMessage().contains("Средний балл"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"RB1267", "RB1234", "RB0001", "RB0000"})
    void validateRecordBookNumber_withValidValues_shouldNotThrowException(String recordBookNumber) {
        // Выполнение и проверка
        assertDoesNotThrow(() -> StudentValidator.validateRecordBookNumber(recordBookNumber));
    }

    @Test
    void validateRecordBookNumber_withNull_shouldThrowException() {
        // Выполнение и проверка
        Exception exception = assertThrows(ValidationException.class,
                () -> StudentValidator.validateRecordBookNumber(null));
        assertTrue(exception.getMessage().contains("Неверный формат номера зачетной книжки!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "ABC", "TooLongRecordBookNumber123456789"})
    void validateRecordBookNumber_withInvalidValues_shouldThrowException(String recordBookNumber) {
        // Выполнение и проверка
        Exception exception = assertThrows(ValidationException.class,
                () -> StudentValidator.validateRecordBookNumber(recordBookNumber));
        assertTrue(exception.getMessage().contains("Неверный формат номера зачетной книжки!"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 1000})
    void validateLength_withValidValues_shouldNotThrowException(int length) {
        // Выполнение и проверка
        assertDoesNotThrow(() -> StudentValidator.validateLength(length));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 1001, 2000})
    void validateLength_withInvalidValues_shouldThrowException(int length) {
        // Выполнение и проверка
        Exception exception = assertThrows(ValidationException.class,
                () -> StudentValidator.validateLength(length));
        assertTrue(exception.getMessage().contains("Размер массива"));
    }
}