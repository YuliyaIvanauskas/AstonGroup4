package aston.sorting;

import aston.sorting.model.Student;
import aston.sorting.strategy.SortingStrategy;

public class StudentSorter {
    private SortingStrategy<Student> strategy;

    public StudentSorter(SortingStrategy<Student> strategy)
    {
        this.strategy = strategy;
    }

    public void setStrategy(SortingStrategy<Student> strategy)
    {
        this.strategy = strategy;
    }

    public void sort(Student[] students, StudentSortField studentSortField)
    {
        strategy.sort(students, studentSortField.getComparator());
    }
}
