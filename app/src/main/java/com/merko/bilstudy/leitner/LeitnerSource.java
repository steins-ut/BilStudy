package com.merko.bilstudy.leitner;

import com.merko.bilstudy.data.AbstractSource;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class LeitnerSource extends AbstractSource {
        public abstract CompletableFuture<Boolean> hasContainer(UUID id);
        public abstract CompletableFuture<LeitnerContainer> getContainer(UUID id);
        public abstract CompletableFuture<LeitnerContainer[]> getContainers(LeitnerContainer container);
        public abstract CompletableFuture<LeitnerContainer[]> getAllContainers();
        public abstract CompletableFuture<UUID> putContainer(LeitnerContainer container);
        public abstract CompletableFuture<UUID[]> putContainers(LeitnerContainer... containers);
        public abstract CompletableFuture<Void> updateContainer(LeitnerContainer container);
        public abstract CompletableFuture<Void> deleteContainer(UUID id);
        public abstract CompletableFuture<Void> deleteContainers(UUID... ids);
        public abstract CompletableFuture<LeitnerQuestion> getQuestion(UUID id);
        public abstract CompletableFuture<LeitnerQuestion[]> getQuestions(UUID... ids);
        public abstract CompletableFuture<LeitnerQuestion[]> getQuestions(LeitnerContainer container);
        public abstract CompletableFuture<UUID> putQuestion(LeitnerQuestion question);
        public abstract CompletableFuture<UUID[]> putQuestions(LeitnerQuestion... questions);
        public abstract CompletableFuture<Void> deleteQuestion(UUID id);
        public abstract CompletableFuture<Void> deleteQuestions(UUID... ids);
}
