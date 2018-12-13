package com.org.growth.Service;

import com.org.growth.entity.AnalyzedataBean;
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
      try {
          List<AnalyzedataBean> a = analyzeDataService.getAllCompletedData(2);
          for (int i = 0; i < a.size(); i++) {
              System.out.println(a.get(i).getDate()+"time");
              System.out.println(a.get(i).getTomatocount()+"amount");
              System.out.println(a.get(i).getTaskCount()+"taskamount");

          }
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    @Test
    public void test2(){
        analyzeDataService.getWeekdayData(2);

    }

    @Test
    public void test3(){
        analyzeDataService.getTimeData(2);
    }
}
