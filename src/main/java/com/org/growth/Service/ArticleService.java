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

    private String[] img = new String[42];

    public void setimg(){
        img[0]="http://5b0988e595225.cdn.sohucs.com/images/20180421/193a3464c8ba4713ac2d3f2fb93829c7.jpeg";
        img[1]="http://5b0988e595225.cdn.sohucs.com/images/20180421/7e2fe577b2d84d02b432f25f921b4959.jpeg";
        img[2]="http://5b0988e595225.cdn.sohucs.com/images/20180421/5a1115697f3a43b6a7841c12cfaf87b8.jpeg";
        img[3]="http://p1.pstatp.com/origin/2a4100008d310b5fd8a8";
        img[4]="http://p3.pstatp.com/origin/2a4200001e17dc84eb58";
        img[5]="http://soft.yesky.com/imagelist/2009/114/5pw6st9wo3c3.jpg";
        img[6]="http://img.ivsky.com/img/tupian/pre/201804/16/maomi_senlin.jpg";
        img[7]="http://img.ivsky.com/img/tupian/pre/201804/16/maomi_senlin-003.jpg";
        img[8]="http://img.ivsky.com/img/tupian/pre/201804/07/hupo.jpg";
        img[9]="http://img.ivsky.com/img/tupian/pre/201804/07/hupo-003.jpg";
        img[10]="http://img.ivsky.com/img/tupian/pre/201804/07/hupo-012.jpg";
        img[11]="http://img.ivsky.com/img/tupian/pre/201803/19/meilidehaitan.jpg";
        img[12]="http://img.ivsky.com/img/tupian/pre/201805/19/shanmai_senlin-001.jpg";
        img[13]="http://img.ivsky.com/img/tupian/pre/201805/19/shanmai_senlin-008.jpg";
        img[14]="http://img.ivsky.com/img/tupian/pre/201804/11/tiankong_baiyun-002.jpg";
        img[15]="http://img.ivsky.com/img/tupian/pre/201804/11/tiankong_baiyun-012.jpg";
        img[16]="http://img.ivsky.com/img/tupian/pre/201804/10/fanxing_yekong.jpg";
        img[17]="http://img.ivsky.com/img/tupian/pre/201804/10/fanxing_yekong-003.jpg";
        img[18]="http://img.ivsky.com/img/tupian/pre/201804/10/fanxing_yekong-012.jpg";
        img[19]="http://img.ivsky.com/img/tupian/pre/201804/10/xiyang_jingse-001.jpg";
        img[20]="http://img.ivsky.com/img/tupian/pre/201804/10/xiyang_jingse-008.jpg";
        img[21]="http://img.ivsky.com/img/tupian/pre/201804/10/xiyang_jingse-014.jpg";
        img[22]="http://img.ivsky.com/img/tupian/pre/201801/23/dongjidehubian-006.jpg";
        img[23]="http://img.ivsky.com/img/tupian/pre/201801/23/dongjidehubian-009.jpg";
        img[24]="http://img.ivsky.com/img/tupian/pre/201801/26/wuhoudehaibian-003.jpg";
        img[25]="http://img.ivsky.com/img/tupian/pre/201801/18/luozaidishangdeshuye.jpg";
        img[26]="http://img.ivsky.com/img/tupian/pre/201801/18/luozaidishangdeshuye-009.jpg";
        img[27]="http://img.ivsky.com/img/tupian/pre/201801/18/luozaidishangdeshuye-013.jpg";
        img[28]="http://img.ivsky.com/img/tupian/pre/201712/30/xuejing-022.jpg";
        img[29]="http://img.ivsky.com/img/tupian/pre/201712/30/jiebaiwuxiadexuejingtupian.jpg";
        img[30]="http://img.ivsky.com/img/tupian/pre/201712/30/shushangdejixuetupian-001.jpg";
        img[31]="http://img.ivsky.com/img/tupian/pre/201711/01/shitou-006.jpg";
        img[32]="http://img.ivsky.com/img/tupian/pre/201710/28/haimian-002.jpg";
        img[33]="http://img.ivsky.com/img/tupian/pre/201710/28/fanzhebowendepingjinghaimiantupian.jpg";
        img[34]="http://img.ivsky.com/img/tupian/pre/201711/27/yangguang-005.jpg";
        img[35]="http://img.ivsky.com/img/tupian/pre/201711/27/yangguang-014.jpg";
        img[36]="http://img.ivsky.com/img/tupian/pre/201711/22/zhaoyaojinlaideyangguang-002.jpg";
        img[37]="http://img.ivsky.com/img/tupian/pre/201711/21/haitan.jpg";
        img[38]="http://img.ivsky.com/img/tupian/pre/201711/21/haitan-011.jpg";
        img[39]="http://img.ivsky.com/img/tupian/pre/201712/14/meilidexuehua-010.jpg";
        img[40]="http://img.ivsky.com/img/tupian/pre/201712/14/meilideshulin-007.jpg";
        img[41]="http://img.ivsky.com/img/tupian/pre/201712/30/xuejing-001.jpg";
    }


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
            else if(tags.get(i)==20)keytag.put(20,"美食");

        }
        return keytag;
    }


    public List<Article> getPopularArticle(){
        this.tags.clear();
        Random random = new Random();
        int max=20;
        int min=11;
        Object[] values = new Object[5];
        //System.out.print(values.length+"aaa");
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
        Object[] value=getRandom();

        setimg();

        for(int i=0;i<articles.size();i++){
            articles.get(i).setImg(img[Integer.parseInt(value[i].toString())]);
        }

        return articles;
    }
    private Object[] getRandom(){
        Object[] values=new Object[25];
        Random random = new Random();
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
       while (hashMap.size()<25){
            int number = random.nextInt(41) ;//0-41
            hashMap.put(number, hashMap.size());
        }
        values = hashMap.keySet().toArray();
        return values;
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
            tags.add((int)values[i]);
        }

        this.tags=tags;
      //  System.out.println(this.tags.size()+"ssssss");

        for(int i=0;i<tags.size();i++) {
            Query query = Query.query(Criteria.where("tags").is(tags.get(i)));
            query.limit(5);
            List<Article> articles=mongoTemplate.find(query,Article.class);
            recommendArticle.addAll(articles);
        }
        //for(int i=0;i<recommendArticle.size();i++){
        setimg();
        Object[] value=getRandom();


        for(int i=0;i<recommendArticle.size();i++){
          //  System.out.println(i+"i  "+Integer.parseInt(value[i].toString()));
         //   System.out.println("img  "+img[Integer.parseInt(value[i].toString())-1]);
            recommendArticle.get(i).setImg(img[Integer.parseInt(value[i].toString())]);
        }

        return  recommendArticle;
    }

    public List<Integer> getTags() {
        return tags;
    }
}


