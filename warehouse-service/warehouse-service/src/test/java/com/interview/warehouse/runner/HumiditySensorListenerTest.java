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

public class HumiditySensorListenerTest {

    @Mock
    private UdpService udpService;

    @Mock
    private PortProperties portProperties;

    @InjectMocks
    private HumiditySensorListener humiditySensorListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun() throws Exception {
        int testPort = 9999;
        when(portProperties.getHumiditySensor()).thenReturn(testPort);

        humiditySensorListener.run();

        // Wait a short time to let the new thread start
        Thread.sleep(100);

        // Verify
        verify(udpService, times(1)).receiveAndForwardPacket(testPort, SensorType.HUMIDITY);
    }

    @Test
    public void testRun_withException() throws Exception {
        int testPort = 9999;
        when(portProperties.getHumiditySensor()).thenReturn(testPort);

        // Make the udpService throw an exception when receiveAndForwardPacket is called
        doThrow(new IOException("Test exception")).when(udpService).receiveAndForwardPacket(testPort, SensorType.HUMIDITY);

        humiditySensorListener.run();

        // Wait a short time to let the new thread start
        Thread.sleep(100);

        // Verify that the udpService's receiveAndForwardPacket method was called with the correct parameters
        verify(udpService, times(1)).receiveAndForwardPacket(testPort, SensorType.HUMIDITY);
    }
}
