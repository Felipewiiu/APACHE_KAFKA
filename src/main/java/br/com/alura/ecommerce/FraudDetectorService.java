
package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class FraudDetectorService {

    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        //preciso consumir de algum lugar
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER"));

        while (true) {


            //perguntar se tem mensagem
            var records = consumer.poll(Duration.ofMillis(100));

            //Quando se cria um consumer é preciso informar o grupo

            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " records");

                records.forEach(order -> {
                    System.out.println("---------------------------------------");
                    System.out.println("Process new order, checking froud");
                    System.out.printf("Sucesso enviando.. Key: %s - Topc: %s - Partition: %s - Offset: %s - Timestamp: %s%n",
                            order.key(), order.topic(), order.partition(), order.offset(), order.timestamp());

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Order processed");

                });
            }
        }


    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //Quando se cria um consumer é preciso informar o grupo
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());

        return properties;
    }

}
