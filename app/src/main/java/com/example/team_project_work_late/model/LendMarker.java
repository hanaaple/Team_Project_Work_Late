package com.example.team_project_work_late.model;

import net.daum.mf.map.api.MapPOIItem;

public class LendMarker extends MapPOIItem {
    private BookMarkItem item;

    public BookMarkItem getItem() {
        return item;
    }

    public void setItem(BookMarkItem item) {
        this.item = item;
    }
}
