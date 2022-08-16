package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.ArticleEntity;
import com.example.demo.entity.CommentEntity;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    public String getLoginUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    public CommentEntity postComment(Long articleId, CommentDto dto) {
//      게시글 확인
        ArticleEntity target = articleRepository.findById(articleId).orElseThrow(
                () -> new NullPointerException("댓글을 달기 위한 게시물이 존재하지 않습니다.")
        );
        if (dto.getComment().length() < 1 || dto.getComment().length() > 40) {
            throw new IllegalArgumentException("댓글은 1~40글자로 입력해주세요");
        }
        CommentEntity result = new CommentEntity(dto, getLoginUserName(),target, articleId);

        commentRepository.save(result);
        return result;
    }
}
