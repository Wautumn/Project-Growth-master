package com.org.growth.utils;

import com.org.growth.entity.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
爬Article数据
 */
@Component
public class getArticleByWeb {

    @Resource
    private MongoTemplate mongoTemplate;

    public void get(int num) {
        Document doc=null;

        try{
            if(num==0) { doc= Jsoup.connect("https://www.csdn.net/nav/newarticles").get();}//热门文章
            if(num==1) { doc= Jsoup.connect("https://www.csdn.net/nav/fund").get();}//计算机
            if(num==2) { doc= Jsoup.connect("https://www.csdn.net/nav/lang").get();}//JAVA，编程语言
            if(num==3) { doc= Jsoup.connect("https://www.csdn.net/nav/newarticles").get();}//网络
            if(num==4) { doc= Jsoup.connect("https://www.csdn.net/nav/engineering").get();}//研发，管理
            if(num==5) { doc= Jsoup.connect("https://www.csdn.net/nav/newarticles").get();}//数学
            if(num==6) { doc= Jsoup.connect("https://www.csdn.net/nav/db").get();}//数据库
            if(num==7) { doc= Jsoup.connect("https://www.csdn.net/nav/newarticles").get();}//软件工程
            if(num==8) { doc= Jsoup.connect("https://www.csdn.net/nav/web").get();}//前端
            if(num==9) { doc= Jsoup.connect("https://www.csdn.net/nav/ai").get();}//机器学习人工智能
            else
            {
                doc= Jsoup.connect("https://www.csdn.net/nav/other").get();
            }
            Elements titleLinks=doc.select("div.title");
            Elements IntroLinks=doc.select("div.summary.oneline");
            System.out.println(titleLinks.size());
            for(int i=0;i<titleLinks.size();i++){
                String title="";
                title=titleLinks.get(i).select("h2").select("a").text();//标题
                String uri=titleLinks.get(i).select("h2").select("a").attr("href");//url
                String intro=IntroLinks.get(i).text();
                Document doc1=Jsoup.connect(uri).get();//#mainBox > main > div.blog-content-box > div > div > div.article-info-box
                //Elements desLinks=doc1.select("div#content_views");
                String author="a";
                author=doc1.select("div.article-bar-top").select("a.follow-nickName").text();
                String readCount=doc1.select("div.article-bar-top").select("span.read-count").text();

                // Long h=new Long(String.valueOf( i+1 ))
                 Article article=new Article((100*num+i),title,intro,author,readCount,uri,num);//先把他tag设置为1

                //  System.out.println(article.getTitle());
             //    mongoTemplate.insert(article);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
