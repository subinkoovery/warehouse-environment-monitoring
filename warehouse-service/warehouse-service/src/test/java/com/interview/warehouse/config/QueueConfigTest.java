package com.interview.warehouse.config;

import com.interview.warehouse.model.SensorType;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static com.interview.warehouse.constants.Constants.SENSOR_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QueueConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testHumidityQueue() {
        Queue humidityQueue = (Queue) applicationContext.getBean("humidityQueue");
        assertThat(humidityQueue.getName()).isEqualTo("HumidityQueue");
        assertThat(humidityQueue.isDurable()).isTrue();
    }

    @Test
    public void testTemperatureQueue() {
        Queue temperatureQueue = (Queue) applicationContext.getBean("temperatureQueue");
        assertThat(temperatureQueue.getName()).isEqualTo("TemperatureQueue");
        assertThat(temperatureQueue.isDurable()).isTrue();
    }

    @Test
    public void testSensorTopicExchange() {
        TopicExchange sensorTopicExchange = (TopicExchange) applicationContext.getBean("sensorTopicExchange");
        assertThat(sensorTopicExchange.getName()).isEqualTo(SENSOR_TOPIC);
    }

    @Test
    public void testHumiditySensorBinding() {
        Binding humiditySensorBinding = (Binding) applicationContext.getBean("humiditySensorBinding");
        assertThat(humiditySensorBinding.getRoutingKey()).isEqualTo(SensorType.HUMIDITY.name());
        assertThat(humiditySensorBinding.getExchange()).isEqualTo(SENSOR_TOPIC);
    }

    @Test
    public void testTemperatureSensorBinding() {
        Binding temperatureSensorBinding = (Binding) applicationContext.getBean("temperatureSensorBinding");
        assertThat(temperatureSensorBinding.getRoutingKey()).isEqualTo(SensorType.TEMPERATURE.name());
        assertThat(temperatureSensorBinding.getExchange()).isEqualTo(SENSOR_TOPIC);
    }

    @Test
    public void testRabbitTemplate() {
        RabbitTemplate rabbitTemplate = (RabbitTemplate) applicationContext.getBean(RabbitTemplate.class);
        assertThat(rabbitTemplate).isNotNull();
        assertThat(rabbitTemplate.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }
}
