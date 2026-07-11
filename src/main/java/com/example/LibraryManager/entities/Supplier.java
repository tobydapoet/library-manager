package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIgnoreProperties({"purchases"})
@Table(name = "supplier")
@Getter
@Setter
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String contact;

    @Column(nullable = false)
    private String address;

    private boolean isDeleted = false;

    @OneToMany(mappedBy = "supplier")
    @JsonManagedReference
    private List<Purchase> purchases;












}
