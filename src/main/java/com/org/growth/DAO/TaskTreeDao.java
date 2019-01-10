package com.org.growth.DAO;

public interface TaskTreeDao {

    //false:-1   repetition:0   success:1
    int addSubTask(long parentId, String sonName);
}
