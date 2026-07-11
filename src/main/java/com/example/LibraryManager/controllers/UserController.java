package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.UserCreateRequest;
import com.example.LibraryManager.dtos.requests.UserStaffCreateRequest;
import com.example.LibraryManager.dtos.responses.UserResponse;
import com.example.LibraryManager.mappers.UserMapper;
import com.example.LibraryManager.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public java.util.List<UserResponse> findAll() {
        return userMapper.toResponses(userService.findALl());
    }

    @GetMapping("/{id}")
    public UserResponse findUserById(@PathVariable String id) {
        return userMapper.toResponse(userService.findById(id));
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserCreateRequest userCreateDto) {
        return userMapper.toResponse(userService.register(userCreateDto));
    }

    //    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @PostMapping(value = "/staff", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse createStaff(@ModelAttribute UserStaffCreateRequest req) {
        return userMapper.toResponse(userService.createStaffUser(req));
    }

}
