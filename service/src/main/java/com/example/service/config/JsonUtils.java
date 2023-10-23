package com.example.service.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
public final class JsonUtils {
    private static final ObjectMapper mapper = createObjectMapper();

    public static String marshal(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static byte[] marshalToBytes(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsBytes(obj);
    }

    public static <T> T unmarshal(String s, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(s, clazz);
    }

    public static <T> T unmarshal(String s, TypeReference<T> type) throws JsonProcessingException {
        return mapper.readValue(s, type);
    }

    public static <T> T unmarshal(String s, JavaType type) throws JsonProcessingException {
        return mapper.readValue(s, type);
    }
    public static ObjectMapper createObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        mapper.setDateFormat(new ISO8601DateFormat());
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        var module = new SimpleModule();
        module.addDeserializer(String.class, new StringTrimDeserializer());
        module.addDeserializer(Instant.class, new InstantDeserializer());
        module.addSerializer(Instant.class, new InstantSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    private static class InstantDeserializer extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return Instant.parse(p.getValueAsString());
        }
    }

    private static class InstantSerializer extends StdSerializer<Instant> {
        private InstantSerializer() {
            super(Instant.class);
        }

        @Override
        public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.truncatedTo(ChronoUnit.SECONDS).toString());
        }
    }

    private static class StringTrimDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            return p.getValueAsString().trim();
        }
    }
}
