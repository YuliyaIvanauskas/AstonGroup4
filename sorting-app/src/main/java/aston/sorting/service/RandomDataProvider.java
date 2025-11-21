package aston.sorting.service;

import aston.sorting.exception.ValidationException;
import aston.sorting.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomDataProvider implements DataProvider {
    private final Random random = new Random();
    private static final String[] GROUPS = {"A1", "A2", "B1", "B2", "C1", "C2"};

    @Override
    public List<Student> provideData(int size) {
        List<Student> students = new ArrayList<>();
        int generated = 0;
        while (generated < size) {
            try {
                String groupNumber = GROUPS[random.nextInt(GROUPS.length)];
                double averageGrade = random.nextDouble() * 10.0; // от 0.0 до 10.0
                averageGrade = Math.round(averageGrade * 10.0) / 10.0; // округление до 1 знака

                String recordBookNumber = "RB" + (1000 + random.nextInt(9000));

                Student student = Student.builder()
                    .groupNumber(groupNumber)
                    .averageGrade(averageGrade)
                    .recordBookNumber(recordBookNumber)
                    .build();
                students.add(student);
                generated++;
            } catch (ValidationException e) {
                log.error("Ошибка валидации при генерации случайных данных: {}", e.getMessage());
                log.info("Повторная попытка генерации данных для студента.");
                // Просто повторяем попытку, не увеличивая счетчик
            }
        }
        return students;
    }
}