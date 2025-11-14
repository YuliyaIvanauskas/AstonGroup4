package aston.sorting.util;

public class ValidationUtil {

    public static boolean isValidGroupNumber(String groupNumber) {
        return groupNumber != null && groupNumber.matches("[A-Z]\\d");
    }

    public static boolean isValidGrade(double grade) {
        return grade >= 2.0 && grade <= 5.0;
    }

    public static boolean isValidRecordBookNumber(String recordBookNumber) {
        return recordBookNumber != null && recordBookNumber.matches("RB\\d{4}");
    }
}