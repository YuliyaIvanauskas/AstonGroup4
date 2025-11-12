package aston.sorting;

import org.junit.jupiter.api.Test;
import aston.sorting.model.Student;

class AppTest {

    @Test
    void bubbleSortOrdersByGroupNumber() {
        Student[] students = new Student[]{
            Student.builder().groupNumber("IU25").averageGrade(7.2).recordBookNumber("AB1234").build(),
            Student.builder().groupNumber("IU11").averageGrade(8.1).recordBookNumber("AB1235").build(),
            Student.builder().groupNumber("CS30").averageGrade(6.5).recordBookNumber("AB1236").build()
        };
    }
}
