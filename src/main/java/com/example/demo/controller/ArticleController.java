package com.example.demo.controller;

import com.example.demo.dto.ArticleDetailResponseDto;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.entity.ArticleEntity;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000, https://devtools-si83a57cz-green9930.vercel.app") // http
//@CrossOrigin(origins = "http://localhost:3000, https://devtools-gewfe9kn1-green9930.vercel.app") // https
//@CrossOrigin(origins = "https://devtools-gewfe9kn1-green9930.vercel.app,https://devtools-si83a57cz-green9930.vercel.app, http://localhost:3000", allowedHeaders = "*", allowCredentials = "true", exposedHeaders = "*")

//@CrossOrigin(origins = "https://devtools-gewfe9kn1-green9930.vercel.app,https://devtools-si83a57cz-green9930.vercel.app, http://localhost:3000", allowedHeaders = "*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

//        페이지네이션 완료
//    @GetMapping("/articles")
//    public List<ArticleResponseDto> getArtciels(
//            @RequestParam("size") int size,
//            @RequestParam("page") int page
//    ) {
////        4. 클라이언트 입장에서는 페이지가 1페이지부터 시작해서 1빼줌
//        page = page - 1;
//        return  articleService.getArticles(size, page);
//    }

    @GetMapping("/articles")
    public List<ArticleResponseDto> getArticles() {
        return articleService.getArticles();
    }

    @PostMapping("/articles")
    public void postArticles(@RequestBody ArticleDto articleDto) {
        articleService.postArticles(articleDto);
    }

    @PatchMapping("/articles/{id}")
    public ArticleEntity patchArticles(@PathVariable Long id, @RequestBody ArticleDto dto) {
        return articleService.patchArticles(dto, id);
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticles(@PathVariable Long id) {
        articleService.deleteArticles(id);
    }

    @GetMapping("/articles/{id}")
    public ArticleDetailResponseDto getDetailArticles(@PathVariable Long id) {
        return articleService.getDetailArticles(id);
    }

}
