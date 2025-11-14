package aston.sorting.util;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @ParameterizedTest
    @ValueSource(strings = {"A1", "B2", "C1", "Z9"})
    void testValidGroupNumbers(String groupNumber) {
        assertTrue(ValidationUtil.isValidGroupNumber(groupNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "A", "1", "A11", "a1", "A1A", "1A"})
    void testInvalidGroupNumbers(String groupNumber) {
        assertFalse(ValidationUtil.isValidGroupNumber(groupNumber));
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.0, 3.5, 4.0, 5.0, 2.1, 4.9})
    void testValidGrades(double grade) {
        assertTrue(ValidationUtil.isValidGrade(grade));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.9, 5.1, 0.0, 6.0, -1.0})
    void testInvalidGrades(double grade) {
        assertFalse(ValidationUtil.isValidGrade(grade));
    }

    @ParameterizedTest
    @ValueSource(strings = {"RB1001", "RB9999", "RB1234"})
    void testValidRecordBookNumbers(String recordBookNumber) {
        assertTrue(ValidationUtil.isValidRecordBookNumber(recordBookNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "RB", "RB1", "RB123", "RB12345", "rb1001", "AB1001", "RB100A"})
    void testInvalidRecordBookNumbers(String recordBookNumber) {
        assertFalse(ValidationUtil.isValidRecordBookNumber(recordBookNumber));
    }
}