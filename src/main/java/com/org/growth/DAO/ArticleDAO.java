package com.org.growth.DAO;

import com.org.growth.entity.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ArticleDAO {
    /*
    获取用户关键词权值
     */
    List<Article> getArticleById(Long UserId);

    /*
    关键词匹配
     */
   // int[] getUserTags(Long userId);

    /*
    给出推荐文章
     */
   // List<String> getAdvistoryArticle(Long userId);
}
