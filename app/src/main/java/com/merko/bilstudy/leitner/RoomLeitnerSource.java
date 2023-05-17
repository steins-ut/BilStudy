package com.merko.bilstudy.leitner;

import com.merko.bilstudy.data.BilStudyDatabase;

import java.util.UUID;

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
    public LeitnerContainer getContainer(UUID id) {
        return BilStudyDatabase.getInstance().leitnerDao().getContainer(id);
    }

    @Override
    public LeitnerContainer[] getContainers(LeitnerContainer container) {
        if(container.type == LeitnerContainerType.BOX) {
            return new LeitnerContainer[0];
        }
        return BilStudyDatabase.getInstance().leitnerDao().getContainers(container.objectIds.toArray(new UUID[0]));
    }

    @Override
    public LeitnerContainer[] getAllContainers() {
        return BilStudyDatabase.getInstance().leitnerDao().getAllContainers();
    }

    @Override
    public UUID putContainer(LeitnerContainer container) {
        container.uuid = UUID.randomUUID();
        BilStudyDatabase.getInstance().leitnerDao().putContainer(container);
        return container.uuid;
    }

    @Override
    public UUID[] putContainers(LeitnerContainer... containers) {
        UUID[] ids = new UUID[containers.length];
        for(int i = 0; i < containers.length; i++) {
            UUID id = UUID.randomUUID();
            containers[i].uuid = id;
            ids[i] = id;
        }
        BilStudyDatabase.getInstance().leitnerDao().putContainers(containers);
        return ids;
    }

    @Override
    public void deleteContainer(UUID id) {
        BilStudyDatabase.getInstance().leitnerDao().deleteContainer(id);
    }

    @Override
    public void deleteContainers(UUID... ids) {
        BilStudyDatabase.getInstance().leitnerDao().deleteContainers(ids);
    }

    @Override
    public LeitnerQuestion getQuestion(UUID id) {
        return BilStudyDatabase.getInstance().leitnerDao().getQuestion(id);
    }

    @Override
    public LeitnerQuestion[] getQuestions(UUID... ids) {
        return BilStudyDatabase.getInstance().leitnerDao().getQuestions(ids);
    }

    @Override
    public LeitnerQuestion[] getQuestions(LeitnerContainer container) {
        if(container.type == LeitnerContainerType.FOLDER) {
            return new LeitnerQuestion[0];
        }
        return BilStudyDatabase.getInstance().leitnerDao().getQuestions(container.objectIds.toArray(new UUID[0]));
    }

    @Override
    public void deleteQuestion(UUID id) {
        BilStudyDatabase.getInstance().leitnerDao().deleteQuestion(id);
    }

    @Override
    public void deleteQuestions(UUID... ids) {
        BilStudyDatabase.getInstance().leitnerDao().deleteQuestions(ids);
    }
}
