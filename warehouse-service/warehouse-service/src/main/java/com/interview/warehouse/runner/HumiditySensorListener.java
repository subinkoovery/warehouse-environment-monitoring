package com.interview.warehouse.runner;

import com.interview.warehouse.config.PortProperties;
import com.interview.warehouse.model.SensorType;
import com.interview.warehouse.service.UdpService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
/**
 * Class to listen the humidity sensor
 */
@RequiredArgsConstructor
@Component
public class HumiditySensorListener implements CommandLineRunner {

    private final UdpService udpService;
    private final PortProperties portProperties;


    @Override
    public void run(String... args) throws Exception {
        int port = portProperties.getHumiditySensor(); // UDP port

        // Start receiving and forwarding UDP packets
        new Thread(() -> {
            try {
                udpService.receiveAndForwardPacket(port, SensorType.HUMIDITY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
