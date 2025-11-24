package aston.sorting.service.sorting.students;

import aston.sorting.model.Student;
import aston.sorting.service.sorting.SortingStrategy;
import lombok.Setter;

@Setter
public class StudentSorter {
    private SortingStrategy<Student> strategy;

    public StudentSorter(SortingStrategy<Student> strategy)
    {
        this.strategy = strategy;
    }

    public void sort(Student[] students, StudentSortField studentSortField)
    {
        strategy.sort(students, studentSortField.getComparator());
    }
}
