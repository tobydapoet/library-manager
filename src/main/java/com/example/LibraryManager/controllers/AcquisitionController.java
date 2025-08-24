package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Acquisition;
import com.example.LibraryManager.requests.acquisition.AcquisitionCreateRequest;
import com.example.LibraryManager.services.AcquisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acquisition")
public class AcquisitionController {
    @Autowired
    private AcquisitionService acquisitionService;

    @GetMapping("/bill/{id}")
    public List<Acquisition> findByBillId(@PathVariable String id) {
        return acquisitionService.findByBillId(id);
    }

    @GetMapping("/{id}")
    public Acquisition findById(@PathVariable String id) {
        return acquisitionService.findById(id);
    }

    @PostMapping()
    public Acquisition create(@RequestBody AcquisitionCreateRequest req) {
        return acquisitionService.create(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        acquisitionService.deleteById(id);
    }
}
