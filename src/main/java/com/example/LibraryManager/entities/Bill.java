package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bill")
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings = new ArrayList<>();

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Acquisition> acquisitions = new ArrayList<>();

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Penalty> penalties = new ArrayList<>();

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false)
    private boolean isPaid = false;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
















}
