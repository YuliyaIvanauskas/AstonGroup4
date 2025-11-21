package aston.sorting.service;

import aston.sorting.exception.ValidationException;
import aston.sorting.model.Student;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileDataProvider implements DataProvider {
    private static final String DEFAULT_FILE_PATH = "students.txt";

    // для получения пути к файлу
    protected String getFilePath() {
        return DEFAULT_FILE_PATH;
    }

    @Override
    public List<Student> provideData(int size) {
        List<Student> students = new ArrayList<>();
        String filePath = getFilePath();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < size) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    processStudentLine(parts, students, line);
                    count = students.size();
                }
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла: {}", e.getMessage());
        }
        return students;
    }

    private void processStudentLine(String[] parts, List<Student> students, String line) {
        try {
            String groupNumber = parts[0].trim();
            double averageGrade = Double.parseDouble(parts[1].trim());
            String recordBookNumber = parts[2].trim();

            addStudent(groupNumber, averageGrade, recordBookNumber, students);
        } catch (NumberFormatException e) {
            log.error("Ошибка формата данных в файле: {}", line);
        }
    }

    private void addStudent(String groupNumber, double averageGrade, String recordBookNumber, List<Student> students) {
        try {
            Student student = Student.builder()
                .groupNumber(groupNumber)
                .averageGrade(averageGrade)
                .recordBookNumber(recordBookNumber)
                .build();
            students.add(student);
        } catch (ValidationException e) {
            log.error("Ошибка валидации: {}", e.getMessage());
        }
    }
}