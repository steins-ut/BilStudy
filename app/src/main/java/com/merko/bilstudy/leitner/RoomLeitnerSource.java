package com.merko.bilstudy.leitner;

import com.merko.bilstudy.data.BilStudyDatabase;

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
            return BilStudyDatabase.getInstance().leitnerDao().getQuestions(container.objectIds.toArray(new UUID[0]));
        });
    }

    @Override
    public CompletableFuture<UUID> putQuestion(LeitnerQuestion question) {
        return CompletableFuture.supplyAsync(() -> {
            question.uuid = UUID.randomUUID();
            BilStudyDatabase.getInstance().leitnerDao().putQuestion(question);
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
