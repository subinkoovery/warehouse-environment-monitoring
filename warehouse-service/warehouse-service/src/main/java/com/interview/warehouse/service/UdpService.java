package com.interview.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.warehouse.model.SensorMeasurementDetail;
import com.interview.warehouse.model.SensorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.interview.warehouse.constants.Constants.SENSOR_TOPIC;

/**
 * Class to receive udp packets and send to msg broker
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UdpService {

    private static final int BUFFER_SIZE = 1024;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    /**
     * Method to receive the UDP packets and push to message queue
     *
     * @param port UDP port number
     * @throws IOException IO exception
     */
    public void receiveAndForwardPacket(int port, SensorType sensorType) throws IOException {
        try (DatagramSocket socket = createSocket(port)) {
            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());

                SensorMeasurementDetail sensorMeasurementDetail = objectMapper.readValue(receivedMessage, SensorMeasurementDetail.class);

                // Forward the message to RabbitMQ
                rabbitTemplate.convertAndSend(SENSOR_TOPIC, sensorType.name(), sensorMeasurementDetail);
                log.info("Message successfully pushed to broker with routing-key:'{}' | msg:'{}'",
                        sensorType.name(), sensorMeasurementDetail);
            }
        }
    }

    protected DatagramSocket createSocket(int port) throws IOException {
        return new DatagramSocket(port);
    }
}
