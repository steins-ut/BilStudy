package com.merko.bilstudy.leitner;

import java.util.List;
import java.util.UUID;

public class LeitnerContainer {
    public String name;
    public UUID uuid;
    public UUID parentUuid;
    public LeitnerContainerType type;
    public UUID iconId;
    public List<UUID> objectIds;

    public LeitnerContainer() {}

    public LeitnerContainer(String name, UUID uuid, LeitnerContainerType type, UUID iconId, List<UUID> objectIds) {
        this.name = name;
        this.uuid = uuid;
        this.type = type;
        this.iconId = iconId;
        this.objectIds = objectIds;
    }
}
