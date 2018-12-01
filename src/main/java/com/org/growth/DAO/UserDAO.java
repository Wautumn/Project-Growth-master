package com.org.growth.DAO;

public interface UserDAO {

    int getTomatoWeeklyCount(Long UserId);

    boolean changeTomatoLength(Long UserId, int tomatoLength);
    boolean changeMusic(Long UserId, String music);

    boolean changeUsername(Long UserId, String username);
    boolean changePassword(Long UserId, String password);
    boolean changeEmail(Long UserId, String email);
    boolean changeUserFace(Long UserId, String userFace);

}
