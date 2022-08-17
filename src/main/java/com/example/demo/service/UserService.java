package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AuthorityEntity;
import com.example.demo.entity.User;
import com.example.demo.exception.ErrorType;
import com.example.demo.exception.EveryExceptions.IllegalArgumentException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //    영문 + 숫자 정규식
    private static final String idPattern = "^[A-za-z0-9]{4,12}$";
    //    영문 + 숫자 + 특수문자 정규식
//    private static final String pwPattern = "^.*(?=^.{8,16}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
    private static final String pwPattern = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$";
//    private static final String pwPattern = const idRegExp = /^[A-za-z0-9]{4,12}$/g;
//const pwRegExp =
//            /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;

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
            throw new IllegalArgumentException(ErrorType.ExistUser);
        }
//        아이디 길이 예외처리
        if (userDto.getUsername().length() < 4 || userDto.getUsername().length() > 12) {
            throw new IllegalArgumentException(ErrorType.idLengthIssue);
        }
        if (!Pattern.matches(idPattern, userDto.getUsername()))
            throw new IllegalArgumentException(ErrorType.idTypeMix) {
            };
//        비밀번호 길이 예외처리
        if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 16) {
            throw new IllegalArgumentException(ErrorType.passwordLengthIssue);
        }
        if (!Pattern.matches(pwPattern, userDto.getPassword()))
            throw new IllegalArgumentException(ErrorType.passwordTypeMix) {
            };


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