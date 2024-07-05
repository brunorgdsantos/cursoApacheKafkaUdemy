package org.curso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class VendaSerializer implements Serializer<Venda> {
    @Override
    public byte[] serialize(String s, Venda venda) {
        try {
            return new ObjectMapper().writeValueAsBytes(venda);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
