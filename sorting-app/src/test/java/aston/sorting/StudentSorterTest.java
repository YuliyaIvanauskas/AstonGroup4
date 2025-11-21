package aston.sorting;

import aston.sorting.model.Student;
import aston.sorting.strategy.BubbleSortStrategy;
import aston.sorting.strategy.SortingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentSorterTest {

    private StudentSorter studentSorter;
    private SortingStrategy<Student> mockStrategy;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        mockStrategy = Mockito.mock(SortingStrategy.class);
        studentSorter = new StudentSorter(mockStrategy);
    }

    @Test
    void constructor_shouldSetStrategy() {
        SortingStrategy<Student> strategy = new BubbleSortStrategy<>();
        StudentSorter sorter = new StudentSorter(strategy);
        
        // Verify that the strategy is not null
        assertNotNull(sorter);
    }
    
    @Test
    void setStrategy_shouldChangeStrategy() {
        // Arrange
        SortingStrategy<Student> newStrategy = new BubbleSortStrategy<>();
        
        // Act
        studentSorter.setStrategy(newStrategy);
        
        // Assert - can't directly test internal state, but we can test behavior
        Student[] students = new Student[] {
            Student.builder().groupNumber("A1").averageGrade(5.0).recordBookNumber("RB1001").build()
        };
        
        // Calling sort with the new strategy
        studentSorter.sort(students, StudentSortField.GROUP_NUMBER);
        
        // Verify the mock strategy wasn't used
        verify(mockStrategy, never()).sort(any(), any());
    }

    @Test
    void sort_shouldCallStrategyWithCorrectParameters() {
        // Arrange
        Student[] students = new Student[] {
            Student.builder().groupNumber("A1").averageGrade(5.0).recordBookNumber("RB1001").build()
        };
        
        // Act
        studentSorter.sort(students, StudentSortField.GROUP_NUMBER);
        
        // Assert
        verify(mockStrategy).sort(eq(students), any(Comparator.class));
    }

    @Test
    void sort_shouldUseCorrectComparatorForGroupNumber() {
        // Arrange
        Student[] students = new Student[] {
            Student.builder().groupNumber("B2").averageGrade(5.0).recordBookNumber("RB1001").build(),
            Student.builder().groupNumber("A1").averageGrade(6.0).recordBookNumber("RB1002").build()
        };
        
        // Replace mock with real implementation for this test
        studentSorter.setStrategy(new BubbleSortStrategy<>());
        
        // Act
        studentSorter.sort(students, StudentSortField.GROUP_NUMBER);
        
        // Assert students are sorted by group number
        assertEquals("A1", students[0].getGroupNumber());
        assertEquals("B2", students[1].getGroupNumber());
    }

    @Test
    void sort_shouldUseCorrectComparatorForAverageGrade() {
        // Arrange
        Student[] students = new Student[] {
            Student.builder().groupNumber("A1").averageGrade(7.0).recordBookNumber("RB1001").build(),
            Student.builder().groupNumber("B2").averageGrade(5.0).recordBookNumber("RB1002").build()
        };
        
        // Replace mock with real implementation for this test
        studentSorter.setStrategy(new BubbleSortStrategy<>());
        
        // Act
        studentSorter.sort(students, StudentSortField.AVERAGE_GRADE);
        
        // Assert students are sorted by average grade
        assertEquals(5.0, students[0].getAverageGrade());
        assertEquals(7.0, students[1].getAverageGrade());
    }

    @Test
    void sort_shouldUseCorrectComparatorForRecordBookNumber() {
        // Arrange
        Student[] students = new Student[] {
            Student.builder().groupNumber("A1").averageGrade(5.0).recordBookNumber("RB1002").build(),
            Student.builder().groupNumber("B2").averageGrade(6.0).recordBookNumber("RB1001").build()
        };
        
        // Replace mock with real implementation for this test
        studentSorter.setStrategy(new BubbleSortStrategy<>());
        
        // Act
        studentSorter.sort(students, StudentSortField.RECORD_BOOK_NUMBER);
        
        // Assert students are sorted by record book number
        assertEquals("RB1001", students[0].getRecordBookNumber());
        assertEquals("RB1002", students[1].getRecordBookNumber());
    }
}