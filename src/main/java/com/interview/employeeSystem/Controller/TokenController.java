package com.interview.employeeSystem.Controller;

import com.interview.employeeSystem.services.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/getAuthToken")
    public String generateAuthToken(@RequestParam("employee_Id") int userId) throws NoSuchAlgorithmException {

        return tokenService.createToken(userId);
    }
}
