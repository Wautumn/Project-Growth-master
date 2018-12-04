package com.org.growth.Service;

import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.corpus.StopWords;
import org.fnlp.app.keyword.AbstractExtractor;
import org.fnlp.app.keyword.WordExtract;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KeywordRelated {
    /*
    获取输入一段string中的关键词，数目可选,因为关键词获取有误差，所以前多少个都是吧，value先丢弃，后面要再去写
     */

    public List<String> GetKeywords(String News, int keywordsNumber) throws Exception {
        ArrayList<String> keywords = new ArrayList<String>();
        Map<String, Object> keywordandvalue = new HashMap<String, Object>();

        StopWords sw = new StopWords("models/stopwords");
        CWSTagger seg = new CWSTagger("models/seg.m");
        AbstractExtractor key = new WordExtract(seg, sw);

        Map<String, Integer> ans = key.extract(News, keywordsNumber);

        for (Map.Entry<String, Integer> entry : ans.entrySet()) {
            String keymap = entry.getKey().toString();
            String value = entry.getValue().toString();
            keywords.add(keymap);//关键词集合
            keywordandvalue.put(keymap, value);//带value的map
          //  System.out.println("key=" + keymap + " value=" + value);
        }
        return keywords;
    }

    /*
    得到关键词以后，去进行关键词tag匹配,每个用户维持一个tag表

     */
    public List<Integer> getUsertags(List<String> keywords){
      List<Integer> tagspage= new LinkedList();
      if(keywords.contains("计算机")) tagspage.add(1);
      if(keywords.contains("Java")) tagspage.add(2);
      if(keywords.contains("网络")) tagspage.add(3);
      if(keywords.contains("编译")) tagspage.add(4);
      if(keywords.contains("数学")) tagspage.add(5);
      if(keywords.contains("数据库")) tagspage.add(6);
      if(keywords.contains("软件工程")) tagspage.add(7);
      if(keywords.contains("前端")) tagspage.add(8);
      if(keywords.contains("机器学习")) tagspage.add(9);

      if(keywords.contains("学习")) tagspage.add(-1);
      if(keywords.contains("看")) tagspage.add(-1);


      return tagspage;

    }



}










