package com.merko.bilstudy.leitner;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = LeitnerContainerEntity.TABLE_NAME,
        indices = {@Index(value = {"uuid"}, unique = true),
                @Index(value = {"rowid"}, unique = true)})
public class LeitnerContainerEntity extends LeitnerContainer {
    public static final String TABLE_NAME = "leitner_containers";

    @PrimaryKey(autoGenerate = true)
    public long rowid;
}
