package com.merko.bilstudy.data;

import android.util.Log;

import androidx.room.TypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RoomTypeConverters {
    @TypeConverter
    public static String uuidToString(UUID id) {
        return id.toString();
    }

    @TypeConverter
    public static UUID stringToUuid(String str) {
        if(str == null) {
            return null;
        }
        return UUID.fromString(str);
    }

    @TypeConverter
    public static String stringListToJson(List<String> list) {
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            Log.e(RoomTypeConverters.class.toString(), e.getMessage());
            return "";
        }
    }

    @TypeConverter
    public static List<String> jsonToStringList(String str) {
        try {
            return Arrays.asList(new ObjectMapper().readValue(str, String[].class));
        } catch (JsonProcessingException e) {
            Log.e(RoomTypeConverters.class.toString(), e.getMessage());
            return null;
        }
    }

    @TypeConverter
    public static String integerListToJson(List<Integer> list) {
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            Log.e(RoomTypeConverters.class.toString(), e.getMessage());
            return "";
        }
    }

    @TypeConverter
    public static List<Integer> jsonToIntegerList(String str) {
        try {
            return Arrays.asList(new ObjectMapper().readValue(str, Integer[].class));
        } catch (JsonProcessingException e) {
            Log.e(RoomTypeConverters.class.toString(), e.getMessage());
            return null;
        }
    }

    @TypeConverter
    public static String uuidListToJson(List<UUID> str) {
        try {
            return new ObjectMapper().writeValueAsString(str);
        } catch (JsonProcessingException e) {
            Log.e(RoomTypeConverters.class.toString(), e.getMessage());
            return "";
        }
    }

    @TypeConverter
    public static List<UUID> jsonToUUIDList(String str) {
        try {
            return Arrays.asList(new ObjectMapper().readValue(str, UUID[].class));
        } catch (JsonProcessingException e) {
            Log.e(RoomTypeConverters.class.toString(), e.getMessage());
            return null;
        }
    }
}
