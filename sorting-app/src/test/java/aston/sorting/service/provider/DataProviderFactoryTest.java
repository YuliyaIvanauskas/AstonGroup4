package aston.sorting.service.provider;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DataProviderFactoryTest {

    @Test
    void testCreateFileDataProvider() {
        DataProvider provider = DataProviderFactory.createProvider(
                DataProviderFactory.DataSourceType.FILE);
        assertInstanceOf(FileDataProvider.class, provider);
    }

    @Test
    void testCreateRandomDataProvider() {
        DataProvider provider = DataProviderFactory.createProvider(
                DataProviderFactory.DataSourceType.RANDOM);
        assertInstanceOf(RandomDataProvider.class, provider);
    }

    @Test
    void testCreateManualDataProvider() {
        DataProvider provider = DataProviderFactory.createProvider(
                DataProviderFactory.DataSourceType.MANUAL);
        assertInstanceOf(ManualDataProvider.class, provider);
    }

    @Test
    void testCreateWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DataProviderFactory.createProvider(null);
        });
    }
}