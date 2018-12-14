package com.org.growth.utils;

import com.org.growth.Other.AutoIncrement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class getArticleTest {
    @Autowired
    getArticleByWeb getArticleByWebt=new getArticleByWeb();

    @Test
    public void testArticle(){
        //for(int i=0;i<21;++i) {
            getArticleByWebt.get(11);
      //  }
    }
}
