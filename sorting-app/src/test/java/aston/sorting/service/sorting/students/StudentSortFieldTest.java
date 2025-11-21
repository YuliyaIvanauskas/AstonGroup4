package aston.sorting.service.sorting.students;

import aston.sorting.model.Student;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class StudentSortFieldTest {

    @Test
    void groupNumberComparator_shouldCompareByGroupNumber() {
        // Arrange
        Student student1 = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        Student student2 = Student.builder()
                .groupNumber("B1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        Comparator<Student> comparator = StudentSortField.GROUP_NUMBER.getComparator();
        
        // Assert
        assertTrue(comparator.compare(student1, student2) < 0); // A comes before B
        assertTrue(comparator.compare(student2, student1) > 0); // B comes after A
        assertEquals(0, comparator.compare(student1, student1)); // Same objects
    }

    @Test
    void averageGradeComparator_shouldCompareByAverageGrade() {
        // Arrange
        Student student1 = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        Student student2 = Student.builder()
                .groupNumber("A1")
                .averageGrade(6.0)
                .recordBookNumber("RB1001")
                .build();
        
        Comparator<Student> comparator = StudentSortField.AVERAGE_GRADE.getComparator();
        
        // Assert
        assertTrue(comparator.compare(student1, student2) < 0); // 5.0 < 6.0
        assertTrue(comparator.compare(student2, student1) > 0); // 6.0 > 5.0
        assertEquals(0, comparator.compare(student1, student1)); // Same objects
    }

    @Test
    void recordBookNumberComparator_shouldCompareByRecordBookNumber() {
        // Arrange
        Student student1 = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        Student student2 = Student.builder()
                .groupNumber("A1") 
                .averageGrade(5.0)
                .recordBookNumber("RB1002")
                .build();
        
        Comparator<Student> comparator = StudentSortField.RECORD_BOOK_NUMBER.getComparator();
        
        // Assert
        assertTrue(comparator.compare(student1, student2) < 0); // RB1001 < RB1002
        assertTrue(comparator.compare(student2, student1) > 0); // RB1002 > RB1001
        assertEquals(0, comparator.compare(student1, student1)); // Same objects
    }

    @Test
    void allComparators_shouldHandleEqualValues() {
        // Arrange - same values for all fields
        Student student1 = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        Student student2 = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        // Assert - all comparators should return 0 for equal objects
        assertEquals(0, StudentSortField.GROUP_NUMBER.getComparator().compare(student1, student2));
        assertEquals(0, StudentSortField.AVERAGE_GRADE.getComparator().compare(student1, student2));
        assertEquals(0, StudentSortField.RECORD_BOOK_NUMBER.getComparator().compare(student1, student2));
    }
}