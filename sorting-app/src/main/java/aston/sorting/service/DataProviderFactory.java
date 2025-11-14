package aston.sorting.service;

public class DataProviderFactory {
    public static DataProvider createProvider(DataSourceType type) {
        if (type == null) {
            throw new IllegalArgumentException("Data source type cannot be null");
        }

        switch (type) {
            case FILE:
                return new FileDataProvider();
            case RANDOM:
                return new RandomDataProvider();
            case MANUAL:
                return new ManualDataProvider();
            default:
                throw new IllegalArgumentException("Unknown data source type: " + type);
        }
    }

    public enum DataSourceType {
        FILE, RANDOM, MANUAL
    }
}