package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.Purchase;
import com.example.LibraryManager.repositories.PurchaseRepository;
import com.example.LibraryManager.dtos.requests.PurchaseCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    private final BookService bookService;

    private final SupplierService supplierService;

    private final BillImportService billImportService;

    public List<Purchase> findByBillId(String billId) {
        return purchaseRepository.findByBillImport_Id(billId);
    }

    public Purchase findById(String id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));
    }

    @Transactional
    public Purchase create(PurchaseCreateRequest req) {
        Purchase purchase = new Purchase();
        Integer quantity = 1;
        purchase.setSupplier(supplierService.findById(req.getSupplier_id()));

        if (req.getQuantity() != null) {
            quantity = req.getQuantity();
        }
        purchase.setQuantity(quantity);
        Book book = bookService.findById(req.getBook_id());

        purchase.setBook(book);
        purchase.setPrice(quantity * book.getImport_price());
        purchase.setBillImport(billImportService.ensureMutableForSupplier(
                req.getBillImport_id(), req.getSupplier_id()));
        bookService.increaseStock(book.getId(), quantity);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        billImportService.calculateBillImportTotal(req.getBillImport_id());
        return savedPurchase;
    }


    @Transactional
    public void deleteById(String id) {
        Purchase purchase = findById(id);
        String billImportId = purchase.getBillImport().getId();
        billImportService.ensureMutable(billImportId);
        bookService.decreaseStock(purchase.getBook().getId(), purchase.getQuantity());
        purchaseRepository.delete(purchase);
        billImportService.calculateBillImportTotal(billImportId);
    }
}
