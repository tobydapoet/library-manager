package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.Purchase;
import com.example.LibraryManager.repositories.PurchaseRepository;
import com.example.LibraryManager.dtos.requests.BookUpdateRequest;
import com.example.LibraryManager.dtos.requests.PurchaseCreateRequest;
import org.springframework.stereotype.Service;

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

    public Purchase create(PurchaseCreateRequest req) {
        Purchase purchase = new Purchase();
        Integer quantity = 1;
        purchase.setSupplier(supplierService.findById(req.getSupplier_id()));

        if (req.getQuantity() != null) {
            purchase.setQuantity(req.getQuantity());
            quantity = req.getQuantity();
        }
        Book book = bookService.findById(req.getBook_id());

        purchase.setBook(book);
        purchase.setPrice(quantity * book.getImport_price());
        purchase.setBillImport(billImportService.findById(req.getBillImport_id()));
        Purchase savedPurchase = purchaseRepository.save(purchase);
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setQuantity(book.getQuantity() + quantity);
        bookService.update(req.getBook_id(), bookUpdateRequest);
        return savedPurchase;
    }


    public void deleteById(String id) {
        purchaseRepository.deleteById(id);
    }
}
