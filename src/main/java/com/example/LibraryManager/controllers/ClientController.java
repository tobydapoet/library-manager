package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.ClientUpdateRequest;
import com.example.LibraryManager.dtos.responses.ClientResponse;
import com.example.LibraryManager.mappers.ClientMapper;
import com.example.LibraryManager.services.ClientService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/search")
    public java.util.List<ClientResponse> searchClient(@RequestParam String keyword,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return clientMapper.toResponses(clientService.searchClients(keyword, page, size));
    }

    @GetMapping("/{id}")
    public ClientResponse getClient(@PathVariable String id) {
        return clientMapper.toResponse(clientService.getClient(id));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ClientResponse update(@PathVariable String id, @RequestPart("req") ClientUpdateRequest req) {
        return clientMapper.toResponse(clientService.update(id, req));
    }
}
