package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.PenaltyCreateRequest;
import com.example.LibraryManager.entities.Borrowing;
import com.example.LibraryManager.entities.Penalty;
import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.exception.BadRequestException;
import com.example.LibraryManager.repositories.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;

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
                .orElseThrow(() -> new ResourceNotFoundException("Penalty Not Found"));
    }

    @Transactional
    public Penalty create(PenaltyCreateRequest req) {
        Penalty penalty = new Penalty();
        Integer quantity = 1;
        if (req.getQuantity() != null) {
            penalty.setQuality(req.getQuantity());
            quantity = req.getQuantity();
        }
        penalty.setQuality(quantity);
        Borrowing borrowing = borrowingService.findById(req.getBorrowing_id());
        if (borrowing.getBill() == null || !borrowing.getBill().getId().equals(req.getBill_id())) {
            throw new BadRequestException("Borrowing does not belong to the selected bill");
        }
        penalty.setPrice(quantity * borrowing.getBook().getSell_price());

        penalty.setBorrowing(borrowing);
        penalty.setBill(billService.ensureMutable(req.getBill_id()));
        Penalty savedPenalty = penaltyRepository.save(penalty);
        billService.calculateBillTotal(req.getBill_id());
        return savedPenalty;
    }

    @Transactional
    public void deleteById(String id) {
        Penalty penalty = findById(id);
        String billId = penalty.getBill().getId();
        billService.ensureMutable(billId);
        penaltyRepository.delete(penalty);
        billService.calculateBillTotal(billId);
    }
}
