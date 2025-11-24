package aston.sorting.model;

import java.util.Objects;
import lombok.Getter;

@Getter
public final class Student {
    private final String groupNumber;
    private final double averageGrade;
    private final String recordBookNumber;

    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageGrade = builder.averageGrade;
        this.recordBookNumber = builder.recordBookNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Double.compare(student.averageGrade, averageGrade) == 0
            && Objects.equals(groupNumber, student.groupNumber)
            && Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }

    @Override
    public String toString() {
        return "Student{"
            + "groupNumber='" + groupNumber + '\''
            + ", averageGrade=" + averageGrade
            + ", recordBookNumber='" + recordBookNumber + '\''
            + '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String groupNumber;
        private Double averageGrade;
        private String recordBookNumber;

        private Builder() {
        }

        public Builder groupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder averageGrade(double averageGrade) {
            this.averageGrade = averageGrade;
            return this;
        }

        public Builder recordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Student build() {
            StudentValidator.validateGroupNumber(groupNumber);
            StudentValidator.validateAverageGrade(averageGrade);
            StudentValidator.validateRecordBookNumber(recordBookNumber);
            return new Student(this);
        }
    }
}

