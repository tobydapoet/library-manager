package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
