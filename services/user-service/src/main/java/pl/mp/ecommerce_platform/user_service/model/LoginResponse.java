package pl.mp.ecommerce_platform.user_service.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;
}
