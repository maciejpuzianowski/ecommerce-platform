package pl.mp.ecommerce_platform.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.mp.ecommerce_platform.user_service.model.LoginResponse;
import pl.mp.ecommerce_platform.user_service.model.User;
import pl.mp.ecommerce_platform.user_service.service.AuthenticationService;
import pl.mp.ecommerce_platform.user_service.service.JwtService;
import pl.mp.ecommerce_platfrom.common_models.model.LoginUserDto;
import pl.mp.ecommerce_platfrom.common_models.model.RegisterUserDto;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        logger.info("User registered: {}", registeredUser.getEmail());
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        logger.info("User authenticated: {}", authenticatedUser.getEmail());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestBody String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        boolean isValid = jwtService.isTokenValid(token, currentUser);
        if(isValid) return ResponseEntity.ok(true);
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/hej")
    public String hej() {
        return "hej";
    }
}
