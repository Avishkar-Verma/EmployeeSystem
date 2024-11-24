package com.interview.employeeSystem.services;

import com.interview.employeeSystem.exception.EmployeeSystemException;
import com.interview.employeeSystem.repository.TokenDetailsRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private final String secretKey = "4261656C64756E67K4h56l6h3lkj32h2jk5h6kk7h33k6hs6t3j5g2jG2J4GJ45G2J1G4J4G";
    private final TokenDetailsRepository tokenDetailsRepository;

    public TokenService(TokenDetailsRepository tokenDetailsRepository) {
        this.tokenDetailsRepository = tokenDetailsRepository;
    }

    public String createToken(int employeeId) {

        String token = generateToken(Integer.toString(employeeId), secretKey, 3600000); // 1 hour expiration
        System.out.println("Generated Token: " + token);

        return token;

//        Claims claims = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//
//        TokenDetails tokenDetails = new TokenDetails();
//        tokenDetails.setToken_Id(((int)tokenDetailsRepository.count())+1);
//        tokenDetails.setEmployee_Id(employee_id);
//        tokenDetails.setAuth_Token(token);
//        tokenDetails.setCreate_Time(new Date());
//        tokenDetails.setExpire_Time(claims.getExpiration());
//
//        tokenDetailsRepository.save(tokenDetails);
    }

    private String generateToken(String subject, String secretKey, long expirationTimeInMillis) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeInMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public int validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Expiration: " + claims.getExpiration());
            return Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new EmployeeSystemException("You are not authorized due to your token is either expired or invalid.", HttpStatus.UNAUTHORIZED);
        }
    }

}
