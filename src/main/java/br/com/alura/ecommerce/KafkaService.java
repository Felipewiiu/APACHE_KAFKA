package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaService {
    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    public KafkaService(String topic, ConsumerFunction parse) {
        this.parse = parse;

        this.consumer = new KafkaConsumer<>(properties());

        consumer.subscribe(List.of(topic));

    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return properties;
    }

    public void run() {
        while (true) {
            var record = consumer.poll(Duration.ofMillis(100));

            if (!record.isEmpty()) {
                System.out.printf("Encontrei %s registro", record.count());

                record.forEach(parse::consume);
            }
        }
    }
}
