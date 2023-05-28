package com.merko.bilstudy.leitner;

import java.util.List;

public class LeitnerContainerFilter {

    public enum ShowType
    {
        ALL,
        BOXES_ONLY,
        FOLDERS_ONLY
    }

    public String name;
    public List<String> tags;
    public ShowType showType;
    public boolean hideSolved;

    public LeitnerContainerFilter(String name, List<String> tags, ShowType showType, boolean hideSolved) {
        this.name = name;
        this.tags = tags;
        this.showType = showType;
        this.hideSolved = hideSolved;
    }
}
