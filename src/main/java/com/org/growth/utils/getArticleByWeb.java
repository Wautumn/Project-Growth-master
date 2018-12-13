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
        Document doc = null;

        try {
            if (num == 11) { doc = Jsoup.connect("https://www.csdn.net/nav/newarticles").get(); }//热门文章
            else if (num == 12) { doc = Jsoup.connect("https://www.douban.com/explore/column/10").header("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36").get(); }//读书
            else if (num == 13) { doc = Jsoup.connect("https://www.douban.com/explore/column/11").get(); }//生活
            else if (num == 14) { doc = Jsoup.connect("https://www.douban.com/explore/column/14").get(); }//自然
            else if (num == 15) { doc = Jsoup.connect("https://www.douban.com/explore/column/7").get(); }//艺术
            else if (num == 16) { doc = Jsoup.connect("https://www.douban.com/explore/column/25").get(); }//成长
            else if (num == 17) { doc=Jsoup.connect("https://www.douban.com/explore/column/2").get();}//旅游
            else if (num == 18) { doc=Jsoup.connect("https://www.douban.com/explore/column/13").get();}//人文
            else if (num == 19) { doc=Jsoup.connect("https://www.douban.com/explore/column/20").get();}//科技
            else if (num == 20) { doc=Jsoup.connect("https://www.douban.com/explore/column/12").get();}//美食
            else if (num == 1) {doc = Jsoup.connect("https://www.csdn.net/nav/fund").get(); }//计算机
            else if (num == 2) { doc = Jsoup.connect("https://www.csdn.net/nav/lang").get(); }//JAVA，编程语言
            else if (num == 3) { doc = Jsoup.connect("https://www.csdn.net/nav/newarticles").get(); }//网络
            else if (num == 4) { doc = Jsoup.connect("https://www.csdn.net/nav/engineering").get(); }//研发，管理
            else if (num == 5) { doc = Jsoup.connect("https://www.csdn.net/nav/newarticles").get(); }//数学
            else if (num == 6) { doc = Jsoup.connect("https://www.csdn.net/nav/db").get(); }//数据库
            else if (num == 7) { doc = Jsoup.connect("https://www.csdn.net/nav/newarticles").get(); }//软件工程
            else if (num == 8) { doc = Jsoup.connect("https://www.csdn.net/nav/web").get(); }//前端
            else if (num == 9) { doc = Jsoup.connect("https://www.csdn.net/nav/ai").get(); }//机器学习人工智能
            else if (num == 0) { doc = Jsoup.connect("https://www.csdn.net/nav/career").get(); }//程序人生
            else { doc = Jsoup.connect("https://www.csdn.net/nav/other").get(); }//其他

            if(1<=num&&num<=11) getCSDN(doc,num);
            else  {
                //head("User-Agent","User-Agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36");

                getDOUBAN(doc,num);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getCSDN(Document document, int num) {
        try {
            Elements titleLinks = document.select("div.title");
            Elements IntroLinks = document.select("div.summary.oneline");
            System.out.println(titleLinks.size()+"is"+num);
            for (int i = 0; i < titleLinks.size(); i++) {
                String title = "";
                String img="";
                title = titleLinks.get(i).select("h2").select("a").text();//标题
                String uri = titleLinks.get(i).select("h2").select("a").attr("href");//url
                String intro = IntroLinks.get(i).text();
                Document doc1 = Jsoup.connect(uri).get();//#mainBox > main > div.blog-content-box > div > div > div.article-info-box
                //Elements desLinks=doc1.select("div#content_views");
                String author = "a";
                author = doc1.select("div.article-bar-top").select("a.follow-nickName").text();
                String readCount = doc1.select("div.article-bar-top").select("span.read-count").text();

                // Long h=new Long(String.valueOf( i+1 ))
                Article article = new Article((100 * num + i), title, intro, author,uri, num,img);//先把他tag设置为1
                mongoTemplate.insert(article);
            }

        } catch (Exception e) {

        }
    }

    private void getDOUBAN(Document document,int num){
        if(document==null) System.out.println("null");
        try{
        Elements titleLinks = document.select("div.item");
     //   Elements IntroLinks = document.select("div.content").select("p");
        System.out.println(num+ "a" + titleLinks.size());

        for (int i = 0; i < titleLinks.size(); i++) {
            String title = "";
            title = titleLinks.get(i).select("div.title").select("a").text();//标题
            String uri = titleLinks.get(i).select("div.title").select("a").attr("href");//url
            String intro = titleLinks.get(i).select("p").text();
            String img="";
            Document doc1 = Jsoup.connect(uri).get();
            String author = doc1.select("a.note-author").text();
            Article article = new Article((100 * num + i), title, intro, author, uri,num, img);//先把他tag设置为1

           // System.out.println("title" + article.getTitle());
            mongoTemplate.insert(article);
        }

    } catch (Exception e) {
            e.printStackTrace();
    }

}
}




