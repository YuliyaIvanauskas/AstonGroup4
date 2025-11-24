package aston.sorting.service.provider;

import aston.sorting.model.Student;
import java.util.List;

public interface DataProvider {
    List<Student> provideData(int size);
}