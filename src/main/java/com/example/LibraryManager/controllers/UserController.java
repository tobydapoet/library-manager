package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.User;
import com.example.LibraryManager.requests.user.UserCreateRequest;
import com.example.LibraryManager.requests.user.UserStaffCreateRequest;
import com.example.LibraryManager.services.UploadService;
import com.example.LibraryManager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UploadService uploadService;

    @GetMapping()
    public Iterable<User> findAll() {
        return userService.findALl();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping("/register")
    public User register(@RequestBody UserCreateRequest userCreateDto) {
        return userService.register(userCreateDto);
    }

    //    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @PostMapping(value = "/staff", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User createStaff(@ModelAttribute UserStaffCreateRequest req) {
        return userService.createStaffUser(req);
    }

}
