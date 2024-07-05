package org.curso;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Random;

public class GeradorVendas {

    private static Random rand = new Random();
    private static Long operacao = 0L;
    private static BigDecimal valorIngresso = BigDecimal.valueOf(500);

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VendaSerializer.class.getName());

        KafkaProducer<String, Venda> producer = new KafkaProducer(props);

        while(true){
            Venda venda = geraVenda();

            ProducerRecord<String, Venda> record = new ProducerRecord("vendaIngressos", venda);
            producer.send(record);
            Thread.sleep(200);
        }
    }

    private static Venda geraVenda() {
        long cliente = rand.nextLong();
        int qtdeIngressos = rand.nextInt(10);
        double valorTotal = rand.nextDouble() * 1000;
        return new Venda(operacao++, cliente, qtdeIngressos,valorIngresso.multiply(BigDecimal.valueOf(qtdeIngressos)));
    }
}