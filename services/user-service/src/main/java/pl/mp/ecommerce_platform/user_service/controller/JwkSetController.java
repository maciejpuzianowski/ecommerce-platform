package pl.mp.ecommerce_platform.user_service.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mp.ecommerce_platform.user_service.service.JwtService;
import pl.mp.ecommerce_platform.user_service.util.KeyGeneratorUtil;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
public class JwkSetController {
    private final RSAKey rsaKey;
    private final JwtService jwtService;

    @Value("${jwt.key-id}")
    private String keyId;

    public JwkSetController(JwtService jwtService) throws Exception {
        this.jwtService = jwtService;
        RSAPublicKey publicKey = jwtService.getPublicKey();
        this.rsaKey = new RSAKey.Builder(publicKey)
                .keyID(keyId)
                .build();
    }

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> getJwkSet() {
        // Return the JWK Set containing the public key
        return new JWKSet(rsaKey).toJSONObject();
    }
}
