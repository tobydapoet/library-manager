package com.example.LibraryManager.requests.location;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationRequest {
    @NotNull
    private Integer floor;

}
