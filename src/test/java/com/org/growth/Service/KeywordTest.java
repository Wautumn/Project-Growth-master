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
        String s="学习计算机网络，做实验";
        List<String> l1= keywordRelated.GetKeywords(s,3);
        for(String s1:l1){
            System.out.println(s1);
        }

    }


}
