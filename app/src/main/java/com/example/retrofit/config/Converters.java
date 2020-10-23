package com.example.retrofit.config;

import androidx.room.TypeConverter;

import com.example.retrofit.model.Departament;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Converters {

    private static ObjectMapper objectMapper;

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Departament value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Departament jsonToList(String value) {
        objectMapper = new ObjectMapper();
        Departament arr = new Departament();
        try {
            arr = objectMapper.readValue(value, Departament.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}