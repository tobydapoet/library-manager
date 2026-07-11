package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Borrowing;
import com.example.LibraryManager.entities.Penalty;
import com.example.LibraryManager.repositories.PenaltyRepository;
import com.example.LibraryManager.dtos.requests.PenaltyCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;

    private final ClientService clientService;

    private final BorrowingService borrowingService;

    private final BillService billService;

    public List<Penalty> findByBillId(String billId) {
        return penaltyRepository.findByBill_Id(billId);
    }

    public List<Penalty> findByBorrowingId(String borrowingId) {
        return penaltyRepository.findByBorrowing_Id(borrowingId);
    }

    public Penalty findById(String id) {
        return penaltyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Penalty Not Found"));
    }

    public Penalty create(PenaltyCreateRequest req) {
        Penalty penalty = new Penalty();
        Integer quantity = 1;
        if (req.getQuantity() != null) {
            penalty.setQuality(req.getQuantity());
            quantity = req.getQuantity();
        }
        Borrowing borrowing = borrowingService.findById(req.getBorrowing_id());
        penalty.setPrice(quantity * borrowing.getBook().getSell_price());

        penalty.setBorrowing(borrowing);
        penalty.setBill(billService.findById(req.getBill_id()));
        return penaltyRepository.save(penalty);
    }

    public void deleteById(String id) {
        penaltyRepository.deleteById(id);
    }
}
