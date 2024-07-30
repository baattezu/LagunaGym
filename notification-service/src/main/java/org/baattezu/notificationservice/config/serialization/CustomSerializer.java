package org.baattezu.notificationservice.config.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.baattezu.notificationservice.config.EmailMessage;

import java.util.Map;

public class CustomSerializer implements Serializer<EmailMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Никакой специальной настройки не требуется
    }

    @Override
    public byte[] serialize(String topic, EmailMessage data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing EmailMessage", e);
        }
    }

    @Override
    public void close() {
        // Никаких действий не требуется
    }
}
