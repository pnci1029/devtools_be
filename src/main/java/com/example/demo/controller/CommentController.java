package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.CommentEntity;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000, https://devtools-si83a57cz-green9930.vercel.app") // http
//@CrossOrigin(origins = "http://localhost:3000, https://devtools-gewfe9kn1-green9930.vercel.app") // https
//@CrossOrigin(origins = "https://devtools-gewfe9kn1-green9930.vercel.app,https://devtools-si83a57cz-green9930.vercel.app, http://localhost:3000", allowedHeaders = "*", allowCredentials = "true", exposedHeaders = "*")

//@CrossOrigin(origins = "https://devtools-gewfe9kn1-green9930.vercel.app,https://devtools-si83a57cz-green9930.vercel.app, http://localhost:3000", allowedHeaders = "*")


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

}
