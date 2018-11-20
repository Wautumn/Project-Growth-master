package com.org.growth.DAO;

import com.org.growth.entity.EXAMPLE;

public interface EXAMPLEDAO {
    void saveDemo(EXAMPLE demoEntity);

    void removeDemo(Long id);

    void updateDemo(EXAMPLE demoEntity);

    EXAMPLE findDemoById(Long id);

}
