package aston.sorting.evenodd;

/**
 * Functional interface for extracting numeric values from objects
 * to determine if they are even or odd.
 */
@FunctionalInterface
public interface NumericFieldExtractor<T> {

    double extractValue(T object);
}

