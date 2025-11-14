package aston.sorting;

import aston.sorting.model.Student;

import java.util.Comparator;

public enum StudentSortField {
    GROUP_NUMBER,
    AVERAGE_GRADE,
    RECORD_BOOK_NUMBER;

    public Comparator<Student> getComparator() {
        return switch (this) {
            case GROUP_NUMBER -> Comparator.comparing(Student::getGroupNumber);
            case AVERAGE_GRADE -> Comparator.comparing(Student::getAverageGrade);
            case RECORD_BOOK_NUMBER -> Comparator.comparing(Student::getRecordBookNumber);
        };
    }
}
