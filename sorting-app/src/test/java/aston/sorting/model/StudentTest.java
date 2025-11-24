package aston.sorting.model;

import aston.sorting.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void builder_withValidFields_shouldCreateStudent() {
        // Act
        Student student = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        // Assert
        assertEquals("A1", student.getGroupNumber());
        assertEquals(5.0, student.getAverageGrade());
        assertEquals("RB1001", student.getRecordBookNumber());
    }

    @Test
    void builder_withInvalidGroupNumber_shouldThrowException() {
        // Arrange
        Student.Builder builder = Student.builder()
                .averageGrade(5.0)
                .recordBookNumber("RB1001");
        
        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            builder.groupNumber("Invalid").build();
        });
        
        assertTrue(exception.getMessage().contains("Неверный формат номера группы"));
    }

    @Test
    void builder_withInvalidAverageGrade_shouldThrowException() {
        // Arrange
        Student.Builder builder = Student.builder()
                .groupNumber("A1")
                .recordBookNumber("RB1001");
        
        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            builder.averageGrade(11.0).build();
        });
        
        assertTrue(exception.getMessage().contains("Средний балл"));
    }

    @Test
    void builder_withInvalidRecordBookNumber_shouldThrowException() {
        // Arrange
        Student.Builder builder = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0);
        
        // Act & Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            builder.recordBookNumber("Invalid").build();
        });
        
        assertTrue(exception.getMessage().contains("Неверный формат номера зачетной книжки"));
    }

    @Test
    void equals_withSameValues_shouldReturnTrue() {
        // Arrange
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
        
        // Act & Assert
        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void equals_withDifferentValues_shouldReturnFalse() {
        // Arrange
        Student student1 = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        Student student2 = Student.builder()
                .groupNumber("B1") // Different group
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        // Act & Assert
        assertNotEquals(student1, student2);
    }

    @Test
    void toString_shouldContainAllFields() {
        // Arrange
        Student student = Student.builder()
                .groupNumber("A1")
                .averageGrade(5.0)
                .recordBookNumber("RB1001")
                .build();
        
        String studentString = student.toString();
        
        // Assert
        assertTrue(studentString.contains("A1"));
        assertTrue(studentString.contains("5.0"));
        assertTrue(studentString.contains("RB1001"));
    }
}