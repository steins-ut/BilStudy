package com.merko.bilstudy.pomodoro;

import java.util.UUID;

/**
 * Class for storing pomodoro presets
 */
public class PomodoroPreset {
    /** Identifier used for identification in storage */
    public UUID uuid;
    /** Name of the preset */
    public String name;
    /** Study minutes of the preset */
    public int studyMinutes;
    /** Break minutes of the preset */
    public int breakMinutes;

    public PomodoroPreset() {}

    public PomodoroPreset(UUID uuid, String name, int studyMinutes, int breakMinutes) {
        this.uuid = uuid;
        this.name = name;
        this.studyMinutes = studyMinutes;
        this.breakMinutes = breakMinutes;
    }
}
