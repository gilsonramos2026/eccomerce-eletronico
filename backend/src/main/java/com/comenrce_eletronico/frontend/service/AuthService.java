package com.comenrce_eletronico.frontend.service;

import com.comenrce_eletronico.frontend.dto.LoginRequest;
import com.comenrce_eletronico.frontend.dto.LoginResponse;
import com.comenrce_eletronico.frontend.dto.RegisterRequest;
import com.comenrce_eletronico.frontend.entity.User;

public interface AuthService {

    User register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
