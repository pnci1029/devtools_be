package com.example.demo.service;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AuthorityEntity;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//UserRepository, PasswordEncoder를 주입받는 UserService클래스를 생성
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    영문 + 숫자 정규식
    private static final String idPattern = "^[0-9a-zA-Z]*$";
//    영문 + 숫자 + 특수문자 정규식
    private static final String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]$";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //signup 메소드는 이미 같은 username으로 가입된 유저가 있는지 확인하고
    //UserDto객체의 정보들을 기반으로 권한 객체와 유저 객체를 생성하여 DB저장
    @Transactional
    public User signup(UserDto userDto) {
//        중복 여부 예외처리
        if (userRepository.findOneWithAuthorityEntityByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
//        아이디 길이 예외처리
        if (userDto.getUsername().length() < 4 || userDto.getUsername().length() > 12) {
            throw new IllegalArgumentException("아이디는 4~12글자로 입력해주세요");
        }
        if (!Pattern.matches(idPattern, userDto.getUsername()))
            throw new IllegalArgumentException("아이디는 영문 숫자 조합으로 작성해주세요") {
        };
//        비밀번호 길이 예외처리
        if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 16) {
            throw new IllegalArgumentException("비밀번호는 8~16글자로 입력해주세요");
        }
        if (!Pattern.matches(pwPattern, userDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호는 영문 숫자 특수문자 조합으로 작성해주세요");
        }


        //해당 메소드롤 생성된 유저는 ROLE_USER권한을 소유해서
        //ROLE_ADMIN 권한만 호출할 수 있는 API는 호출불가능함
        AuthorityEntity authorityEntity = AuthorityEntity.ROLE_USER;

//        유저 입력값 빌더
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorityEntity(AuthorityEntity.ROLE_USER)
                .activated(true)
                .build();

        userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    //getUserWithAuthorities : username을 파라미터롤 받아 해당유저 정보 및 권한정보 리턴
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthorityEntityByUsername(username);
    }

    @Transactional(readOnly = true)
    //getMyUserWithAuthorities : SecurityUtil의 getCurrentUsername()
    //메소드가 리턴하는 username의 유저 및 권한 정보를 리턴함
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthorityEntityByUsername);
    }

    public Boolean checkUsername(UserDto userDto) {
//  중복 닉네임 검증
        List<User> target = userRepository.findAll();
        System.out.println(userDto.getUsername());
        for (User targets : target) {
            if (userDto.getUsername().equals(targets.getUsername())) {
                return false;
            }
        }
        return true;
    }

}