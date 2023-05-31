package com.merko.bilstudy.leitner;

import android.util.Log;

import com.merko.bilstudy.data.BilStudyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RoomLeitnerSource extends LeitnerSource {
    @Override
    public boolean save() {
        return true;
    }

    @Override
    protected boolean loadImpl() {
        return true;
    }

    @Override
    protected boolean unloadImpl() {
        return true;
    }

    @Override
    public CompletableFuture<LeitnerContainer> getContainer(UUID id) {
        return CompletableFuture.supplyAsync(() -> BilStudyDatabase.getInstance().leitnerDao().getContainer(id));
    }

    @Override
    public CompletableFuture<LeitnerContainer[]> getContainers(LeitnerContainer container) {
        return CompletableFuture.supplyAsync(() -> {
            if(container.type == LeitnerContainerType.BOX) {
                return new LeitnerContainer[0];
            }
            return BilStudyDatabase.getInstance().leitnerDao().getContainers(container.objectIds.toArray(new UUID[0]));
        });
    }

    @Override
    public CompletableFuture<LeitnerContainer[]> getAllContainers() {
        return CompletableFuture.supplyAsync(() -> BilStudyDatabase.getInstance().leitnerDao().getAllContainers());
    }

    @Override
    public CompletableFuture<UUID> putContainer(LeitnerContainer container) {
        return CompletableFuture.supplyAsync(() -> {
            container.uuid = UUID.randomUUID();
            BilStudyDatabase.getInstance().leitnerDao().putContainer(container);
            return container.uuid;
        });
    }

    @Override
    public CompletableFuture<UUID[]> putContainers(LeitnerContainer... containers) {
        return CompletableFuture.supplyAsync(() -> {
            UUID[] ids = new UUID[containers.length];
            for(int i = 0; i < containers.length; i++) {
                containers[i].uuid = UUID.randomUUID();
                ids[i] = containers[i].uuid;
            }
            BilStudyDatabase.getInstance().leitnerDao().putContainers(containers);
            return ids;
        });
    }

    @Override
    public CompletableFuture<Void> updateContainer(LeitnerContainer container) {
        return CompletableFuture.runAsync(() -> {
           LeitnerContainerEntity entity = BilStudyDatabase.getInstance().leitnerDao().getContainerEntity(container.uuid);
           entity.name = container.name;
           entity.tags = container.tags;
           entity.objectIds = container.objectIds;
           BilStudyDatabase.getInstance().leitnerDao().updateContainer(entity);
        });
    }

    @Override
    public CompletableFuture<Void> deleteContainer(UUID id) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().leitnerDao().deleteContainer(id));
    }

    @Override
    public CompletableFuture<Void> deleteContainers(UUID... ids) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().leitnerDao().deleteContainers(ids));
    }

    @Override
    public CompletableFuture<LeitnerQuestion> getQuestion(UUID id) {
        return CompletableFuture.supplyAsync(() -> BilStudyDatabase.getInstance().leitnerDao().getQuestion(id));
    }

    @Override
    public CompletableFuture<LeitnerQuestion[]> getQuestions(UUID... ids) {
        return CompletableFuture.supplyAsync(() -> BilStudyDatabase.getInstance().leitnerDao().getQuestions(ids));
    }

    @Override
    public CompletableFuture<LeitnerQuestion[]> getQuestions(LeitnerContainer container) {
        return CompletableFuture.supplyAsync(() -> {
            if(container.type == LeitnerContainerType.FOLDER) {
                return new LeitnerQuestion[0];
            }
            List<UUID> ids = container.objectIds;
            LeitnerQuestion[] questions = BilStudyDatabase.getInstance().leitnerDao().getQuestions(container.objectIds.toArray(new UUID[0]));
            HashMap<UUID, LeitnerQuestion> map = new HashMap<>();
            for(LeitnerQuestion q: questions) {
                map.put(q.uuid, q);
            }
            LeitnerQuestion[] ordered = new LeitnerQuestion[ids.size()];
            for(int i = 0; i < ids.size(); i++) {
                ordered[i] = map.get(ids.get(i));
            }
            return ordered;
        });
    }

    @Override
    public CompletableFuture<UUID> putQuestion(LeitnerQuestion question) {
        return CompletableFuture.supplyAsync(() -> {
            question.uuid = UUID.randomUUID();
            BilStudyDatabase.getInstance().leitnerDao().putQuestion(question);
            LeitnerContainerEntity entity = BilStudyDatabase.getInstance().leitnerDao().getContainerEntity(question.containerId);
            entity.objectIds = new ArrayList<>(entity.objectIds);
            entity.objectIds.add(question.uuid);
            try {
                BilStudyDatabase.getInstance().leitnerDao().updateContainer(entity);
            } catch (Exception e) {
                Log.d(toString(), e.getLocalizedMessage());
            }
            return question.uuid;
        });
    }

    @Override
    public CompletableFuture<UUID[]> putQuestions(LeitnerQuestion... questions) {
        return CompletableFuture.supplyAsync(() -> {
            UUID[] ids = new UUID[questions.length];
            for(int i = 0; i < questions.length; i++) {
                questions[i].uuid = UUID.randomUUID();
                ids[i] = questions[i].uuid;
            }
            BilStudyDatabase.getInstance().leitnerDao().putQuestions(questions);
            return ids;
        });
    }

    @Override
    public CompletableFuture<Void> deleteQuestion(UUID id) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().leitnerDao().deleteQuestion(id));
    }

    @Override
    public CompletableFuture<Void> deleteQuestions(UUID... ids) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().leitnerDao().deleteQuestions(ids));
    }
}
