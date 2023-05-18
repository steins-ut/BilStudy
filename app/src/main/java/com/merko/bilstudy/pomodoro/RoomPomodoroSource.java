package com.merko.bilstudy.pomodoro;

import com.merko.bilstudy.data.BilStudyDatabase;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RoomPomodoroSource extends PomodoroSource {
    @Override
    public CompletableFuture<PomodoroPreset> getPreset(UUID id) {
        return CompletableFuture.supplyAsync(() -> BilStudyDatabase.getInstance().pomodoroDao().getPreset(id));
    }

    @Override
    public CompletableFuture<PomodoroPreset[]> getAllPresets() {
        return CompletableFuture.supplyAsync(() -> BilStudyDatabase.getInstance().pomodoroDao().getAllPresets());
    }

    @Override
    public CompletableFuture<UUID> putPreset(PomodoroPreset preset) {
        return CompletableFuture.supplyAsync(() -> {
            preset.uuid = UUID.randomUUID();
            BilStudyDatabase.getInstance().pomodoroDao().putPreset(preset);
            return preset.uuid;
        });
    }

    @Override
    public CompletableFuture<Void> deletePreset(UUID id) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().pomodoroDao().deletePreset(id));
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
