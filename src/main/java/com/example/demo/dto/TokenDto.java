package com.example.demo.dto;

import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String mytoken;

    private String username;

}