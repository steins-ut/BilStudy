package com.merko.bilstudy.pomodoro;

import com.merko.bilstudy.data.BilStudyDatabase;

import java.util.UUID;

public class RoomPomodoroProvider extends PomodoroProvider {
    @Override
    public PomodoroPreset getPreset(UUID presetId) {
        return BilStudyDatabase.getInstance().pomodoroDao().getPreset(presetId);
    }

    @Override
    public PomodoroPreset[] getAllPresets() {
        return BilStudyDatabase.getInstance().pomodoroDao().getAllPresets();
    }

    @Override
    public UUID putPreset(PomodoroPreset preset) {
        if(preset.uuid != null) {
            if(BilStudyDatabase.getInstance().pomodoroDao().hasPreset(preset.uuid)) {
                deletePreset(preset.uuid);
            }
        }
        preset.uuid = UUID.randomUUID();
        BilStudyDatabase.getInstance().pomodoroDao().putPreset(preset);
        return preset.uuid;
    }

    @Override
    public void deletePreset(UUID presetId) {
        BilStudyDatabase.getInstance().pomodoroDao().deletePreset(presetId);
    }

    @Override
    protected boolean loadImpl() {
        return true;
    }

    @Override
    public boolean save() {
        return true;
    }

    @Override
    protected boolean unloadImpl() {
        return true;
    }
}
