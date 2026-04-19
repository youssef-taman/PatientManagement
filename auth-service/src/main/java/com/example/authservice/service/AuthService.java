package com.example.authservice.service;


import com.example.authservice.dto.AuthResponseDTO;
import com.example.authservice.dto.AuthRequestDTO;
import com.example.authservice.exception.ExceptionMessages;
import com.example.authservice.exception.InvalidCredentialsException;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;

    public AuthResponseDTO register(AuthRequestDTO authRequestDTO) {

        User user = new User(authRequestDTO.getEmail(), passwordEncoder.encode(authRequestDTO.getPassword()), "USER");
        userRepository.save(user);
        String jwtToken = jwtUtils.generateToken(user.getEmail(), user.getRole());
        return new AuthResponseDTO(jwtToken, "jwt", jwtUtils.getExpirationDate(jwtToken));
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword());
        authManager.authenticate(authToken);
        User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow(() -> new InvalidCredentialsException(ExceptionMessages.INVALID_CREDENTIALS));
        String jwtToken = jwtUtils.generateToken(user.getEmail(), user.getRole());
        return new AuthResponseDTO(jwtToken, "jwt", jwtUtils.getExpirationDate(jwtToken));
    }

    public Boolean validate(String token) {
        return token != null && jwtUtils.validateJwt(token) && ! jwtUtils.isExpired(token);

    }


}
