package com.org.growth.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/*
推荐文章板块
每一个文章有一些标签，用户关键词与标签匹配
 */
@Document(collection = "Article")
public class Recommend {

    private Long id;

    private int[] tags;

    private String title;

    private String content;

    private int clickcount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getClickcount() {
        return clickcount;
    }

    public void setClickcount(int clickcount) {
        this.clickcount = clickcount;
    }

    public int[] getTags() {
        return tags;
    }

    public void setTags(int[] tags) {
        this.tags = tags;
    }
}
