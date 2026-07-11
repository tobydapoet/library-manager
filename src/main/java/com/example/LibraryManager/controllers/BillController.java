package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.BillRequest;
import com.example.LibraryManager.dtos.responses.BillResponse;
import com.example.LibraryManager.mappers.BillMapper;
import com.example.LibraryManager.services.BillService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    private final BillMapper billMapper;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping()
    public Page<BillResponse> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
        return billService.findAll(page, size).map(billMapper::toResponse);
    }

    @GetMapping("/client/{id}")
    public Page<BillResponse> findAllByClientId(@PathVariable String id,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return billService.findByClient(id, page, size).map(billMapper::toResponse);
    }

    @GetMapping("/{id}")
    public BillResponse findOne(@PathVariable String id) {
        return billMapper.toResponse(billService.findById(id));
    }

    @GetMapping("/search")
    public Page<BillResponse> searchByClientName(
            @RequestParam String clientName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return billService.searchByClientName(clientName, page, size).map(billMapper::toResponse);
    }

    @PostMapping()
    public BillResponse create(@RequestBody BillRequest req) {
        return billMapper.toResponse(billService.create(req));
    }

    @PutMapping("/{id}")
    public BillResponse calculate(@PathVariable String id) {
        return billMapper.toResponse(billService.calculateBillTotal(id));
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        billService.deleteById(id);
    }
}
