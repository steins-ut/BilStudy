package com.merko.bilstudy.leitner;

import java.util.List;
import java.util.UUID;

public class LeitnerContainer {
    public String name;
    public List<String> tags;
    public UUID uuid;
    public UUID parentUuid;
    public LeitnerContainerType type;
    public UUID iconId;
    public List<UUID> objectIds;

    public LeitnerContainer() {}

    public LeitnerContainer(String name, List<String> tags, UUID uuid, UUID parentUuid, LeitnerContainerType type, UUID iconId, List<UUID> objectIds) {
        this.name = name;
        this.tags = tags;
        this.uuid = uuid;
        this.parentUuid = parentUuid;
        this.type = type;
        this.iconId = iconId;
        this.objectIds = objectIds;
    }
}
