package com.org.growth.Service;

import com.org.growth.DAO.EXAMPLEDAO;
import com.org.growth.GrowthApplication;
import com.org.growth.entity.EXAMPLE;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.tree.ExpandVetoException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class EXAMPLEDAOIMPTest {

    @Autowired
    private EXAMPLEDAO demoDao;
    @Test
    public void saveDemo() {
        EXAMPLE demoEntity = new EXAMPLE();
        //demoEntity.setId(1L);
        demoEntity.setAge(1);
        demoEntity.setName("rua");
        demoDao.saveDemo(demoEntity);

        //demoEntity.setId(2L);
        demoEntity.setAge(2);
        demoEntity.setName("raa");
        demoDao.saveDemo(demoEntity);
    }

    @Test
    public void removeDemo() {
        demoDao.removeDemo(2L);
    }

    @Test
    public void updateDemo() {
        EXAMPLE demoEntity = new EXAMPLE();
        demoEntity.setId(2L);
        demoEntity.setAge(2);
        demoEntity.setName("raa");
        demoDao.updateDemo(demoEntity);
    }

    @Test
    public void findDemoById() {
        EXAMPLE demoEntity = demoDao.findDemoById(2L);
        System.out.println(demoEntity);
    }

}