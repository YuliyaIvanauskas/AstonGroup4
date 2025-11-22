package aston.sorting.evenodd;

import aston.sorting.model.Student;
import aston.sorting.service.sorting.SortingStrategy;
import aston.sorting.service.sorting.students.StudentSortField;
import lombok.Setter;

/**
 * Sorter for Student objects that supports even/odd sorting strategies.
 * Objects with even values of the numeric field are sorted in natural order,
 * while objects with odd values remain in their original positions.
 */
@Setter
public class EvenOddStudentSorter {

    private SortingStrategy<Student> strategy;

    public EvenOddStudentSorter(SortingStrategy<Student> strategy) {
        this.strategy = strategy;
    }

    /**
     * Sorts students by average grade using even/odd logic.
     * Students with even average grades are sorted in natural order,
     * students with odd average grades remain in their original positions.
     */
    public void sortByAverageGrade(Student[] students) {
        strategy.sort(students, StudentSortField.AVERAGE_GRADE.getComparator());
    }

    /**
     * Creates an EvenOddBubbleSortStrategy for students sorted by average grade.
     */
    public static EvenOddStudentSorter createBubbleSort() {
        NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
        return new EvenOddStudentSorter(new EvenOddBubbleSortStrategy<>(extractor));
    }

    /**
     * Creates an EvenOddQuickSortStrategy for students sorted by average grade.
     */
    public static EvenOddStudentSorter createQuickSort() {
        NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
        return new EvenOddStudentSorter(new EvenOddQuickSortStrategy<>(extractor));
    }

    /**
     * Creates an EvenOddMergeSortStrategy for students sorted by average grade.
     */
    public static EvenOddStudentSorter createMergeSort() {
        NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
        return new EvenOddStudentSorter(new EvenOddMergeSortStrategy<>(extractor));
    }

    /**
     * Creates an EvenOddSelectionSortStrategy for students sorted by average grade.
     */
    public static EvenOddStudentSorter createSelectionSort() {
        NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
        return new EvenOddStudentSorter(new EvenOddSelectionSortStrategy<>(extractor));
    }

    /**
     * Creates an EvenOddInsertionSortStrategy for students sorted by average grade.
     */
    public static EvenOddStudentSorter createInsertionSort() {
        NumericFieldExtractor<Student> extractor = Student::getAverageGrade;
        return new EvenOddStudentSorter(new EvenOddInsertionSortStrategy<>(extractor));
    }
}

