package com.interview.central_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SensorType {
    HUMIDITY(50),
    TEMPERATURE(35);

    private float threshold;
}
