package com.interview.warehouse.runner;

import com.interview.warehouse.config.PortProperties;
import com.interview.warehouse.model.SensorType;
import com.interview.warehouse.service.UdpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class TemperatureSensorListenerTest {

    @Mock
    private UdpService udpService;

    @Mock
    private PortProperties portProperties;

    @InjectMocks
    private TemperatureSensorListener temperatureSensorListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun() throws Exception {
        int testPort = 8888;
        when(portProperties.getTemperatureSensor()).thenReturn(testPort);

        temperatureSensorListener.run();

        // Wait a short time to let the new thread start
        Thread.sleep(100);

        // Verify
        verify(udpService, times(1)).receiveAndForwardPacket(testPort, SensorType.TEMPERATURE);
    }

    @Test
    public void testRun_withException() throws Exception {
        int testPort = 8888;
        when(portProperties.getTemperatureSensor()).thenReturn(testPort);

        doThrow(new IOException("Test exception")).when(udpService).receiveAndForwardPacket(testPort, SensorType.TEMPERATURE);

        temperatureSensorListener.run();

        // Wait to start thread
        Thread.sleep(100);

        // Verify
        verify(udpService, times(1)).receiveAndForwardPacket(testPort, SensorType.TEMPERATURE);
    }
}
