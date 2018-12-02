package com.org.growth.DAO;

public interface UserDAO {

    int getTomatoWeeklyCount(Long UserId);

    boolean changeTomatoLength(long UserId, int tomatoLength);
    boolean changeMusic(long UserId, String music);

    boolean changeUsername(long UserId, String username);
    boolean changePassword(long UserId, String password);
    boolean changeEmail(long UserId, String email);
    boolean changeUserFace(long UserId, String userFace);

    boolean logIn(long UserId, String password);
    long signUp(String username, String password, String email, String userFace, int tomatoLength, String music);

}
