package com.org.growth.DAO;

public interface TaskTreeDao {
    // -1 错误， 0插入重复， 1 成功
    int addSubTask(long parentId, String sonName);
}
