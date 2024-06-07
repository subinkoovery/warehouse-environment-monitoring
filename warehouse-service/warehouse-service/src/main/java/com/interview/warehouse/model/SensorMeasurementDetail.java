package com.interview.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to hold sensor measurement detail
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorMeasurementDetail {

    private String sensorId;
    private float value;

}
