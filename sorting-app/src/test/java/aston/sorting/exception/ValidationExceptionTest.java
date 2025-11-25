package aston.sorting.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

    @Test
    void constructor_withMessage_shouldSetMessage() {
        // Arrange
        String errorMessage = "Test error message";
        
        // Act
        ValidationException exception = new ValidationException(errorMessage);
        
        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void constructor_withMessageAndCause_shouldSetBoth() {
        // Arrange
        String errorMessage = "Test error message";
        Throwable cause = new RuntimeException("Original cause");
        
        // Act
        ValidationException exception = new ValidationException(errorMessage, cause);
        
        // Assert
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void exception_shouldBeRuntimeException() {
        // Arrange & Act
        ValidationException exception = new ValidationException("Test");
        
        // Assert
        assertInstanceOf(RuntimeException.class, exception);
    }
}