package com.org.growth.Service;

import com.org.growth.entity.Article;
import com.org.growth.entity.History;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Query;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;

@Component
public class ArticleService {

    @Resource
    private MongoTemplate mongoTemplate;

    /*
    获取历史记录
     */
    public List<String>getHistoryTaskDescription(long userId){

        List<String> recordContent=new LinkedList<>();
        Query query=Query.query(Criteria.where("userId").is(userId));
        query.limit(50);//五十条
        List<History> histories=mongoTemplate.find(query,History.class);
        //通过历史记录去加载任务内容
        for(History h:histories){
            long TaskId=h.getTaskId();
            Query query1=Query.query(Criteria.where("id").is(TaskId));
            com.org.growth.entity.Task task=mongoTemplate.findOne(query1, com.org.growth.entity.Task.class);
            String TaskConntent=" ";
            if(task!=null)  TaskConntent=task.getDescription();
            recordContent.add(TaskConntent+',');//顺序
        }
        return recordContent;
    }

    /*
    转成string
     */
    public String getStringHistory(Long userId){
        List<String> taskContent=getHistoryTaskDescription(userId);
        String allContext="";
        Iterator iterator=taskContent.iterator();
        for(int i=0;i<taskContent.size();i++){
            allContext=allContext+taskContent.get(i);

        }
        return allContext;
    }

    /*
       获取热门文章 tag为0
    */
    public List<Article> getPopularArticle(){
        Random random = new Random();
        int max=20;
        int min=11;
        Object[] values = new Object[5];
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for(int i = 0;i < values.length;i++){
            int number =  random.nextInt(max)%(max-min+1) + min;//11-20的随机数
            hashMap.put(number, i);
        }
        values = hashMap.keySet().toArray();
        for(int i = 0;i < values.length;i++) {
           // System.out.print(values[i] + "\t");
        }
        List<Article> articles =new LinkedList<>();
        for(int i=0;i<5;++i) {
            Query query = Query.query(Criteria.where("tags").is(values[i]));
            query.limit(5);
            List<Article> nowarticles = mongoTemplate.find(query, Article.class);
            articles.addAll(nowarticles);
        }
        return articles;
    }


    /*
    根据tags推荐
     */
    public List<Article> getRecommendArticle(List<Integer> tags){
        List<Article> recommendArticle=new LinkedList<>();
        Random random = new Random();
        int max=20;
        int min=11;
        while(tags.size()<6)//无标签
        {
            tags.add(random.nextInt(max)%(max-min+1) + min);
        }
        for(int i=0;i<tags.size();i++) {
            Query query = Query.query(Criteria.where("tags").is(tags.get(i)));
            query.limit(5);
            List<Article> articles=mongoTemplate.find(query,Article.class);
            recommendArticle.addAll(articles);
        }
        return  recommendArticle;
    }








}


