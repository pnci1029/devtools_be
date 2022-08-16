package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", length = 12, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @Column
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private AuthorityEntity authorityEntity;




//    public User(String username, String password, String nickname, boolean activated, AuthorityEntity authorityEntity) {
//        this.username = username;
//        this.password = password;
//        this.nickname = nickname;
//        this.activated = activated;
//        this.authorityEntity = authorityEntity;
//    }
}