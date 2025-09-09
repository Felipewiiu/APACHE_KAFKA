
package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());

        for (var i = 0; i < 100; i++) {

            var key = UUID.randomUUID().toString();
            var value = "%s,1221,530".formatted(key);
            var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, value);

            Callback callback = (data, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                    return;
                }
                System.out.printf("Sucesso enviando.. Topc: %s - Partition: %s - Offset: %s - Timestamp: %s%n",
                        data.topic(), data.partition(), data.offset(), data.timestamp());

            };

            var email = "var email = \"Thank you for your order! We are processing your order!";
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", UUID.randomUUID().toString(), email);

            producer.send(emailRecord, callback).get();
            producer.send(record, callback).get();

        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // É preciso serializar as strings
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
