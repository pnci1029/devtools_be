package com.example.demo.controller;

import com.example.demo.dto.ArticleDetailResponseDto;
import com.example.demo.dto.ArticleDto;
import com.example.demo.entity.ArticleEntity;
import com.example.demo.dto.ArticleResponseDtoList;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleController {

    private ArticleService articleService;


    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //    페이지네이션 완료
//    @GetMapping("/api/articles")
//    public List<ArticleResponseDto> getArtciels(
//            @RequestParam("size") int size,
//            @RequestParam("page") int page
//    ) {
////        4. 클라이언트 입장에서는 페이지가 1페이지부터 시작해서 1빼줌
//        page = page - 1;
//        return articleService.getArticles(size, page);
//    }

    @GetMapping("/articles")
    public ArticleResponseDtoList getArtciels() {
        return articleService.getArticles();
    }

    @PostMapping("/articles")
    public void RpostArticles(@RequestBody ArticleDto articleDto) {
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
