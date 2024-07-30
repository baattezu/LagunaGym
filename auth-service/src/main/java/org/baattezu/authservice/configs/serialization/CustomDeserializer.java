package org.baattezu.authservice.configs.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.baattezu.authservice.dto.EmailMessage;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<EmailMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Никакой специальной настройки не требуется
    }

    @Override
    public EmailMessage deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, EmailMessage.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing EmailMessage", e);
        }
    }

    @Override
    public void close() {
        // Никаких действий не требуется
    }
}
