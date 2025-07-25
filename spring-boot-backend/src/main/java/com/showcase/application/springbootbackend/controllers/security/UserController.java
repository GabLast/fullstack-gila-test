package com.showcase.application.springbootbackend.controllers.security;

import com.showcase.application.springbootbackend.services.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/findall")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(
                userService.findAll(), HttpStatus.OK);
    }
}
