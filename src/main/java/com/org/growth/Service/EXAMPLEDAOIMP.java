package com.org.growth.Service;

import com.org.growth.DAO.EXAMPLEDAO;
import com.org.growth.entity.EXAMPLE;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EXAMPLEDAOIMP implements EXAMPLEDAO {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveDemo(EXAMPLE demoEntity) {
        mongoTemplate.save(demoEntity);
    }

    @Override
    public void removeDemo(Long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,EXAMPLE.class);
    }

    @Override
    public void updateDemo(EXAMPLE demoEntity) {
        Query query = new Query();
        Update update = new Update();
        update.set("name","TEST");
        mongoTemplate.updateMulti(query, update, EXAMPLE.class);
    }

    @Override
    public EXAMPLE findDemoById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        EXAMPLE demoEntity = mongoTemplate.findOne(query, EXAMPLE.class);
        return demoEntity;
    }
}
