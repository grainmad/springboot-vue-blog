package com.hncu.mapper;

import com.hncu.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章 Mapper 接口
 * </p>
 *
 */
@Repository
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    //创建一篇博文
    int create(Article article);

    //删除符合条件的博文
    int delete(Map<String,Object> paraMap);

    //更新博文
    int update(Map<String,Object> paraMap);

    //按照id降序查询所有博文
    List<Article> query(Map<String,Object> paramMap);

    //查询浏览次数最高的8篇
    List<Article> olderBy(Map<String,Object> paramMap);

    //id降序，5篇博文标题和图片和摘要
    List<Article> ArticlePosQuery(Map<String,Object> paramMap);

    //随机两篇博文
    List<Article> getRandomArticle();

    //id降序，4篇博文标题和图片
    List<Article> getNotice(Map<String,Object> paramMap);

    //博客详情
    Article detail(Map<String,Object> paramMap);
    //
    int count(Map<String,Object> paramMap);
}
