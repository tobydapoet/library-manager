package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.entities.User;
import com.example.LibraryManager.enums.Roles;
import com.example.LibraryManager.repositories.ClientRepository;
import com.example.LibraryManager.repositories.UserRepository;
import com.example.LibraryManager.dtos.requests.UserCreateRequest;
import com.example.LibraryManager.dtos.requests.UserGoogleCreateRequest;
import com.example.LibraryManager.dtos.requests.UserStaffCreateRequest;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffService staffService;

    public UserService(UserRepository userRepository,
                       ClientRepository clientRepository,
                       PasswordEncoder passwordEncoder,
                       StaffService staffService
    ) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.staffService = staffService;
    }

    public User login(String email, String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UsernameNotFoundException("Email not found!");
        }

        boolean isMatchPassword = passwordEncoder.matches(password, existingUser.getPassword());
        if (!isMatchPassword) {
            throw new UsernameNotFoundException("Wrong password!");
        }
        return existingUser;
    }

    @Cacheable(value = "uploadCache", key = "'user:all'")
    public Iterable<User> findALl() {
        return userRepository.findAll();
    }

    @Cacheable(value = "uploadCache", key = "'user:' + #id")
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(()
                -> new RuntimeException("User not found with id: " + id));
    }

    @CacheEvict(value = "uploadCache", key = "'user:all'")
    public User register(UserCreateRequest dto) {
        if (clientRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("This phone is already used!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("This email is already used!");
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        Client client = new Client();
        client.setUser(savedUser);
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        clientRepository.save(client);
        return userRepository.findById(savedUser.getId()).get();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createStaffUser(UserStaffCreateRequest req) {
        User user = new User();
        User existingUser = userRepository.findByEmail(req.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("This email is already used!");
        }
        user.setEmail(req.getEmail());
        user.setRole(Roles.staff);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        User savedUser = userRepository.save(user);
        staffService.create(req, savedUser);
        return savedUser;
    }

    @Transactional
    public User createWithGoogle(UserGoogleCreateRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("This email is already used!");
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setProvided_id(req.getProvided_id());
        User savedUser = userRepository.save(user);

        Client client = new Client();
        client.setUser(savedUser);
        client.setAvatar_url(req.getAvatar_url());
        client.setName(req.getName());
        clientRepository.save(client);

        return userRepository.findById(savedUser.getId()).get();
    }

}
