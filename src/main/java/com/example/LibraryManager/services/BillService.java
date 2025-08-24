package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.repositories.BillRepository;
import com.example.LibraryManager.requests.bill.BillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ClientService clientService;

    public Bill findById(String id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this bill"));
    }

    public Page<Bill> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billRepository.findAll(pageable);
    }

    public Page<Bill> searchByClientName(String clientName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billRepository.findByClient_NameContainingIgnoreCase(clientName, pageable);
    }

    public Page<Bill> findByClient(String client_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billRepository.findByClient_Id(client_id, pageable);
    }

    public Bill calculateBillTotal(String billId) {
        Bill bill = findById(billId);
        int total = 0;
        if (bill.getBorrowings() != null) {
            total += bill.getBorrowings().stream()
                    .mapToInt(Borrowing::getPrice)
                    .sum();
        }
        if (bill.getAcquisitions() != null) {
            total += bill.getAcquisitions().stream()
                    .mapToInt(Acquisition::getPrice)
                    .sum();
        }
        if (bill.getPenalties() != null) {
            total += bill.getPenalties().stream()
                    .mapToInt(Penalty::getPrice)
                    .sum();
        }
        bill.setTotal(total);
        return billRepository.save(bill);
    }

    public Bill create(BillRequest req) {
        Bill bill = new Bill();
        Client client = clientService.getClient(req.getClientId());
        bill.setClient(client);
        return billRepository.save(bill);
    }

    public void deleteById(String id) {
        billRepository.deleteById(id);
    }
}
