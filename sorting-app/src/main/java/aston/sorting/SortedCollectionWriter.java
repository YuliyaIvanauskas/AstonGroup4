package aston.sorting.sorting;
import java.io.*;
import java.util.*;

public class SortedCollectionWriter {

    //Method for write in file sorted collection

    public static <T> void writeSortedCollectionToFile(Collection<T> collection, String filename){


        try (BufferedWriter writer = new BufferredWriter(new FileWriter(filename, true))) {

            writer.write("---Student---");
            writer.newLine();

            for (T item : collection){
                writer.write(item.toString());
                writer.newLine();
            }

            writer.write("---End of collection---");

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}