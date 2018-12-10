package com.org.growth.Listener;

import com.org.growth.Event.NextWeekEvent;
import org.springframework.context.ApplicationListener;

public class NextWeekListener implements ApplicationListener<NextWeekEvent> {
    @Override
    public void onApplicationEvent(NextWeekEvent event) {
        event.printMsg(null);
    }
}
