package com.interview.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.warehouse.model.SensorMeasurementDetail;
import com.interview.warehouse.model.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static com.interview.warehouse.constants.Constants.SENSOR_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UdpServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UdpService udpService;

    @Captor
    private ArgumentCaptor<SensorMeasurementDetail> sensorMeasurementDetailCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        udpService = new UdpService(rabbitTemplate, objectMapper) {
            @Override
            protected DatagramSocket createSocket(int port) throws IOException {
                return mockSocket;
            }
        };
    }

    @Mock
    private DatagramSocket mockSocket;

    @Test
    public void testReceiveAndForwardPacket() throws IOException {
        int port = 9999;
        SensorType sensorType = SensorType.HUMIDITY;
        String testMessage = "{\"sensorId\":\"1234\",\"value\":56.7}";

        // Mock DatagramSocket behavior
        DatagramPacket packet = new DatagramPacket(testMessage.getBytes(), testMessage.length());
        doAnswer(invocation -> {
            DatagramPacket receivedPacket = invocation.getArgument(0);
            receivedPacket.setData(packet.getData());
            receivedPacket.setLength(packet.getLength());
            return null;
        }).when(mockSocket).receive(any(DatagramPacket.class));

        // Mock ObjectMapper behavior
        SensorMeasurementDetail sensorMeasurementDetail = new SensorMeasurementDetail();
        sensorMeasurementDetail.setSensorId("1234");
        sensorMeasurementDetail.setValue(56.7f);
        when(objectMapper.readValue(testMessage, SensorMeasurementDetail.class)).thenReturn(sensorMeasurementDetail);

        // Execute the method in a separate thread to simulate the while(true) loop
        new Thread(() -> {
            try {
                udpService.receiveAndForwardPacket(port, sensorType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Verify that the message was forwarded to RabbitMQ
        verify(rabbitTemplate, timeout(1000).atLeast(1)).convertAndSend(eq(SENSOR_TOPIC), eq(sensorType.name()), sensorMeasurementDetailCaptor.capture());
        SensorMeasurementDetail capturedDetail = sensorMeasurementDetailCaptor.getValue();
        assertThat(capturedDetail.getSensorId()).isEqualTo("1234");
        assertThat(capturedDetail.getValue()).isEqualTo(56.7f);
    }
}
