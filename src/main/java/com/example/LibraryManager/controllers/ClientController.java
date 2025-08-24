package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.requests.client.ClientUpdateRequest;
import com.example.LibraryManager.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/search")
    public Iterable<Client> searchClient(@RequestParam String keyword,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return clientService.searchClients(keyword, page, size);
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable String id) {
        return clientService.getClient(id);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Client update(@PathVariable String id, @RequestPart("req") ClientUpdateRequest req) {
        return clientService.update(id, req);
    }
}
