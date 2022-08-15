package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //가입 SecurrityConfig.java에서 permitAll 설정했기때문에 권한없이 가능함
    @PostMapping("/register")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @PostMapping("/register/username")
    public Boolean checkUsername(@RequestBody UserDto userDto) {
        return userService.checkUsername(userDto);
    }

    @GetMapping("/user")
    //@PreAuthorize 해당 어노테이션을 이용하여 권한 2개모두 호출가능하게 설정함
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    //현재 Security Context에 저장되어있는 인증정보의 username을 기준으로 한
    //유저 정보 및 권한 정보를 리턴하는 API
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //ADMIN 만 호출가능
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
