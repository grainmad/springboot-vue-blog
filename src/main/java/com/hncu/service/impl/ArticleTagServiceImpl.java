package com.hncu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hncu.entity.Article;
import com.hncu.entity.ArticleTag;
import com.hncu.entity.Channel;
import com.hncu.entity.User;
import com.hncu.mapper.ArticleMapper;
import com.hncu.mapper.ArticleTagMapper;
import com.hncu.mapper.ChannelMapper;
import com.hncu.mapper.UserMapper;
import com.hncu.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.utils.Maps;
import com.hncu.utils.UserNullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章标签 服务实现类
 * </p>
 *
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Transactional
    public int create(ArticleTag articleTag){
        return articleTagMapper.create(articleTag);
    }
    @Transactional
    public int delete(Integer id){
        return articleTagMapper.delete(Maps.build(id).getMap());
    }
    @Transactional
    public int update(ArticleTag articleTag){
        return articleTagMapper.update(Maps.build(articleTag.getId()).beanToMapForUpdate(articleTag));
    }

    public PageInfo<ArticleTag> query(ArticleTag articleTag){
        if (articleTag!=null && articleTag.getPage() != null){
            PageHelper.startPage(articleTag.getPage(),articleTag.getLimit());
        }
        List<ArticleTag> list = articleTagMapper.query(Maps.build().beanToMap(articleTag));
        return new PageInfo<> (list);
    }

    //通过标签找分页查询文章
    public PageInfo<ArticleTag> getArticleId(ArticleTag articleTag){
        if (articleTag!=null && articleTag.getPage() != null){
            PageHelper.startPage(articleTag.getPage(),8);
        }
        List<ArticleTag> list = articleTagMapper.query(Maps.build(articleTag.getTagId()).beanToMap(articleTag));

        for (int i=0 ; i<list.size();i++){
            Article article = articleMapper.detail(Maps.build(list.get(i).getArticleId()).getMap());

            List<ArticleTag> articleTagList = articleTagMapper.query(Maps.build().put("articleId", article.getId()).getMap());
            List<String> tagsName = new ArrayList<String>();
            articleTagList.forEach(x->{
                tagsName.add(x.getTagName());
            });
            article.setSelectTagNameList(tagsName);

            User user = userMapper.detail(Maps.build(article.getCreateUser()).getMap());
            if (user!=null){
                article.setUser(user);
            }else {
                article.setUser(UserNullUtils.userIsNull());
            }

            Channel channel = channelMapper.detail(Maps.build(article.getChannelId()).getMap());
            article.setChannel(channel);



            list.get(i).setArticle(article);
        }
        return new PageInfo<> (list);
    }

    public ArticleTag detail(Integer id){
        return articleTagMapper.detail(Maps.build(id).getMap());
    }

    public int count(ArticleTag articleTag){
        return articleTagMapper.count(Maps.build().beanToMap(articleTag));
    }
}


