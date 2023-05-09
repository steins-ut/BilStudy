package com.merko.bilstudy.data;

import androidx.room.TypeConverter;

import com.merko.bilstudy.pomodoro.PomodoroPreset;

import java.util.UUID;

public class RoomTypeConverters {
    @TypeConverter
    public static String uuidToString(UUID id) {
        return id.toString();
    }

    @TypeConverter
    public static UUID stringToUuid(String str) {
        return UUID.fromString(str);
    }
}
