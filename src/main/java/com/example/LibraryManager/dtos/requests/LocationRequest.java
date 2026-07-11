package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationRequest {
    @NotNull
    private Integer floor;

}
