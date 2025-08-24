package com.example.LibraryManager.requests.location;

import jakarta.validation.constraints.NotNull;

public class LocationRequest {
    @NotNull
    private Integer floor;

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
