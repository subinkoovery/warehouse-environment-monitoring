package com.interview.central_service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensorMeasurementDetailTest {

    @Test
    public void testGetterAndSetter() {
        // Arrange
        SensorMeasurementDetail sensorMeasurementDetail = new SensorMeasurementDetail();

        // Act
        sensorMeasurementDetail.setSensorId("123");
        sensorMeasurementDetail.setValue(25.0f);

        // Assert
        assertEquals("123", sensorMeasurementDetail.getSensorId());
        assertEquals(25.0f, sensorMeasurementDetail.getValue());
    }
}