package com.org.growth.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordTest {
    @Autowired
    KeywordRelated keywordRelated=new KeywordRelated();
    @Test
    public void test1()throws Exception{
        String s="甬温线特别重大铁路交通事故车辆经过近24小时的清理工作，26日深夜已经全部移出事故现场，之前埋下的D301次动车车头被挖出运走";
        List<String> l1= keywordRelated.GetKeywords(s,5);
        for(String s1:l1){
            System.out.println(s1);
        }

    }

}
