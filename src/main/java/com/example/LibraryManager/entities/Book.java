package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.example.LibraryManager.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "book")
@JsonIgnoreProperties({"book_categories", "borrowings", "reviews", "purchases", "acquisitions"})
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String image_url;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private BookStatus status = BookStatus.available;

    @Column(nullable = true)
    private int borrow_price;

    @Column(nullable = true)
    private int sell_price;

    @Column(nullable = false)
    private int import_price;

    @Column(nullable = false)
    private Integer quantity = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "book")
    private List<BookCategory> book_categories;

    @OneToMany(mappedBy = "book")
    private List<Borrowing> borrowings;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

    @OneToMany(mappedBy = "book")
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "book")
    private List<Acquisition> acquisitions;


































}
