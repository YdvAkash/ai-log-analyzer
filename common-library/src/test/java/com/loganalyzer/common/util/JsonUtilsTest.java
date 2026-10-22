package com.loganalyzer.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class JsonUtilsTest {

    private final JsonUtils jsonUtils = new JsonUtils(new ObjectMapper());

    @Test
    void serializesAndDeserializesAString() {
        String json = jsonUtils.toJson("sample log");

        assertEquals("\"sample log\"", json);
        assertEquals("sample log", jsonUtils.fromJson(json, String.class));
    }

    @Test
    void reportsSerializationFailuresWithOriginalCause() {
        Object unsupported = new Object() {
            public Object getSelf() {
                return this;
            }
        };

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> jsonUtils.toJson(unsupported));

        assertEquals("Serialization Error", exception.getMessage());
        assertTrue(exception.getCause() != null);
    }

    @Test
    void reportsDeserializationFailuresWithOriginalCause() {
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> jsonUtils.fromJson("{", String.class));

        assertEquals("Deserialization Error", exception.getMessage());
        assertTrue(exception.getCause() != null);
    }
}
