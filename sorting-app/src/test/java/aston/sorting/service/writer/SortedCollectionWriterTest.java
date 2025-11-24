package aston.sorting.service.writer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SortedCollectionWriterTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Should write header and footer when collection is empty")
    void testWriteSortedCollectionToFile_WithEmptyCollection() throws IOException {
        // Arrange
        Collection<String> emptyCollection = new ArrayList<>();
        File outputFile = tempDir.resolve("empty_collection_output.txt").toFile();

        // Act
        SortedCollectionWriter.writeSortedCollectionToFile(emptyCollection, outputFile.getAbsolutePath());

        // Assert
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(2, lines.size());
        assertEquals("---Student---", lines.get(0));
        assertEquals("---End of collection---", lines.get(1));
    }

    @Test
    @DisplayName("Should write single element between header and footer")
    void testWriteSortedCollectionToFile_WithSingleElement() throws IOException {
        // Arrange
        Collection<String> collection = List.of("Test Element");
        File outputFile = tempDir.resolve("single_element_output.txt").toFile();

        // Act
        SortedCollectionWriter.writeSortedCollectionToFile(collection, outputFile.getAbsolutePath());

        // Assert
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(3, lines.size());
        assertEquals("---Student---", lines.get(0));
        assertEquals("Test Element", lines.get(1));
        assertEquals("---End of collection---", lines.get(2));
    }

    @Test
    @DisplayName("Should write multiple elements in order between header and footer")
    void testWriteSortedCollectionToFile_WithMultipleElements() throws IOException {
        // Arrange
        Collection<String> collection = Arrays.asList("Element 1", "Element 2", "Element 3");
        File outputFile = tempDir.resolve("multiple_elements_output.txt").toFile();

        // Act
        SortedCollectionWriter.writeSortedCollectionToFile(collection, outputFile.getAbsolutePath());

        // Assert
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(5, lines.size());
        assertEquals("---Student---", lines.get(0));
        assertEquals("Element 1", lines.get(1));
        assertEquals("Element 2", lines.get(2));
        assertEquals("Element 3", lines.get(3));
        assertEquals("---End of collection---", lines.get(4));
    }

    @Test
    @DisplayName("Should append to existing file rather than overwriting")
    void testWriteSortedCollectionToFile_AppendsToExistingFile() throws IOException {
        // Arrange
        Collection<String> firstCollection = List.of("First Collection Element");
        Collection<String> secondCollection = List.of("Second Collection Element");
        File outputFile = tempDir.resolve("append_test_output.txt").toFile();

        // Act - Write the first collection
        SortedCollectionWriter.writeSortedCollectionToFile(firstCollection, outputFile.getAbsolutePath());
        // Act - Write the second collection (should append)
        SortedCollectionWriter.writeSortedCollectionToFile(secondCollection, outputFile.getAbsolutePath());

        // Assert
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(6, lines.size());
        assertEquals("---Student---", lines.get(0));
        assertEquals("First Collection Element", lines.get(1));
        assertEquals("---End of collection---", lines.get(2));
        assertEquals("---Student---", lines.get(3));
        assertEquals("Second Collection Element", lines.get(4));
        assertEquals("---End of collection---", lines.get(5));
    }

    @Test
    @DisplayName("Should write custom objects using their toString() method")
    void testWriteSortedCollectionToFile_WithCustomObjects() throws IOException {
        // Arrange
        TestObject obj1 = new TestObject("Object 1");
        TestObject obj2 = new TestObject("Object 2");
        Collection<TestObject> collection = Arrays.asList(obj1, obj2);
        File outputFile = tempDir.resolve("custom_objects_output.txt").toFile();

        // Act
        SortedCollectionWriter.writeSortedCollectionToFile(collection, outputFile.getAbsolutePath());

        // Assert
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(4, lines.size());
        assertEquals("---Student---", lines.get(0));
        assertEquals(obj1.toString(), lines.get(1));
        assertEquals(obj2.toString(), lines.get(2));
        assertEquals("---End of collection---", lines.get(3));
    }

    // Helper method to read lines from a file
    private List<String> readLinesFromFile(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    @Test
    @DisplayName("Should create directory if it doesn't exist")
    void testWriteSortedCollectionToFile_CreatesDirectories() throws IOException {
        // Arrange
        Collection<String> collection = List.of("Test Element");
        File nestedDir = new File(tempDir.toFile(), "nested/dir/structure");
        File outputFile = new File(nestedDir, "nested_output.txt");
    
        // Act
        SortedCollectionWriter.writeSortedCollectionToFile(collection, outputFile.getAbsolutePath());
    
        // Assert
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(3, lines.size());
        assertEquals("---Student---", lines.get(0));
        assertEquals("Test Element", lines.get(1));
        assertEquals("---End of collection---", lines.get(2));
    }
    
    @Test
    @DisplayName("Should handle null collection gracefully")
    void testWriteSortedCollectionToFile_WithNullCollection() throws IOException {
        // Arrange
        Collection<String> nullCollection = null;
        File outputFile = tempDir.resolve("null_collection_output.txt").toFile();
    
        // Act
        SortedCollectionWriter.writeSortedCollectionToFile(nullCollection, outputFile.getAbsolutePath());
    
        // Assert - The file should be created with header and footer but no content
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(2, lines.size());
        assertEquals("---Student---", lines.get(0));
        assertEquals("---End of collection---", lines.get(1));
    }
    
    @Test
    @DisplayName("Should handle invalid file path")
    void testWriteSortedCollectionToFile_WithInvalidPath() {
        // Arrange
        Collection<String> collection = List.of("Test Element");
        String invalidPath = "\\invalid\\path:*?\\file.txt"; // Invalid characters for a file path
    
        // Act & Assert
        assertDoesNotThrow(() -> {
            SortedCollectionWriter.writeSortedCollectionToFile(collection, invalidPath);
        }, "Should handle invalid paths gracefully without throwing exceptions");
    }
    
    @Test
    @DisplayName("Should preserve the order of elements")
    void testWriteSortedCollectionToFile_PreservesOrder() throws IOException {
        // Arrange
        List<String> originalList = Arrays.asList("C", "A", "B", "D");
        Collection<String> collection = new ArrayList<>(originalList); // Use ArrayList to preserve order
        File outputFile = tempDir.resolve("preserve_order_output.txt").toFile();
    
        // Act
        SortedCollectionWriter.writeSortedCollectionToFile(collection, outputFile.getAbsolutePath());
    
        // Assert
        assertTrue(outputFile.exists());
        List<String> lines = readLinesFromFile(outputFile);
        assertEquals(6, lines.size()); // Header + 4 elements + Footer
        assertEquals("---Student---", lines.get(0));
        for (int i = 0; i < originalList.size(); i++) {
            assertEquals(originalList.get(i), lines.get(i + 1));
        }
        assertEquals("---End of collection---", lines.get(5));
    }
    
    @Nested
    @DisplayName("Edge Cases")
    class EdgeCaseTests {
    
        @Test
        @DisplayName("Should handle very large strings")
        void testWriteSortedCollectionToFile_WithVeryLargeString() throws IOException {
            // Arrange
            StringBuilder largeString = new StringBuilder();
            for (int i = 0; i < 10000; i++) {
                largeString.append("very long string content ");
            }
            Collection<String> collection = Collections.singletonList(largeString.toString());
            File outputFile = tempDir.resolve("large_string_output.txt").toFile();
    
            // Act
            SortedCollectionWriter.writeSortedCollectionToFile(collection, outputFile.getAbsolutePath());
    
            // Assert
            assertTrue(outputFile.exists());
            long fileSize = Files.size(outputFile.toPath());
            assertTrue(fileSize > 200000, "File should be large enough to contain the large string");
    
            List<String> lines = readLinesFromFile(outputFile);
            assertEquals(3, lines.size());
            assertEquals("---Student---", lines.get(0));
            assertEquals(largeString.toString(), lines.get(1));
            assertEquals("---End of collection---", lines.get(2));
        }
    }
    
    // Simple test class for custom objects test
    private static class TestObject {
        private final String name;

        public TestObject(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestObject{name='" + name + "'}";
        }
    }
}
