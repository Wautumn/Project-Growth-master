package com.org.growth.EntityForTest;

import com.org.growth.entity.TaskTree;

public class TaskTreeTest {
    public static TaskTree taskTree1;
    public static TaskTree taskTree2;
    static {
        taskTree1.setParentTaskId(100L);
        taskTree1.setTaskDone(true);
        taskTree1.setSonTaskName("TEST");

        taskTree2.setParentTaskId(100L);
        taskTree2.setTaskDone(false);
        taskTree2.setSonTaskName("TEST1");
    }
}
