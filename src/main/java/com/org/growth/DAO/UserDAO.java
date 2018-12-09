package com.org.growth.DAO;

import com.org.growth.entity.User;

public interface UserDAO {

    int getTomatoWeeklyCount(Long UserId);

    int changeTomatoLength(long UserId, int tomatoLength);
    //boolean changeMusic(long UserId, String music);

    String changeUsername(long UserId, String username);
    boolean changePassword(String username, String oldPassword, String newPassword);
    String changeEmail(long UserId, String email);
    String changeUserFace(long UserId, String userFace);

    long logIn(String username, String password);
    long signUp(String username, String password, String email, String userFace, int tomatoLength, String music);

}
