package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Bill;
import com.example.LibraryManager.requests.bill.BillRequest;
import com.example.LibraryManager.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping()
    public Page<Bill> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
        return billService.findAll(page, size);
    }

    @GetMapping("/client/{id}")
    public Page<Bill> findAllByClientId(@PathVariable String id,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return billService.findByClient(id, page, size);
    }

    @GetMapping("/{id}")
    public Bill findOne(@PathVariable String id) {
        return billService.findById(id);
    }

    @GetMapping("/search")
    public Page<Bill> searchByClientName(
            @RequestParam String clientName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return billService.searchByClientName(clientName, page, size);
    }

    @PostMapping()
    public Bill create(@RequestBody BillRequest req) {
        return billService.create(req);
    }

    @PutMapping("/{id}")
    public Bill calculate(@PathVariable String id) {
        return billService.calculateBillTotal(id);
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        billService.deleteById(id);
    }
}
