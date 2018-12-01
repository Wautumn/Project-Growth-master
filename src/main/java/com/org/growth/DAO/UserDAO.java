package com.org.growth.DAO;

public interface UserDAO {

    int getTomatoWeeklyCount(Long UserId);
    boolean changeTomatoLength(Long UserId, int tomatoLength);
    boolean changeMusic(Long UserId, String music);
}
