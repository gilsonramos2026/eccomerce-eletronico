package com.comenrce_eletronico.frontend.controller;

import com.comenrce_eletronico.frontend.dto.LoginRequest;
import com.comenrce_eletronico.frontend.dto.LoginResponse;
import com.comenrce_eletronico.frontend.dto.RegisterRequest;
import com.comenrce_eletronico.frontend.entity.User;
import com.comenrce_eletronico.frontend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    public final AuthService authService;

    // =========================
    // REGISTER
    // =========================
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest registerRequest
            ) {
        User user = authService.register(registerRequest);

        Map<String, Object> response  = new HashMap<>();

        response.put(
                "message",
                "Usuaruo registrado com sucesso"
        );
        response.put(
                "userId",
                user.getId()
        );

        response.put(
                "name",
                user.getName()
        );

        response.put(
                "email",
                user.getEmail()
        );

        response.put(
                "role",
                user.getRole()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(response);
    }
}
