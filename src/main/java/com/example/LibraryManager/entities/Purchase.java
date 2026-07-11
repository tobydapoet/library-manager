package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "purchase")
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billImport_id", nullable = false)
    private BillImport billImport;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;














}
