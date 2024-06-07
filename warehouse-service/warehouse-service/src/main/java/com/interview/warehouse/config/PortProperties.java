package com.interview.warehouse.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app.port")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortProperties {
    private int humiditySensor;
    private int temperatureSensor;
}
