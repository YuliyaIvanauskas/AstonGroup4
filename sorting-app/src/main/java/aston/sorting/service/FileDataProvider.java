package aston.sorting.service;

import aston.sorting.model.Student;
import aston.sorting.util.ValidationUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDataProvider implements DataProvider {
    private static final String DEFAULT_FILE_PATH = "students.txt";

    // для получения пути к файлу
    protected String getFilePath() {
        return DEFAULT_FILE_PATH;
    }

    @Override
    public List<Student> provideData(int size) {
        List<Student> students = new ArrayList<>();
        String filePath = getFilePath(); // Используем метод вместо прямой константы

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < size) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        String groupNumber = parts[0].trim();
                        double averageGrade = Double.parseDouble(parts[1].trim());
                        String recordBookNumber = parts[2].trim();

                        if (ValidationUtil.isValidGroupNumber(groupNumber) &&
                                ValidationUtil.isValidGrade(averageGrade) &&
                                ValidationUtil.isValidRecordBookNumber(recordBookNumber)) {

                            Student student = Student.builder()
                                    .groupNumber(groupNumber)
                                    .averageGrade(averageGrade)
                                    .recordBookNumber(recordBookNumber)
                                    .build();
                            students.add(student);
                            count++;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка формата данных в файле: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        return students;
    }
}