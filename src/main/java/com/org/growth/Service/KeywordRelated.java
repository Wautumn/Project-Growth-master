package com.org.growth.Service;

import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.corpus.StopWords;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.fnlp.app.keyword.AbstractExtractor;
import org.fnlp.app.keyword.WordExtract;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
public class KeywordRelated {
    /*
    获取输入一段string中的关键词，数目可选,因为关键词获取有误差，所以前多少个都是吧，value先丢弃，后面要再去写
     */

    public List<String> GetKeywords(String News, int keywordsNumber) throws Exception {
        ArrayList<String> keywords = new ArrayList<String>();
        Map<String, Object> keywordandvalue = new HashMap<String, Object>();

        Resource resource=new ClassPathResource("models/seg.m");
        Resource resourceSA=new ClassPathResource("models/stopwords/ErrorWords.txt");
        Resource resourceSB=new ClassPathResource("models/stopwords/NoSenseWords.txt");
        Resource resourceSC=new ClassPathResource("models/stopwords/StopWords.txt");

        Path path= Files.createTempDirectory("stopwords");
        File file1 = File.createTempFile("ErrorWords", ".txt",path.toFile());
        File file2 = File.createTempFile("NoSenseWords", ".txt",path.toFile());
        File file3 = File.createTempFile("StopWords", ".txt",path.toFile());
        File somethingFile = File.createTempFile("seg", ".m");


        InputStream inputStream =resource.getInputStream();
        InputStream inputStream1 =resourceSA.getInputStream();
        InputStream inputStream2=resourceSB.getInputStream();
        InputStream inputStream3 =resourceSC.getInputStream();

        try {
            FileUtils.copyInputStreamToFile(inputStream, somethingFile);
            FileUtils.copyInputStreamToFile(inputStream1, file1);
            FileUtils.copyInputStreamToFile(inputStream2, file2);
            FileUtils.copyInputStreamToFile(inputStream3, file3);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(inputStream1);
            IOUtils.closeQuietly(inputStream2);
            IOUtils.closeQuietly(inputStream3);
        }
        String pathCWS=somethingFile.getPath();
        String pathSW=path.toString();
        System.out.println(pathCWS+"swwwwww");
        System.out.println(somethingFile+"dsdsdssds");

        CWSTagger seg = new CWSTagger(pathCWS);
        StopWords sw = new StopWords(pathSW);
        AbstractExtractor key = new WordExtract(seg, sw);
       /* StopWords sw = new StopWords("models/stopwords");
        CWSTagger seg = new CWSTagger("models/seg.m");
        AbstractExtractor key = new WordExtract(seg, sw);*/

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
      tagspage.add(0);
      if(keywords.contains("计算机")) tagspage.add(1);
      if(keywords.contains("Java")) tagspage.add(2);
      if(keywords.contains("网络")) tagspage.add(3);
      if(keywords.contains("研发")||keywords.contains("管理")) tagspage.add(4);
      if(keywords.contains("数学")) tagspage.add(5);
      if(keywords.contains("数据库")) tagspage.add(6);
      if(keywords.contains("软件工程")) tagspage.add(7);
      if(keywords.contains("前端")) tagspage.add(8);
      if(keywords.contains("机器")||keywords.contains("智能")) tagspage.add(9);
      if(keywords.contains("学习")) tagspage.add(-1);
      if(keywords.contains("看")) tagspage.add(-1);


      return tagspage;

    }



}










