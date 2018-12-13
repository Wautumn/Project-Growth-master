package com.org.growth.controller;

import com.org.growth.Service.ArticleService;
import com.org.growth.Service.KeywordRelated;
import com.org.growth.Service.UserService;
import com.org.growth.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService=new ArticleService();

    @Autowired
    UserService userService=new UserService();

    @Autowired
    KeywordRelated keywordRelated=new KeywordRelated();

    //热门文章
    @GetMapping(value = "/getPopularArticle")
    public List<Article> performPopularArticle(){
        return articleService.getPopularArticle();
    }

    @GetMapping(value = "/getRecommendArticle")
    public List<Article> performRecommendArticle(@RequestParam(value = "userId")long userId)throws Exception{
        Article article=new Article("sorry....");
        String context="";
        List<String> keywords=new LinkedList<>();
        List<Integer> keyTags=new LinkedList<>();
        List<Article> articles=new LinkedList<>();
        if(userService.findByUserId(userId)==null){
          return performPopularArticle();//返回热门文章
        }
        context=articleService.getStringHistory(userId);
        keywords=keywordRelated.GetKeywords(context,10);
        keyTags=keywordRelated.getUsertags(keywords);
        articles=articleService.getRecommendArticle(keyTags);
        if(articles==null){articles.add(article);}
        return articles;

    }



}
