package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.PenaltyCreateRequest;
import com.example.LibraryManager.dtos.responses.PenaltyResponse;
import com.example.LibraryManager.mappers.PenaltyMapper;
import com.example.LibraryManager.services.PenaltyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/penalty")
@RequiredArgsConstructor
public class PenaltyController {
    private final PenaltyService penaltyService;
    private final PenaltyMapper penaltyMapper;

    @GetMapping("/bill/{id}")
    public List<PenaltyResponse> findByBillId(@PathVariable String id) {
        return penaltyService.findByBillId(id).stream().map(penaltyMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PenaltyResponse findById(@PathVariable String id) {
        return penaltyMapper.toResponse(penaltyService.findById(id));
    }

    @PostMapping()
    public PenaltyResponse create(@RequestBody PenaltyCreateRequest req) {
        return penaltyMapper.toResponse(penaltyService.create(req));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        penaltyService.deleteById(id);
    }
}
