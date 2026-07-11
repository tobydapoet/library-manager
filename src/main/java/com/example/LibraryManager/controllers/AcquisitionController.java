package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.AcquisitionCreateRequest;
import com.example.LibraryManager.dtos.responses.AcquisitionResponse;
import com.example.LibraryManager.mappers.AcquisitionMapper;
import com.example.LibraryManager.services.AcquisitionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acquisition")
@RequiredArgsConstructor
public class AcquisitionController {
    private final AcquisitionService acquisitionService;
    private final AcquisitionMapper acquisitionMapper;

    @GetMapping("/bill/{id}")
    public List<AcquisitionResponse> findByBillId(@PathVariable String id) {
        return acquisitionService.findByBillId(id).stream().map(acquisitionMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AcquisitionResponse findById(@PathVariable String id) {
        return acquisitionMapper.toResponse(acquisitionService.findById(id));
    }

    @PostMapping()
    public AcquisitionResponse create(@RequestBody AcquisitionCreateRequest req) {
        return acquisitionMapper.toResponse(acquisitionService.create(req));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        acquisitionService.deleteById(id);
    }
}
