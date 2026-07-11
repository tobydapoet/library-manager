package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "penalty")
@Getter
@Setter
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne()
    @JoinColumn(name = "borrow_id")
    private Borrowing borrowing;

    @Column(nullable = false)
    private int quality;

    @Column(nullable = false)
    private int price;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;












}
