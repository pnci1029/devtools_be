package com.example.demo.service;

import com.example.demo.dto.ArticleDetailResponseDto;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.entity.ArticleEntity;
import com.example.demo.entity.CommentEntity;
import com.example.demo.exception.ErrorType;
import com.example.demo.exception.EveryExceptions.IllegalArgumentException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }
    public String rightNow() {
        LocalDateTime now1 = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String time = now1.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        return time;
    }

        public String bringUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Transactional
    public List<ArticleResponseDto> getArticles() {
        List<ArticleEntity> articleEntity = articleRepository.findAllDesc();
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
        for (ArticleEntity articles : articleEntity) {
            articleResponseDtos.add(new ArticleResponseDto(articles));
        }
        for (ArticleResponseDto datas : articleResponseDtos) {
            if (datas.getUsername().equals(bringUserName())) {
                datas.setIsMyArticles(Boolean.TRUE);
            }
        }

        return articleResponseDtos;
    }

    @Transactional
    public ArticleEntity postArticles(ArticleDto articleDto) {
        ArticleEntity articleEntity = new ArticleEntity(articleDto, bringUserName());
        if (articleDto.getTitle().length() < 1 || articleDto.getTitle().length() > 30) {
            throw new IllegalArgumentException(ErrorType.ArticleLengthException);
        }
        if (articleDto.getContent().length() < 1 || articleDto.getContent().length() > 400) {
            throw new IllegalArgumentException(ErrorType.ContentLengthException);
        }
        articleRepository.save(articleEntity);
        return articleEntity;
    }

    @Transactional
    public ArticleEntity patchArticles(ArticleDto dto, Long id) {
//        다른 아이디 수정요청
        ArticleEntity target = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ErrorType.NotExistPatchArticles)
        );

//        다른 게시글 수정요청
        if (!target.getUserName().equals(bringUserName())) {
            throw new IllegalArgumentException(ErrorType.PatchOtherArticles);
        }

//        비어있을때
        if (dto.getContent().equals("")) {
            throw new IllegalArgumentException(ErrorType.PatchBlank);
        }


        target.patch(dto, rightNow());
        return target;
    }

    @Transactional
    public void deleteArticles(Long id) {
        ArticleEntity target = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ErrorType.NotExistDeleteArticles)
        );

        //        다른 게시글 삭제요청
        if (!target.getUserName().equals(bringUserName())) {
            throw new IllegalArgumentException(ErrorType.DeleteOtherArticles);
        }
        articleRepository.delete(target);
    }

//    게시판 상세페이지
    @Transactional
    public ArticleDetailResponseDto getDetailArticles(Long id) {
        ArticleEntity target = articleRepository.findById(id).orElse(null);

//        내림차순 정렬
        List<CommentEntity> commentEntity = commentRepository.findAllByArticleIdDesc(id);

        List<CommentEntity> commentBox = new ArrayList<>();
        for (CommentEntity commentData : commentEntity) {
            if (commentData.getArticleId().equals(id)) {
                commentBox.add(commentData);
            }
        }
        ArticleResponseDto articleResponseDtos = new ArticleResponseDto(target);

        System.out.println(articleResponseDtos.getUsername());
        if (!bringUserName().equals(articleResponseDtos.getUsername())) {
            System.out.println(bringUserName());
            articleResponseDtos.setIsMyArticles(Boolean.FALSE);
        }


        ArticleDetailResponseDto articleDetailResponseDto = new ArticleDetailResponseDto(articleResponseDtos, commentBox);
        return articleDetailResponseDto;
    }

    //     페이지네이션
//    @Transactional
//    public List<ArticleResponseDto> getArticles(int size, int page) {
//        String LoginUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//
////        3. 페이지 내림차순으로 정렬을 해주겠다
//        Sort.Direction direction = Sort.Direction.DESC;
//
////        2. 정렬 방향설정
////        Sort sort = Sort.by(direction, sortBy);
//
////        1. 페이지의 크기, 페이지 수, 정렬방법 설정
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<ArticleEntity> articleEntity = articleRepository.findAllDesc(pageable);
//
//
//        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
//        for (ArticleEntity articles : articleEntity) {
//            articleResponseDtos.add(new ArticleResponseDto(articles));
//        }
//        for (ArticleResponseDto datas : articleResponseDtos) {
//            if (!datas.getUsername().equals(LoginUsername)) {
//                datas.setIsMyArticles(Boolean.FALSE);
//            }
//        }
////        return articleResponseDtos;
//        return articleResponseDtos;
//    }

}
