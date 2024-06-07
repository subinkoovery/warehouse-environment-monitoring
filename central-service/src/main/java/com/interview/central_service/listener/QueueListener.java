package com.interview.central_service.listener;

import com.interview.central_service.model.SensorMeasurementDetail;
import com.interview.central_service.enums.SensorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.interview.central_service.enums.SensorType.HUMIDITY;
import static com.interview.central_service.enums.SensorType.TEMPERATURE;

/**
 * Class to listen to queue
 */
@Slf4j
@Component
public class QueueListener {

    @RabbitListener(queues = "HumidityQueue")
    public void humidityListener(SensorMeasurementDetail sensorMeasurementDetail) {

        if (sensorMeasurementDetail.getValue() > HUMIDITY.getThreshold()) {
            triggerAlarm(HUMIDITY, sensorMeasurementDetail);
        }


    }

    @RabbitListener(queues = "TemperatureQueue")
    public void temperatureListener(SensorMeasurementDetail sensorMeasurementDetail) {

        if (sensorMeasurementDetail.getValue() > TEMPERATURE.getThreshold()) {
            triggerAlarm(TEMPERATURE, sensorMeasurementDetail);
        }

    }

    private void triggerAlarm(SensorType sensorType, SensorMeasurementDetail sensorMeasurementDetail) {
        log.info("ALARM TRIGGERED for '{}' sensor. Current reading:'{}' [threshold -'{}']", sensorType,
                sensorMeasurementDetail.getValue(), sensorType.getThreshold());
    }

}
