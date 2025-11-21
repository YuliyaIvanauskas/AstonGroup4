package aston.sorting.service;

public class DataProviderFactory {
    public static DataProvider createProvider(DataSourceType type) {
        if (type == null) {
            throw new IllegalArgumentException("Data source type cannot be null");
        }

        return switch (type) {
            case FILE -> new FileDataProvider();
            case RANDOM -> new RandomDataProvider();
            case MANUAL -> new ManualDataProvider();
        };
    }

    public enum DataSourceType {
        FILE, RANDOM, MANUAL
    }
}