package com.calblay.catering_api.pruebas;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    
    private final UserService userService; 

    @PostMapping("/users")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping("/users/{id}")
    public UserEntity getMethodName(@PathVariable Long id) {
        return userService.readUser(id);
    }
    
}
