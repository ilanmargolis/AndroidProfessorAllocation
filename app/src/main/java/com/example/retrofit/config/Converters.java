package com.example.retrofit.config;

import androidx.room.TypeConverter;

import com.example.retrofit.model.Course;
import com.example.retrofit.model.Departament;
import com.example.retrofit.model.Professor;
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
    public static Departament jsonToListDepartament(String value) {
        objectMapper = new ObjectMapper();
        Departament arr = new Departament();
        try {
            arr = objectMapper.readValue(value, Departament.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Course value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Course jsonToListCourse(String value) {
        objectMapper = new ObjectMapper();
        Course arr = new Course();
        try {
            arr = objectMapper.readValue(value, Course.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Professor value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Professor jsonToListProfessor(String value) {
        objectMapper = new ObjectMapper();
        Professor arr = new Professor();
        try {
            arr = objectMapper.readValue(value, Professor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}