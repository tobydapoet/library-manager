package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "receive_id")
    private User receiver;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column(nullable = false)
    private boolean isRead = false;














}
