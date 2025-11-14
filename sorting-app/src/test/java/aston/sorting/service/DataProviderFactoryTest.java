package aston.sorting.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DataProviderFactoryTest {

    @Test
    void testCreateFileDataProvider() {
        DataProvider provider = DataProviderFactory.createProvider(
                DataProviderFactory.DataSourceType.FILE);
        assertTrue(provider instanceof FileDataProvider);
    }

    @Test
    void testCreateRandomDataProvider() {
        DataProvider provider = DataProviderFactory.createProvider(
                DataProviderFactory.DataSourceType.RANDOM);
        assertTrue(provider instanceof RandomDataProvider);
    }

    @Test
    void testCreateManualDataProvider() {
        DataProvider provider = DataProviderFactory.createProvider(
                DataProviderFactory.DataSourceType.MANUAL);
        assertTrue(provider instanceof ManualDataProvider);
    }

    @Test
    void testCreateWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DataProviderFactory.createProvider(null);
        });
    }
}