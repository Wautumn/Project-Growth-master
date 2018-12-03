package com.org.growth.entity;

import com.org.growth.Other.AutoIncrement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tasktree")
public class TaskTree {

    @Id
    @AutoIncrement
    private long sonTaskId;

    @Field("parenttaskid")
    private long parentTaskId;

    @Field("sontaskname")
    private String sonTaskName;

    @Field("istaskdone")
    private boolean isTaskDone;

    public long getParentTaskId() {
        return parentTaskId;
    }


    public String  getSonTaskName() {
        return sonTaskName;
    }

    public void setParentTaskId(long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public void setSonTaskName(String  sonTaskName) {
        this.sonTaskName = sonTaskName;
    }


    public void setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
    }
}
