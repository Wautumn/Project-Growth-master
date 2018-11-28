package com.org.growth.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ListTree {

    @Field("parentListId")
    private long parentListId;

    @Field("sonListId")
    private long sonListId;


    public Long getSonlistid() {
        return sonListId;
    }

    public void setSonlistid(Long sonListId) {
        this.sonListId = sonListId;
    }

    public Long getParentlistid() {
        return parentListId;
    }

    public void setParentlistid(Long parentListId) {
        this.parentListId = parentListId;
    }
}
