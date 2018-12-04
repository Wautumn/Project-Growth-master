package com.org.growth.controller;

import com.org.growth.Service.ArticleService;
import com.org.growth.Service.KeywordRelated;
import com.org.growth.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService=new ArticleService();

    @Autowired
    KeywordRelated keywordRelated=new KeywordRelated();

    //热门文章
    @GetMapping(value = "/getPopularArticle")
    public List<Article> performPopularArticle(){
        return articleService.getPopularArticle();
    }

    @GetMapping(value = "/getRecommendArticle")
    public List<Article> performRecommendArticle(@RequestParam(value = "userId") Long userId)throws Exception{
        String context=articleService.getStringHistory(userId);
        List<String> keywords=keywordRelated.GetKeywords(context,10);
        List<Integer> keyTags=keywordRelated.getUsertags(keywords);
        List<Article> articles=articleService.getRecommendArticle(keyTags);
        return articles;

    }



}
