package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bill_import")
@Getter
@Setter
public class BillImport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false)
    private Integer total;

    @OneToMany(mappedBy = "billImport", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    @Column(nullable = false)
    private boolean isPaid = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;












}
