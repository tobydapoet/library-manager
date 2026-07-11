package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.BillImport;
import com.example.LibraryManager.entities.Purchase;
import com.example.LibraryManager.entities.Supplier;
import com.example.LibraryManager.repositories.BillImportRepository;
import com.example.LibraryManager.requests.bill_import.BillImportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillImportService {
    private final BillImportRepository billImportRepository;

    private final SupplierService supplierService;

    public BillImport findById(String id) {
        return billImportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this bill"));
    }

    public Page<BillImport> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return billImportRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public BillImport calculateBillImportTotal(String billId) {
        BillImport bill = findById(billId);
        int total = 0;
        if (bill.getPurchases() != null) {
            total += bill.getPurchases().stream()
                    .mapToInt(Purchase::getQuantity)
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
        return billImportRepository.save(bill_import);
    }

    public void deleteById(String id) {
        billImportRepository.deleteById(id);
    }
}
