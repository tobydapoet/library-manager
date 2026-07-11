package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.example.LibraryManager.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`user`")
@JsonIgnoreProperties({"sessions", "password", "client", "staff", "hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role = Roles.client;

    @Column(unique = true)
    private String provided_id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Session> sessions;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Staff staff;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Client client;


















}

