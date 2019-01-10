package com.org.growth.DAO;

import com.org.growth.entity.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ArticleDAO {
    /*
    get weight of user keywords
     */
    List<Article> getArticleById(Long UserId);

    /*
    keywords matching
     */
   // int[] getUserTags(Long userId);

    /*
    provide recommended articles
     */
   // List<String> getAdvistoryArticle(Long userId);
}
