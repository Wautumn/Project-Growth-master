package com.org.growth.DAO;

import java.util.List;
import java.util.Map;

public interface RecommendDAO {
    /*
    获取用户关键词权值
     */
    Map<String,Integer> getUserKeyword(Long userId);

    /*
    关键词匹配
     */
    int[] getUserTags(Long userId);

    /*
    给出推荐文章
     */
    List<String> getAdvistoryArticle(Long userId);
}
