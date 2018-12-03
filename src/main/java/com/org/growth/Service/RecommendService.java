package com.org.growth.Service;

import edu.fudan.nlp.cn.CNFactory;
import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.corpus.StopWords;

import com.org.growth.DAO.RecommendDAO;
import com.org.growth.entity.History;

import edu.fudan.util.exception.LoadModelException;
import org.fnlp.app.keyword.AbstractExtractor;
import org.fnlp.app.keyword.WordExtract;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Query;
import javax.annotation.Resource;
import java.util.*;

@Component
public class RecommendService {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<String>getHistoryTaskDescription(Long userId){

        List<String> recordContent=new LinkedList<>();
        Query query=Query.query(Criteria.where("userId").is(userId));
        query.limit(50);//五十条
        List<History> histories=mongoTemplate.find(query,History.class);
        //通过历史记录去加载任务内容
        for(History h:histories){
            Long TaskId=h.getTaskId();
            Query query1=Query.query(Criteria.where("id").is(TaskId));
            com.org.growth.entity.List list=mongoTemplate.findOne(query1, com.org.growth.entity.List.class);
            String TaskConntent=list.getDescription();
            recordContent.add(recordContent.size(),TaskConntent);//顺序
        }
        return recordContent;

    }
   // ArrayList<String>
   /* public void  GetKeyword(String News, int keywordsNumber) throws Exception{
        ArrayList<String> keywords=new ArrayList<String>();
        Map<String,Object> keywordandvalue = new HashMap<String ,Object>();
        StopWords sw= new StopWords("models/stopwords");
        CWSTagger seg = new CWSTagger("models/seg.m");
        AbstractExtractor key = new WordExtract(seg, sw);

        Map<String, Integer> ans = key.extract(News, keywordsNumber);

        for (Map.Entry<String, Integer> entry : ans.entrySet()) {
            String keymap = entry.getKey().toString();
            String value = entry.getValue().toString();
            keywords.add(keymap);
            keywordandvalue.put(keymap, value);//带value的map
            System.out.println("key=" + keymap + " value=" + value);
            }
        System.out.println(key.extract("甬温线特别重大铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走", 20, true));


        // return keywords;
    }*/
  /* public ArrayList<String> GetKeyword(String News) {
       CNFactory factory;
       List<Map.Entry<String,Integer>>sortedWordList;
       try{
           factory=CNFactory.getInstance(mFileReader.getModelPath());

           String[] words=factory.seg(News);

           List<String> noStopWords=mPhraseDel(words);

           CCNWordsCounter wc=new CCNWordsCounter();

           for(String s:noStopWordsStr)
           {
               wc.addWordCount(s);
           }
           //根据词的频次排序
           sortedWordsList = sortHashMap(wc.gethMap());
           for (int i = 0; i < (19<=sortedWordsList.size()?19:sortedWordsList.size()); i++) {
               Map.Entry<String,Integer> ent=sortedWordsList.get(i);
               Top20WordsList.add(ent);
           }
       } catch (LoadModelException e) {
           log.info("加载语言处理模型失败！");
           e.printStackTrace();
       }


       }
   }
    private List<String> mPhraseDel(String[] words){
        StopWords sw = new StopWords();
        sw.read(mFileReader.getStopwordsPath());
        List<String> list = new ArrayList<String>();
        String s;
        int length= words.length;
        for(int i = 0; i < length; i++){
            s = words[i];
            if(!sw.isStopWord(s,2,4))
                list.add(s);
        }
        return list;
    }
*/

    /*
    获取十个用户关键词
     */
   /* @Override
    public ArrayList<String>  getUserKeyword(Long userId)throws Exception
    {
        List<String> taskContent=getHistoryTaskDescription(userId);
        String allContext="";
        Iterator iterator=taskContent.iterator();
        while (iterator.hasNext()){
            allContext=allContext+iterator.next();

        }
        ArrayList<String> keywordset=GetKeyword(allContext,10);
        return keywordset;

    }*/



}


