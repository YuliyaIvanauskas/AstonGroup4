package aston.sorting;

import aston.sorting.strategy.BubbleSortStrategy;
import org.junit.jupiter.api.Test;
import aston.sorting.model.Student;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void bubbleSortOrdersByGroupNumber() {
        Student[] students = new Student[]{
            Student.builder().groupNumber("IU25").averageGrade(7.2).recordBookNumber("AB1234").build(),
            Student.builder().groupNumber("IU11").averageGrade(8.1).recordBookNumber("AB1235").build(),
            Student.builder().groupNumber("CS30").averageGrade(6.5).recordBookNumber("AB1236").build()
        };
        StudentSorter studentSorter = new StudentSorter(new BubbleSortStrategy());
        studentSorter.sort(students, StudentSortField.GROUP_NUMBER);
        assertEquals("CS30", students[0].getGroupNumber());
        assertEquals("IU11", students[1].getGroupNumber());
        assertEquals("IU25", students[2].getGroupNumber());
    }
}
