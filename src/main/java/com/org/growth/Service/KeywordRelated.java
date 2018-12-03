package com.org.growth.Service;

import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.corpus.StopWords;
import org.fnlp.app.keyword.AbstractExtractor;
import org.fnlp.app.keyword.WordExtract;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class KeywordRelated {
    /*
    获取输入一段string中的关键词，数目可选,因为关键词获取有误差，所以前多少个都是吧，value先丢弃
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


}










