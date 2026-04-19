package com.example.authservice.controller;


import com.example.authservice.dto.AuthResponseDTO;
import com.example.authservice.dto.AuthRequestDTO;
import com.example.authservice.dto.ValidateResponseDTO;
import com.example.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        return new ResponseEntity<>(authService.register(authRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        return new ResponseEntity<>(authService.authenticate(authRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateResponseDTO> validateToken(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(authService.validate(token), HttpStatus.OK);
    }
}
