package com.example.LibraryManager.requests.bill;

import jakarta.validation.constraints.NotBlank;

public class BillRequest {
    @NotBlank
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
