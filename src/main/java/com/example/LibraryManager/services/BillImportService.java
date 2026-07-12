package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.exception.ResourceInUseException;
import com.example.LibraryManager.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.BillImport;
import com.example.LibraryManager.entities.Purchase;
import com.example.LibraryManager.entities.Supplier;
import com.example.LibraryManager.repositories.BillImportRepository;
import com.example.LibraryManager.dtos.requests.BillImportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BillImportService {
    private final BillImportRepository billImportRepository;

    private final SupplierService supplierService;

    public BillImport findById(String id) {
        return billImportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find this bill"));
    }

    public Page<BillImport> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billImportRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public BillImport calculateBillImportTotal(String billId) {
        BillImport bill = findById(billId);
        int total = 0;
        if (bill.getPurchases() != null) {
            total += bill.getPurchases().stream()
                    .mapToInt(Purchase::getPrice)
                    .sum();
        }
        bill.setTotal(total);
        return billImportRepository.save(bill);
    }

    public Page<BillImport> searchBySupplierName(String supplierName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billImportRepository.findBySupplier_NameContainingIgnoreCase(supplierName, pageable);
    }

    public BillImport create(BillImportRequest req) {
        BillImport bill_import = new BillImport();
        Supplier supplier = supplierService.findById(req.getSupplierId());
        bill_import.setSupplier(supplier);
        bill_import.setTotal(0);
        return billImportRepository.save(bill_import);
    }

    @Transactional
    public BillImport markAsPaid(String id) {
        BillImport bill = calculateBillImportTotal(id);
        bill.setPaid(true);
        return billImportRepository.save(bill);
    }

    @Transactional
    public BillImport markAsUnpaid(String id) {
        BillImport bill = findById(id);
        bill.setPaid(false);
        return billImportRepository.save(bill);
    }

    public BillImport ensureMutable(String id) {
        BillImport bill = findById(id);
        if (bill.isPaid()) {
            throw new ResourceInUseException("Paid import bill cannot be modified");
        }
        return bill;
    }

    public BillImport ensureMutableForSupplier(String id, String supplierId) {
        BillImport bill = ensureMutable(id);
        if (bill.getSupplier() == null || !bill.getSupplier().getId().equals(supplierId)) {
            throw new BadRequestException("Import bill does not belong to the selected supplier");
        }
        return bill;
    }

    public void deleteById(String id) {
        BillImport bill = ensureMutable(id);
        if (!bill.getPurchases().isEmpty()) {
            throw new ResourceInUseException("Import bill still contains purchases");
        }
        billImportRepository.delete(bill);
    }
}
