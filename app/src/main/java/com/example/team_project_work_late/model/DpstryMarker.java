package com.example.team_project_work_late.model;

import net.daum.mf.map.api.MapPOIItem;

public class DpstryMarker extends MapPOIItem {
    private BcyclDpstryData_responseBody_items item;

    public BcyclDpstryData_responseBody_items getItem() {
        return item;
    }

    public void setItem(BcyclDpstryData_responseBody_items item) {
        this.item = item;
    }
}
