package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Location;
import com.example.LibraryManager.repositories.LocationRepository;
import com.example.LibraryManager.requests.location.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Cacheable(value = "uploadCache", key = "'location:all'")
    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }

    @Cacheable(value = "uploadCache", key = "'location:' + #id")
    public Location findById(Integer id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this location"));
    }

    @CacheEvict(value = "uploadCache", key = "'location:all'")
    public Location create(LocationRequest req) {
        Location location = new Location();
        location.setFloor(req.getFloor());
        return locationRepository.save(location);
    }

    @Caching(evict = {
            @CacheEvict(value = "uploadCache", key = "'location:all'"),
            @CacheEvict(value = "uploadCache", key = "'location:' + #id")

    })
    public Location update(Integer id, Integer floor) {
        Location location = findById(id);
        location.setFloor(floor);
        return locationRepository.save(location);
    }

    @Caching(evict = {
            @CacheEvict(value = "uploadCache", key = "'location:all'"),
            @CacheEvict(value = "uploadCache", key = "'location:' + #id")

    })
    public void delete(Integer id) {
        locationRepository.deleteById(id);
    }
}
