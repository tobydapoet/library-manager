package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Acquisition;
import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.repositories.AcquisitionRepository;
import com.example.LibraryManager.dtos.requests.AcquisitionCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new ResourceNotFoundException("Acquisition Not Found!"));
    }

    @Transactional
    public Acquisition create(AcquisitionCreateRequest req) {
        Acquisition acquisition = new Acquisition();
        Integer quantity = 1;
        Book book = bookService.findById(req.getBook_id());
        acquisition.setBook(book);
        acquisition.setClient(clientService.getClient(req.getClient_id()));
        acquisition.setBill(billService.ensureMutableForClient(req.getBill_id(), req.getClient_id()));
        if (req.getQuantity() != null) {
            acquisition.setQuantity(req.getQuantity());
            quantity = req.getQuantity();
        }
        acquisition.setQuantity(quantity);
        acquisition.setPrice(quantity * book.getSell_price());
        bookService.decreaseStock(book.getId(), quantity);
        Acquisition savedAcquisition = acquisitionRepository.save(acquisition);
        billService.calculateBillTotal(req.getBill_id());
        return savedAcquisition;
    }

    @Transactional
    public void deleteById(String id) {
        Acquisition acquisition = findById(id);
        String billId = acquisition.getBill().getId();
        billService.ensureMutable(billId);
        acquisitionRepository.delete(acquisition);
        bookService.increaseStock(acquisition.getBook().getId(), acquisition.getQuantity());
        billService.calculateBillTotal(billId);
    }
}
