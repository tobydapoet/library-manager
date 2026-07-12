package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.exception.ResourceInUseException;
import com.example.LibraryManager.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.repositories.BillRepository;
import com.example.LibraryManager.dtos.requests.BillRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;

    private final ClientService clientService;

    public Bill findById(String id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find this bill"));
    }

    public Page<Bill> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billRepository.findAll(pageable);
    }

    public Page<Bill> searchByClientName(String clientName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billRepository.searchByClientName(clientName, pageable);
    }

    public Page<Bill> findByClient(String client_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billRepository.findByClient_Id(client_id, pageable);
    }

    @Transactional
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
        bill.setTotal(0);
        return billRepository.save(bill);
    }

    @Transactional
    public Bill markAsPaid(String id) {
        Bill bill = calculateBillTotal(id);
        bill.setPaid(true);
        return billRepository.save(bill);
    }

    @Transactional
    public Bill markAsUnpaid(String id) {
        Bill bill = findById(id);
        bill.setPaid(false);
        return billRepository.save(bill);
    }

    public Bill ensureMutable(String id) {
        Bill bill = findById(id);
        if (bill.isPaid()) {
            throw new ResourceInUseException("Paid bill cannot be modified");
        }
        return bill;
    }

    public Bill ensureMutableForClient(String id, String clientId) {
        Bill bill = ensureMutable(id);
        if (bill.getClient() == null || !bill.getClient().getId().equals(clientId)) {
            throw new BadRequestException("Bill does not belong to the selected client");
        }
        return bill;
    }

    public void deleteById(String id) {
        Bill bill = ensureMutable(id);
        if (!bill.getBorrowings().isEmpty() || !bill.getAcquisitions().isEmpty() || !bill.getPenalties().isEmpty()) {
            throw new ResourceInUseException("Bill still contains transactions");
        }
        billRepository.delete(bill);
    }
}
