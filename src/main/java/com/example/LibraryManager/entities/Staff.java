package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.example.LibraryManager.enums.StaffPosition;
import jakarta.persistence.*;

@Entity
@Table(name = "staff")
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer salary = 5000000;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private String phone;

    private String avatar_url;

    @Column(nullable = false)
    private StaffPosition position;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
















}
