package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Acquisition;
import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.repositories.AcquisitionRepository;
import com.example.LibraryManager.dtos.requests.AcquisitionCreateRequest;
import com.example.LibraryManager.dtos.requests.BookUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcquisitionService {
    private final AcquisitionRepository acquisitionRepository;

    private final BookService bookService;

    private final ClientService clientService;

    private final BillService billService;


    public List<Acquisition> findByBillId(String billId) {
        return acquisitionRepository.findByBill_Id(billId);
    }

    public Acquisition findById(String id) {
        return acquisitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acquisition Not Found!"));
    }

    public Acquisition create(AcquisitionCreateRequest req) {
        Acquisition acquisition = new Acquisition();
        Integer quantity = 1;
        Book book = bookService.findById(req.getBook_id());
        acquisition.setBook(book);
        acquisition.setClient(clientService.getClient(req.getClient_id()));
        acquisition.setBill(billService.findById(req.getBill_id()));
        if (req.getQuantity() != null) {
            acquisition.setQuantity(req.getQuantity());
            quantity = req.getQuantity();
        }
        acquisition.setQuantity(quantity);
        acquisition.setPrice(quantity * book.getSell_price());
        Acquisition savedAcquisition = acquisitionRepository.save(acquisition);
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setQuantity(book.getQuantity() - savedAcquisition.getQuantity());
        bookService.update(req.getBook_id(), bookUpdateRequest);
        return savedAcquisition;
    }

    public void deleteById(String id) {
        acquisitionRepository.deleteById(id);
    }
}
