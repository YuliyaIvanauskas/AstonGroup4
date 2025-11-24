package aston.sorting.service.writer;
import java.io.*;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SortedCollectionWriter {

    private SortedCollectionWriter() {
    }

    //Method to write sorted collection in to the file

    public static <T> void writeSortedCollectionToFile(Collection<T> collection, String filename) {
        if (filename == null) {
            log.error("Файл для записи не указан");
            return;
        }
    
        // Create parent directories if they don't exist
        File file = new File(filename);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {

            writer.write("---Student---");
            writer.newLine();

            // Handle null collection gracefully
            if (collection != null) {
                for (T item : collection) {
                writer.write(item.toString());
                writer.newLine();
                }
            }

            writer.write("---End of collection---");
            writer.newLine();

        } catch (IOException e) {
            log.error("Ошибка при записи в файл: {}", e.getMessage());
        }
    }
}