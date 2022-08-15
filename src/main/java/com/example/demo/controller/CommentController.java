package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.CommentEntity;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/articles/{articleId}/comments")
    public CommentEntity postComment(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        return commentService.postComment(articleId, dto);
    }

//    @GetMapping("/{id}")
//    public String getComment(@PathVariable Long id) {
//
//        return commentService.getComment(id);
//    }
}