package org.curso;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class ProcessadorVendas {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VendaDeserializer.class.getName());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, Venda> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Arrays.asList("testeJava"));

        while(true){
            ConsumerRecords<String, Venda> records = consumer.poll(Duration.ofSeconds(200));
            for(ConsumerRecord<String, Venda> record : records){
                Venda venda = record.value();
                if(new Random().nextBoolean()){
                    venda.setStatus("APROVADA");
                }else {
                    venda.setStatus("REPROVADA");
                }
                Thread.sleep(500);
                System.out.println(venda);
            }
        }
    }
}