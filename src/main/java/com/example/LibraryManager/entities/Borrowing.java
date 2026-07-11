package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "borrowing")
@Getter
@Setter
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity = 1;

    @Column(nullable = false)
    private Date expiryDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(mappedBy = "borrowing")
    private Penalty penalty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;



















}
