
package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {

    public static void main(String[] args) {

        var fraudService = new FraudDetectorService();

        var service = new KafkaService(FraudDetectorService.class.getSimpleName(),"ECOMMERCE_NEW_ORDER", fraudService::parse);

        service.run();

    }

    private void parse(ConsumerRecord<String, String> records) {

        System.out.println("---------------------------------------");
        System.out.println("Process new order, checking froud");
        System.out.printf("Sucesso enviando.. Key: %s - Topc: %s - Partition: %s - Offset: %s - Timestamp: %s%n",
                records.key(), records.topic(), records.partition(), records.offset(), records.timestamp());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

