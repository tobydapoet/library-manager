package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Penalty;
import com.example.LibraryManager.requests.penalty.PenaltyCreateRequest;
import com.example.LibraryManager.services.PenaltyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/penalty")
@RequiredArgsConstructor
public class PenaltyController {
    private final PenaltyService penaltyService;

    @GetMapping("/bill/{id}")
    public List<Penalty> findByBillId(@PathVariable String id) {
        return penaltyService.findByBillId(id);
    }

    @GetMapping("/{id}")
    public Penalty findById(@PathVariable String id) {
        return penaltyService.findById(id);
    }

    @PostMapping()
    public Penalty create(@RequestBody PenaltyCreateRequest req) {
        return penaltyService.create(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        penaltyService.deleteById(id);
    }
}
