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

    private int[] values= new int[5];
    private List<Integer> tags=new LinkedList<>();


    public Map<Integer,String> keyanftag(List<Integer> tags){
        Map<Integer,String> keytag=new HashMap<>();
        for(int i=0;i<tags.size();++i){
            if(tags.get(i)==0)keytag.put(0,"程序人生");
            else if(tags.get(i)==1)keytag.put(1,"计算机");
            else if(tags.get(i)==2)keytag.put(2,"java");
            else if(tags.get(i)==3)keytag.put(3,"网络");
            else if(tags.get(i)==4)keytag.put(4,"研发管理");
            else if(tags.get(i)==5)keytag.put(5,"游戏开发");
            else if(tags.get(i)==6)keytag.put(6,"数据库");
            else if(tags.get(i)==7)keytag.put(7,"软件工程");
            else if(tags.get(i)==8)keytag.put(8,"前端");
            else if(tags.get(i)==9)keytag.put(9,"机器学习");
           // else if(tags.get(i)==10)keytag.put(10,"程序人生");
            else if(tags.get(i)==11)keytag.put(11,"热门");
            else if(tags.get(i)==12)keytag.put(12,"读书");
            else if(tags.get(i)==13)keytag.put(13,"生活");
            else if(tags.get(i)==14)keytag.put(14,"自然");
            else if(tags.get(i)==15)keytag.put(15,"艺术");
            else if(tags.get(i)==16)keytag.put(16,"成长");
            else if(tags.get(i)==17)keytag.put(17,"旅游");
            else if(tags.get(i)==18)keytag.put(18,"人文");
            else if(tags.get(i)==19)keytag.put(19,"科技");
            else if(tags.get(i)==20)keytag.put(20,"科技");
            else keytag.put(0,"程序人生");
        }
        return keytag;
    }


    public List<Article> getPopularArticle(){
        this.tags.clear();
        Random random = new Random();
        int max=20;
        int min=11;
        Object[] values = new Object[5];
        System.out.print(values.length+"aaa");
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        while(hashMap.size()<5){
            int number =  random.nextInt(max)%(max-min+1) + min;//11-20的随机数
            hashMap.put(number, hashMap.size());
        }
        values = hashMap.keySet().toArray();
        //System.out.print(values.length+"kkk");
        for(int i = 0;i < values.length;i++) {
           // [i] + "\t");
            tags.add((int)values[i]);
        }
        List<Article> articles =new LinkedList<>();
        for(int i=0;i<values.length;i++) {
           //  System.out.print(values[i]+"ii");
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
        this.tags.clear();
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        List<Article> recommendArticle=new LinkedList<>();
        for(int i=0;i<tags.size();++i){
            hashMap.put(tags.get(i),i);
        }
        Random random = new Random();
        int max=20;
        int min=11;
        while (hashMap.size()<5)
        {
           // tags.add(random.nextInt(max)%(max-min+1) + min);
            int number =  random.nextInt(max)%(max-min+1) + min;//11-20的随机数
            hashMap.put(number, hashMap.size());
        }
        Object[] values = new Object[5];
        values= hashMap.keySet().toArray();
        for(int i = 0;i < values.length;i++) {
            // [i] + "\t");
            tags.add((int)values[i]);
        }

        this.tags=tags;
        System.out.println(this.tags.size()+"ssssss");

        for(int i=0;i<tags.size();i++) {
            Query query = Query.query(Criteria.where("tags").is(tags.get(i)));
            query.limit(5);
            List<Article> articles=mongoTemplate.find(query,Article.class);
            recommendArticle.addAll(articles);
        }
        return  recommendArticle;
    }

    public List<Integer> getTags() {
        return tags;
    }
}


