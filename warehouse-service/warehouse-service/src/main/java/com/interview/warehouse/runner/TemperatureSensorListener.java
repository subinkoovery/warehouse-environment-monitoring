package com.interview.warehouse.runner;

import com.interview.warehouse.config.PortProperties;
import com.interview.warehouse.model.SensorType;
import com.interview.warehouse.service.UdpService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class to listen the temperature sensor
 */
@RequiredArgsConstructor
@Component
public class TemperatureSensorListener implements CommandLineRunner {

    private final UdpService udpService;
    private final PortProperties portProperties;


    @Override
    public void run(String... args) throws Exception {
        int port = portProperties.getTemperatureSensor(); // UDP port

        // Start receiving and forwarding UDP packets
        new Thread(() -> {
            try {
                udpService.receiveAndForwardPacket(port, SensorType.TEMPERATURE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
