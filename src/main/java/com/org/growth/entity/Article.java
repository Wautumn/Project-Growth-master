package com.org.growth.entity;

import com.org.growth.Other.AutoIncrement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*
推荐文章板块
每一个文章有一些标签，用户关键词与标签匹配
 */
@Document(collection = "Article")
public class Article {


    @Id
    private long id;

    @Field("tags")
    private int tags;

    @Field("title")
    private String title;

    @Field("content")
    private String content;

    @Field("clickcount")
    private String clickcount;

    @Field("author")
    private String author;

    @Field("url")
    private String url;

    public int getTags() {
        return tags;
    }

    public void setTags(int tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClickcount() {
        return clickcount;
    }

    public void setClickcount(String clickcount) {
        this.clickcount = clickcount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Article(int id,String title, String content, String author, String clickcount, String url,int tags){
        this.id=id;
        this.url=url;
        this.author=author;
        this.clickcount=clickcount;
        this.content=content;
        this.tags=tags;
        this.title=title;


    }




}
