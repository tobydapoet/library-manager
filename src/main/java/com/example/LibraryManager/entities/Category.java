package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
@JsonIgnoreProperties({"bookCategories"})
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BookCategory> bookCategories;








}
