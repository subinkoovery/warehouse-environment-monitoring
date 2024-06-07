package com.interview.central_service.listener;

import com.interview.central_service.model.SensorMeasurementDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@Slf4j
public class QueueListenerTest {

    @InjectMocks
    private QueueListener queueListener;

    @Mock
    private SensorMeasurementDetail sensorMeasurementDetail;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHumidityListener() {
        float valueAboveThreshold = 80.0f;
        when(sensorMeasurementDetail.getValue()).thenReturn(valueAboveThreshold);

        queueListener.humidityListener(sensorMeasurementDetail);

        verify(sensorMeasurementDetail, atLeast(1)).getValue();
    }

    @Test
    public void testTemperatureListener() {
        float valueAboveThreshold = 30.0f;
        when(sensorMeasurementDetail.getValue()).thenReturn(valueAboveThreshold);

        queueListener.temperatureListener(sensorMeasurementDetail);

        verify(sensorMeasurementDetail, times(1)).getValue();
    }
}
