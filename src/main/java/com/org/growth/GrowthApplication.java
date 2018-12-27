package com.org.growth;

import com.org.growth.Event.NextWeekEvent;
import com.org.growth.Listener.NextWeekListener;
import com.org.growth.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Set;

@RestController
@SpringBootApplication
public class GrowthApplication {

    @RequestMapping("/")
    String home(){
        return "Hello Growth!";
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GrowthApplication.class);
        application.addListeners(new NextWeekListener());
        Set<ApplicationListener<?>> listeners = application.getListeners();
        ConfigurableApplicationContext context = application.run(args);
        Calendar calendar = Calendar.getInstance();
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        while (true){
            int weekNum = calendar.get(Calendar.WEEK_OF_YEAR);
            if(weekNum!=weekOfYear) {
                weekOfYear = weekNum;
                context.publishEvent(new NextWeekEvent(new Object()));
            }
        }

    }
}
