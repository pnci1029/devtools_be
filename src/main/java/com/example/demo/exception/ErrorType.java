package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorType {

//    UsernameOrPasswordNotFoundException (400, "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    UsernameNotFoundException (400, "사용자가 없습니다. 회원가입해주세요.", HttpStatus.BAD_REQUEST),
    PasswordNotFoundException (400, "로그인 정보를 다시 확인해 주세요.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZEDException (401, "로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ReLogin(445, "모든 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    NotExistComment(400, "댓글을 달기 위한 게시물이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),

    CommentLengthIssue(400, "댓글은 1글자 이상 40자 이하로 작성해주세요", HttpStatus.BAD_REQUEST),
    ExistUser(400, "이미 가입한 유저아이디입니다.", HttpStatus.BAD_REQUEST),
    passwordTypeMix(400, "비밀번호는 영문 숫자 특수문자로 작성해주세요", HttpStatus.BAD_REQUEST),

    passwordLengthIssue(400, "비밀번호는 8~16글자로 입력해주세요", HttpStatus.BAD_REQUEST),

    idTypeMix(400,"아이디는 숫자와 영어 조합으로 작성해주세요" ,HttpStatus.BAD_REQUEST ),

    idLengthIssue(400, "아이디는 4~12글자로 작성해주세요", HttpStatus.BAD_REQUEST ),


    ArticleLengthException(400, "제목은 1~40글자로 작성해주세요", HttpStatus.BAD_REQUEST),

    ContentLengthException(400, "내용은 1~400글자로 작성해주세요", HttpStatus.BAD_REQUEST),

    NotExistPatchArticles(400, "수정할 아이디가 없습니다.", HttpStatus.BAD_REQUEST),
    PatchOtherArticles(400, "다른 사용자 게시글을 수정할 수 없습니다", HttpStatus.BAD_REQUEST),
    PatchBlank(400, "수정해주세요", HttpStatus.BAD_REQUEST),
    NotExistDeleteArticles(400, "삭제 할 아이디가 없습니다", HttpStatus.BAD_REQUEST),
    DeleteOtherArticles(400, "다른 사용자 게시글을 삭제할 수 없습니다", HttpStatus.BAD_REQUEST);

    @Getter
    private int code;

    @Getter
    private String description;

    @Getter
    private HttpStatus httpStatus;

    ErrorType(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
