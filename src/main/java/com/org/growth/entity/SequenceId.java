package com.org.growth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * @author rubick
 * 用于实现id自增
 */
@Document(collection = "sys_sequence")
public class SequenceId {
    @Id
    private String id;

    @Field("seq_id")
    private long seqId;

    @Field("coll_name")
    private String collName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeqId() {
        return seqId;
    }

    public void setSeqId(long seqId) {
        this.seqId = seqId;
    }

    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }

}
