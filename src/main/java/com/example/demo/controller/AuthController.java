package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.UserRepository;
import com.sun.net.httpserver.Headers;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
    }

    //username, password를 파라미터로 받아서 UsernamePasswordAuthenticationToken 객체를 생성합니다.
    //해당 객체를 통해 authenticate 메소드 로직을 수행합니다. 이때 위에서 만들었던 loadUserByUsername 메소드가 수행되며 유저 정보를 조회해서 인증 정보를 생성하게 됩니다.
    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginDto loginDto) {

//      로그인시 유저 아이디 존재유무 확인
        User LoginUserName = userRepository.findByUsername(loginDto.getUsername()).orElse(null);

        if (LoginUserName == null) {
            throw new IllegalArgumentException("사용자가 없습니다. 회원가입해주세요");
        }

//        if (!loginDto.getPassword().equals(LoginUserName.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 다릅니다.");
//        }


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
    }
}