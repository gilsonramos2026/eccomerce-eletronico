package com.comenrce_eletronico.frontend.service.impl;

import com.comenrce_eletronico.frontend.dto.LoginRequest;
import com.comenrce_eletronico.frontend.dto.LoginResponse;
import com.comenrce_eletronico.frontend.dto.RegisterRequest;
import com.comenrce_eletronico.frontend.entity.User;
import com.comenrce_eletronico.frontend.repository.UserRepository;
import com.comenrce_eletronico.frontend.security.JwtUtil;
import com.comenrce_eletronico.frontend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    // =========================
    // REGISTER
    // =========================

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new RuntimeException("Email ja registrato");
        }

        User user = new User();
        user.setName(registerRequest.name());

        user.setEmail(registerRequest.email());

        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        user.setRole("USER");

        return userRepository.save(user);
    }

    // =========================
    // LOGIN
    // =========================

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        //Encontrar usuario por email
        User user = userRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Email ou senha invalida"));

        //checar senha
        if (!passwordEncoder.matches(
                loginRequest.password(),
                user.getPassword()
        )) {

            throw new RuntimeException("Email ou senha invalido");
        }

        //Gerar token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        //Retornar resposta
        return new LoginResponse(
                token,
                "Logado com sucesso!",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
