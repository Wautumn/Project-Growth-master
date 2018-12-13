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

    @Field("intro")
    private String intro;

    @Field("clickcount")
    private String clickcount;

    @Field("author")
    private String author;

    @Field("url")
    private String url;

    @Field("img")
    private String img;

    public Article(){

    }

    public Article(String title){
        this.title=title;

    }



    public Article(int id,String title, String intro, String author, String clickcount, String url,int tags){
        this.id=id;
        this.url=url;
        this.author=author;
        this.clickcount=clickcount;
        this.intro=intro;
        this.tags=tags;
        this.img=img;
        this.title=title;


    }

    public Article(int id,String title,String intro,String author,String url,int tags,String img){
        this.id=id;
        this.url=url;
        this.author=author;
        this.img=img;
        this.intro=intro;
        this.tags=tags;
        this.title=title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
