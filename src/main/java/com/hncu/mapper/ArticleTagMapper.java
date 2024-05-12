package com.hncu.mapper;

import com.hncu.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    int create(ArticleTag articleTag);

    int delete(Map<String,Object> paraMap);

    int update(Map<String,Object> paraMap);

    List<ArticleTag> query(Map<String,Object> paramMap);

    List<ArticleTag> getLike(Map<String,Object> paramMap);

    ArticleTag detail(Map<String,Object> paramMap);

    int count(Map<String,Object> paramMap);
}
