package com.org.growth.EntityForTest;

import com.org.growth.entity.User;

public class UserTest {
    public static User user1;
    public static User user2;
    static {
        user1.setId(100L);
        user1.setUsername("USERTEST1");
        user1.setPassword("USERTEST1");
        user1.setTomatoLength(30);
        user1.setTomatoamount(10);
        user1.setTomatoweekly(2);
        user1.setEmail("TEST@TEST");
        user1.setUserface("TEST1");

        user2.setId(101L);
        user2.setUsername("USERTEST2");
        user2.setPassword("USERTEST2");
        user2.setTomatoLength(30);
        user2.setTomatoamount(10);
        user2.setTomatoweekly(2);
        user2.setEmail("TEST@TEST");
        user2.setUserface("TEST2");
    }
}
