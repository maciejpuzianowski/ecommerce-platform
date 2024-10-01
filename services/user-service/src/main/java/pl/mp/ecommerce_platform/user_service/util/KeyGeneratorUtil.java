package pl.mp.ecommerce_platform.user_service.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGeneratorUtil {

    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}

