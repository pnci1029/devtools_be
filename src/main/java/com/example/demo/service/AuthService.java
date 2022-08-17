package com.example.demo.service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ErrorType;
import com.example.demo.exception.EveryExceptions.AuthenticationException;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;

@Service
@Slf4j
@CrossOrigin("http://localhost:3000")
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(
            TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
            UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<TokenDto> userLogin(LoginDto loginDto) {

        User LoginUserName = userRepository.findByUsername(loginDto.getUsername()).orElse(null);

        if (LoginUserName == null) {
            throw new AuthenticationException(ErrorType.UsernameNotFoundException);
        }

        try {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);


            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info(SecurityContextHolder.getContext().getAuthentication().getName());
            return new ResponseEntity<>( new TokenDto(jwt,username), httpHeaders, HttpStatus.OK);

        } catch (Exception e) {
            throw new AuthenticationException(ErrorType.PasswordNotFoundException);
        }
    }
}
