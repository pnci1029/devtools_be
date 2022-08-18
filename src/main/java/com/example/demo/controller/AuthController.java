package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000, https://devtools-si83a57cz-green9930.vercel.app") // http
//@CrossOrigin(origins = "http://localhost:3000, https://devtools-gewfe9kn1-green9930.vercel.app") // https

//@CrossOrigin(origins = "https://devtools-gewfe9kn1-green9930.vercel.app,https://devtools-si83a57cz-green9930.vercel.app, http://localhost:3000", allowedHeaders = "*", allowCredentials = "true", exposedHeaders = "*")
//@CrossOrigin(origins = "https://devtools-gewfe9kn1-green9930.vercel.app,https://devtools-si83a57cz-green9930.vercel.app, http://localhost:3000", allowedHeaders = "*")

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginDto loginDto) {
        return authService.userLogin(loginDto);
    }
}