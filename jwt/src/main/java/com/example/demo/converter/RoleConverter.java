package com.example.demo.converter;

import com.example.demo.domain.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Set;

@Converter
public class RoleConverter implements AttributeConverter<Set<Role>, String> {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(Set<Role> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        try {
            return MAPPER.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public Set<Role> convertToEntityAttribute(String dbData) {
        try {
            if (StringUtils.isBlank(dbData)) {
                return null;
            }
            return MAPPER.readValue(dbData, new TypeReference<Set<Role>>() {
            });
        } catch (IOException e) {
            return null;
        }
    }
}
