package aston.sorting.service.provider.stream;

import aston.sorting.model.Student;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionStreamFiller {
    private final Random random = new Random();

    // Доступные буквы для групп
    private final char[] groupLetters = {'A', 'B', 'C', 'D', 'E', 'F'};
    // Доступные цифры для групп
    private final int[] groupNumbers = {1, 2, 3, 4, 5};

    //Стрим для заполнения коллекции случайными студентами, реализуется фунцией createRandomStudent
    public List<Student> fillWithRandomStudents(int count) {
        return Stream.generate(this::createRandomStudent)
                .limit(count)
                .toList();
    }

    //Стрим для заполнения коллекции студентами с определенной группой, реализуется функцией createStudentInGroup
    public List<Student> fillStudentsByGroup(int count, String groupName) {
        return Stream.generate(() -> createStudentInGroup(groupName))
                .limit(count)
                .collect(Collectors.toList());
    }

    //Стрим для заполнения коллекции студентами с оценкой в диапазоне, реализуется функцией createStudentWithGradeRange
    public List<Student> fillStudentsByGradeRange(int count, double minGrade, double maxGrade) {
        return Stream.generate(() -> createStudentWithGradeRange(minGrade, maxGrade))
                .limit(count)
                .toList();
    }

    //Стрим для заполнения коллекции студентами с последовательными номерами зачеток
    public List<Student> fillWithSequentialRecordBooks(int count, int startRecordBookNumber) {
        return Stream.iterate(startRecordBookNumber, n -> n + 1)
                .limit(count)
                .map(this::createStudentWithRecordBook)
                .toList();
    }

    private Student createRandomStudent() {
        return Student.builder()
                .groupNumber(generateRandomGroupName())
                .averageGrade(generateRandomGrade())
                .recordBookNumber(generateRandomRecordBook())
                .build();
    }


    private Student createStudentInGroup(String groupName) {
        return Student.builder()
                .groupNumber(groupName)
                .averageGrade(generateRandomGrade())
                .recordBookNumber(generateRandomRecordBook())
                .build();
    }

    private Student createStudentWithGradeRange(double minGrade, double maxGrade) {
        double grade = minGrade + random.nextDouble() * (maxGrade - minGrade);
        grade = Math.min(grade, 5.0);

        return Student.builder()
                .groupNumber(generateRandomGroupName())
                .averageGrade(Math.round(grade * 10.0) / 10.0)
                .recordBookNumber(generateRandomRecordBook())
                .build();
    }

    private Student createStudentWithRecordBook(int recordBookNumber) {
        return Student.builder()
                .groupNumber(generateRandomGroupName())
                .averageGrade(generateRandomGrade())
                .recordBookNumber("RB" + recordBookNumber)
                .build();
    }

    private String generateRandomGroupName() {
        char letter = groupLetters[random.nextInt(groupLetters.length)];
        int number = groupNumbers[random.nextInt(groupNumbers.length)];
        return letter + String.valueOf(number);
    }

    private double generateRandomGrade() {
        return Math.round((3.0 + random.nextDouble() * 2.0) * 10.0) / 10.0;
    }

    private String generateRandomRecordBook() {
        return "RB" + (1000 + random.nextInt(9000));
    }

}