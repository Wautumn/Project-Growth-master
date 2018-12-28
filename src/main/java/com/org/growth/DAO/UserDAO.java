package com.org.growth.DAO;

import com.org.growth.entity.User;

import java.util.List;

public interface UserDAO {

    int getTomatoWeeklyCount(Long UserId);

    int changeTomatoLength(long UserId, int tomatoLength);
    int changeDayGoal(long UserId, int dayGoal);
    int changeWeekGoal(long UserId, int weekGoal);
    int changeMonthGoal(long UserId, int monthGoal);

    //boolean changeMusic(long UserId, String music);
    String changeUsername(long UserId, String username);
    boolean changePassword(long userId, String oldPassword, String newPassword);
    String changeEmail(long UserId, String email);
    String changeUserFace(long UserId, String userFace);

    List logIn(String username, String password);
    long signUp(String username, String password, String email);

}
