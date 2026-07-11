package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "acquisition")

@Getter
@Setter
public class Acquisition {
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
    private Integer quantity = 1;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;














}
