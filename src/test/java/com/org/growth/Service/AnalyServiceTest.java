package com.org.growth.Service;

import com.org.growth.entity.History;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyServiceTest {
    @Autowired
    AnalyzeDataService analyzeDataService=new AnalyzeDataService();

    @Test
    public void test(){
       // analyzeDataService.getWeekdayData();
       List<History> c =analyzeDataService.find(100);
        //if(analyzeDataService.find(100)==null)
          //  h=null;
       // SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(c.size());
      //  System.out.println(simpleDateFormat.format(h.getStartTime()).substring(0,10));

    }
}
