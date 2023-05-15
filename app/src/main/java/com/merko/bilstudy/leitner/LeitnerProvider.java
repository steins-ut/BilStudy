package com.merko.bilstudy.leitner;

import com.merko.bilstudy.data.AbstractProvider;

import java.util.UUID;

public abstract class LeitnerProvider extends AbstractProvider {
        public abstract LeitnerContainer getContainer(UUID id);
        public abstract LeitnerContainer[] getContainers(LeitnerContainer container);
        public abstract LeitnerContainer[] getAllContainers();
        public abstract UUID putContainer(LeitnerContainer container);
        public abstract UUID[] putContainers(LeitnerContainer... containers);
        public abstract void deleteContainer(UUID id);
        public abstract void deleteContainers(UUID... ids);
        public abstract LeitnerQuestion getQuestion(UUID id);
        public abstract LeitnerQuestion[] getQuestions(UUID... ids);
        public abstract LeitnerQuestion[] getQuestions(LeitnerContainer container);
        public abstract void deleteQuestion(UUID id);
        public abstract void deleteQuestions(UUID... ids);
}
