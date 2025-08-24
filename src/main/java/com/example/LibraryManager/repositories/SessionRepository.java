package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {
    Session findByToken(String token);
}
