package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "client")
@JsonIgnoreProperties({"borrowings", "reviews", "acquisitions", "bill"})

@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = true)
    private Date dob;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(nullable = true, unique = true)
    private String phone;

    private String avatar_url;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "client")
    private List<Borrowing> borrowings;

    @OneToMany(mappedBy = "client")
    private List<Review> reviews;

    @OneToMany(mappedBy = "client")
    private List<Acquisition> acquisitions;

    @OneToMany(mappedBy = "client")
    private List<Bill> bills;






















}
