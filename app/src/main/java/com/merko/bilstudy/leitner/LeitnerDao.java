package com.merko.bilstudy.leitner;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.UUID;

@Dao
public abstract class LeitnerDao {
    @Query("SELECT * FROM " + LeitnerContainerEntity.TABLE_NAME + " WHERE uuid = :id")
    public abstract LeitnerContainer getContainer(UUID id);
    @Query("SELECT * FROM " + LeitnerContainerEntity.TABLE_NAME + " WHERE uuid IN (:ids)")
    public abstract LeitnerContainer[] getContainers(UUID... ids);
    @Query("SELECT * FROM " + LeitnerContainerEntity.TABLE_NAME)
    public abstract LeitnerContainer[] getAllContainers();
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = LeitnerContainerEntity.class)
    public abstract void putContainer(LeitnerContainer container);
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = LeitnerContainerEntity.class)
    public abstract void putContainers(LeitnerContainer... containers);
    @Query("DELETE FROM " + LeitnerContainerEntity.TABLE_NAME + " WHERE uuid = :id")
    public abstract void deleteContainer(UUID id);
    @Query("DELETE FROM " + LeitnerContainerEntity.TABLE_NAME + " WHERE uuid IN (:ids)")
    public abstract void deleteContainers(UUID... ids);
    @Query("SELECT * FROM " + LeitnerQuestionEntity.TABLE_NAME + " WHERE uuid = :id")
    public abstract LeitnerQuestion getQuestion(UUID id);
    @Query("SELECT * FROM " + LeitnerQuestionEntity.TABLE_NAME + " WHERE uuid IN (:ids)")
    public abstract LeitnerQuestion[] getQuestions(UUID... ids);
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = LeitnerQuestionEntity.class)
    public abstract void putQuestion(LeitnerQuestion question);
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = LeitnerQuestionEntity.class)
    public abstract void putQuestions(LeitnerQuestion... questions);
    @Query("DELETE FROM " + LeitnerQuestionEntity.TABLE_NAME + " WHERE uuid = :id")
    public abstract void deleteQuestion(UUID id);
    @Query("DELETE FROM " + LeitnerQuestionEntity.TABLE_NAME + " WHERE uuid IN (:ids)")
    public abstract void deleteQuestions(UUID... ids);
}
