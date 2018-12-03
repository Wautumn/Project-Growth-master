package com.org.growth.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RecommendDAO {
    /*
    获取用户关键词权值
     */
    ArrayList<String> getUserKeyword(Long userId) throws Exception;

    /*
    关键词匹配
     */
   // int[] getUserTags(Long userId);

    /*
    给出推荐文章
     */
   // List<String> getAdvistoryArticle(Long userId);
}
