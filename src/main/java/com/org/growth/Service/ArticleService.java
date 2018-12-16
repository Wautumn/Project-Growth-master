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
        img[6]="http://upload.art.ifeng.com/2017/0425/1493105660290.jpg";
        img[7]="https://a.bbkz.net/forum/gallery/images/1343/large/1_DSC03604a.jpg";
        img[8]="http://tu.587kan.com/c171108/15100F4214c30-19143.jpg";
        img[9]="https://www.dilianna.com/wp-content/uploads/2018/07/1508759490-2807984048.jpg";
        img[10]="http://seopic.699pic.com/photo/50053/8949.jpg_wh1200.jpg";
        img[11]="http://pic.sc.chinaz.com/files/pic/pic9/201801/bpic5121.jpg";
        img[12]="https://5b0988e595225.cdn.sohucs.com/images/20180604/850558d4a27e4c9bb01bec3a945ee535.jpeg";
        img[13]="http://pic.zsucai.com/files/2013/1022/gududechangy1.jpg";
        img[14]="https://s11.sinaimg.cn/mw690/004hgq0Ozy7eVD9tgFA5a&690";
        img[15]="http://statics.logohhh.com/forum/201811/12/212547voc41v6awsckoxc7.jpg";
        img[16]="http://soft.yesky.com/imagelist/2009/114/5pw6st9wo3c3.jpg";
        img[17]="https://desk-fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/00/ChMkJlbKw0WIcjXGAA--xnhwdi4AALG0QOAIDAAD77e961.jpg";
        img[18]="http://images.xuejuzi.cn/1505/1_150511223801_1.jpg";
        img[19]="http://img.mp.itc.cn/upload/20170517/110d32467aa346279231b85562664e03_th.jpg";
        img[20]="http://youmeitu.oss-cn-hongkong.aliyuncs.com/docs/allimg/180317/4-1P31G31934.jpg";
        img[21]="http://pic1.win4000.com/wallpaper/9/57a1484ad776c.jpg";
        img[22]="http://img.h1365.cn/uploads3/sc/jpgs/1808/zzpic13803_sc115.com.jpg";
        img[23]="http://img1.xiazaizhijia.com/walls/20160801/1440x900_f771bfcd95db53a.jpg";
        img[24]="http://upload.art.ifeng.com/2016/0322/1458614532688.jpg";
        img[25]="http://pic1.win4000.com/wallpaper/9/59dadcc07f9e4.jpg";
        img[26]="http://588ku.qiao88.com/back_pic/04/85/24/9158dd079dc09e0.jpg!/fw/650";
        img[27]="http://img1.xiazaizhijia.com/walls/20160801/1440x900_f771bfcd95db53a.jpg";
        img[28]="http://www.xinhuanet.com/tw/2016-09/10/129275383_14734037448631n.jpeg";
        img[29]="http://wlzb.net/community/data/attachment/group/0f/group_639_banner.jpg";
        img[30]="https://i3.3conline.com/images/piclib/201112/11/batch/1/120397/1323616140104fb8czmk2uu.jpg";
        img[31]="https://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/63d9f2d3572c11df23821de8642762d0f603c2ae.jpg";
        img[32]="http://pic1.win4000.com/wallpaper/f/59801be5b47c7.jpg";
        img[33]="http://img2.manshijian.com/upload/member/image/31911/d7b07256b5aca4da493d9ef5acf6dce0.jpg";
        img[34]="https://img.91ddcc.com/14803153228639.jpg";
        img[35]="http://www.52desktop.cn/upimg/allimg/080519/12111631AN04a549.jpg";
        img[36]="http://a1.att.hudong.com/72/39/01300000857962127234391168755.jpg";
        img[37]="http://mmweb.tw/sys/ieb/pic/m85416_2b.jpg";
        img[38]="https://desk-fd.zol-img.com.cn/t_s960x600c5/g5/M00/08/00/ChMkJ1exsLCIULDFAAedtOIaCSsAAUdOAE_UEYAB53M287.jpg";
        img[39]="http://5b0988e595225.cdn.sohucs.com/images/20180421/5a1115697f3a43b6a7841c12cfaf87b8.jpeg";
        img[40]="http://5b0988e595225.cdn.sohucs.com/images/20180421/b21060e5de854c8e87c0157a4dedff09.jpeg";
        img[41]="http://5b0988e595225.cdn.sohucs.com/images/20180421/193a3464c8ba4713ac2d3f2fb93829c7.jpeg";
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
           // recommendArticle.get(i).setImg(img[1]);
        }

        return  recommendArticle;
    }

    public List<Integer> getTags() {
        return tags;
    }
}


