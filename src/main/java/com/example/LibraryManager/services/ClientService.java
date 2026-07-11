package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;


import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.repositories.ClientRepository;
import com.example.LibraryManager.requests.client.ClientUpdateRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    private final UploadService uploadService;

    @Cacheable(value = "uploadCache", key = "'client:' + #id")
    public Client getClient(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @CacheEvict(value = "uploadCache", key = "'client:' + #id")
    public Client update(String id, ClientUpdateRequest req) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (req.getAddress() != null) {
            client.setAddress(req.getAddress());
        }
        if (req.getName() != null) {
            client.setName(req.getName());
        }
        if (req.getFile() != null) {
            if (client.getAvatar_url() != null) {
                uploadService.delete(client.getAvatar_url());
            }
            String uploadedImage = uploadService.upload(req.getFile(), "client");
            client.setAvatar_url(uploadedImage);
        }
        if (req.getDob() != null) {
            client.setDob(req.getDob());
        }
        if (req.getPhone() != null) {
            client.setPhone(req.getPhone());
        }
        if (req.getFile() != null) {
            String savedFile = uploadService.upload(req.getFile(), "client");
            client.setAvatar_url(savedFile);
        }
        Client savedClient = clientRepository.save(client);
        return getClient(savedClient.getId());
    }


    public Page<Client> getAllClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAll(pageable);
    }

    public Page<Client> searchClients(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository
                .findByPhoneContainingIgnoreCaseOrNameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(keyword, keyword, keyword, pageable);
    }
}
