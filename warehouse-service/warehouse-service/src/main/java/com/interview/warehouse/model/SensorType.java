package com.interview.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SensorType {
    HUMIDITY(50),
    TEMPERATURE(35);

    private float threshold;
}
