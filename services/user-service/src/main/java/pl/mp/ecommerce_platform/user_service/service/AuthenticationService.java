package pl.mp.ecommerce_platform.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mp.ecommerce_platform.user_service.model.User;
import pl.mp.ecommerce_platform.user_service.repository.UserRepository;
import pl.mp.ecommerce_platfrom.common_models.model.RegisterUserDto;
import pl.mp.ecommerce_platfrom.common_models.model.LoginUserDto;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        User user = new User(null,input.getFullName(),input.getEmail(),passwordEncoder.encode(input.getPassword()), null, null);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
