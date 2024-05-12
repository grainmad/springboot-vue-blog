package com.hncu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hncu.entity.*;
import com.hncu.entity.Article;
import com.hncu.mapper.*;
import com.hncu.mapper.ArticleMapper;
import com.hncu.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.utils.Maps;
import com.hncu.utils.UserNullUtils;
import com.hncu.vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleAttachmentMapper articleAttachmentMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;
    //创建博客
    @Transactional
    public int create(Article article){
        //博客表插入
        article.setArticleView(0);
        int row = articleMapper.create(article);
        //附件表插入
        article.getArticleAttachments().forEach(entity->{
            ArticleAttachment articleAttachment = new ArticleAttachment();
            articleAttachment.setArticleId(article.getId());
            articleAttachment.setDescription(entity.get("name")+"");
            articleAttachment.setUrl(entity.get("url")+"");
            articleAttachmentMapper.create(articleAttachment);
        });
        //标签表插入
        article.getSelectTagList().forEach(tag->{
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag);
            articleTag.setTagName(tagMapper.detail(Maps.build(articleTag.getTagId()).getMap()).getTagName());
            articleTagMapper.create(articleTag);
        });
        return row;
    }
    //删除指定id的博客
    @Transactional
    public int delete(Integer id){
        //删除博客
        int delete = articleMapper.delete(Maps.build(id).getMap());
        //删除对应的附件
        articleAttachmentMapper.delete(Maps.build().put("articleId",id).getMap());
        //删除博客表与标签表的中间表的记录
        articleTagMapper.delete(Maps.build().put("articleId",id).getMap());
        //删除对应评论
        commentMapper.delete(Maps.build().put("articleId",id).getMap());
        return delete;
    }

    //更新博客
    @Transactional
    public int update(Article article){
        //更新文章记录
        int update = articleMapper.update(Maps.build(article.getId()).beanToMapForUpdate(article));
        //删除附件
        articleAttachmentMapper.delete(Maps.build().put("articleId",article.getId()).getMap());
        //删除标签
        articleTagMapper.delete(Maps.build().put("articleId",article.getId()).getMap());
        //插入新的附件
        article.getArticleAttachments().forEach(entity->{
            ArticleAttachment articleAttachment = new ArticleAttachment();
            articleAttachment.setArticleId(article.getId());
            articleAttachment.setDescription(entity.get("name")+"");
            articleAttachment.setUrl(entity.get("url")+"");
            articleAttachmentMapper.create(articleAttachment);
        });
        //插入新的标签
        article.getSelectTagList().forEach(tag->{
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag);
            articleTag.setTagName(tagMapper.detail(Maps.build(articleTag.getTagId()).getMap()).getTagName());
            articleTagMapper.create(articleTag);
        });
        return update;
    }
    //分页查询
    public PageInfo<Article> query(Article article){
        if (article!=null && article.getPage() != null){
            PageHelper.startPage(article.getPage(),article.getLimit());
        }

        List<Article> list = articleMapper.query(Maps.build().beanToMap(article));
        for (Article article1 : list) {
            User user = userMapper.detail(Maps.build(article1.getCreateUser()).getMap());
            Channel channel = channelMapper.detail(Maps.build(article1.getChannelId()).getMap());
            article1.setChannel(channel);
            if (user!=null){
                article1.setUser(user);
            }else {
                article1.setUser(UserNullUtils.userIsNull());
            }
        }

        return new PageInfo<> (list);
    }

    //按浏览量查询
    public List<Article> orderBy(){
        return  articleMapper.olderBy(Maps.build().beanToMap(null));
    }

    //获取分栏为id的所有博文
    public List<Article> getChannelPos(Integer id){
        Article article = new Article();
        article.setChannelId(id);
        List<Article> list = articleMapper.ArticlePosQuery(Maps.build().put("channelId",id).getMap());
        for (Article article1 : list) {
            User user = userMapper.detail(Maps.build(article1.getCreateUser()).getMap());
            if (user!=null){
                article1.setUser(user);
            }else {
                article1.setUser(UserNullUtils.userIsNull());
            }
        }
        return list;
    }
    //设置页数查询，每页8条
    public PageInfo<Article> getPage(Article article,Integer number){
        if (number!=null){
            PageHelper.startPage(number,8);
        }
         List<Article> list = articleMapper.query(Maps.build().beanToMap(article));
        for (Article article1 : list) {
            List<ArticleTag> articleTagList = articleTagMapper.query(Maps.build().put("articleId", article1.getId()).getMap());
            List<String> tagsName = new ArrayList<String>();
            articleTagList.forEach(x->{
                tagsName.add(x.getTagName());
            });
            article1.setSelectTagNameList(tagsName);
            Channel channel = channelMapper.detail(Maps.build(article1.getChannelId()).getMap());
            article1.setChannel(channel);
            User user = userMapper.detail(Maps.build(article1.getCreateUser()).getMap());
            if (user!=null){
                article1.setUser(user);
            }else {
                article1.setUser(UserNullUtils.userIsNull());
            }
        }
        return new PageInfo<> (list);
    }
    //随机获取2条博文
    public List<Article> getRandomArticle(){
        return articleMapper.getRandomArticle();
    }

    //获取前top条
    public List<Article> top(Article article,Integer top){
        PageHelper.startPage(0,top);
        List<Article> list = articleMapper.query(Maps.build().beanToMap(article));
        return list;
    }
    //获取置顶的文章
    public List<Article> top(){
        Article article = new Article();
        article.setTop(1);
        List<Article> list = articleMapper.query(Maps.build().beanToMap(article));
        for (Article article1 : list) {

            List<ArticleTag> articleTagList = articleTagMapper.query(Maps.build().put("articleId", article1.getId()).getMap());
            List<String> tagsName = new ArrayList<String>();
            articleTagList.forEach(x->{
                tagsName.add(x.getTagName());
            });
            article1.setSelectTagNameList(tagsName);

            Channel channel = channelMapper.detail(Maps.build(article1.getChannelId()).getMap());
            article1.setChannel(channel);

            User user = userMapper.detail(Maps.build(article1.getCreateUser()).getMap());
            if (user!=null) {
                article1.setUser(user);
            }else {
                article1.setUser(UserNullUtils.userIsNull());
            }
        }
        return list;
    }

    //博文详情
    public Article detail(Article a){
        Integer id=a.getId();
        Article article = articleMapper.detail(Maps.build(id).getMap());
        //查找发布人信息
        if (article!=null){
            User user = userMapper.detail(Maps.build(article.getCreateUser()).getMap());
            if (user!=null){
                article.setUser(user);
            }else {
                article.setUser(UserNullUtils.userIsNull());
            }
        }
        //查询标签信息
        List<ArticleTag> articleTagList = articleTagMapper.query(Maps.build().put("articleId", id).getMap());
//        System.out.println("aticleTagList:"+articleTagList);
//        List<ArticleAttachment> articleAttachments = articleAttachmentMapper.query(Maps.build().put("articleId", id).getMap());
        List<Integer> tags =new ArrayList<>();
        //查询栏目信息
        List<Map<String,Object>> articleAttachmentList =new ArrayList<>();
        articleTagList.forEach(entity->{
            tags.add(entity.getTagId());
        });
//        articleAttachments.forEach(entity->{
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("name",entity.getDescription());
//            map.put("url",entity.getUrl());
//            articleAttachmentList.add(map);
//        });

//        System.out.println("tag "+tags);
        article.setSelectTagList(tags);
            article.setArticleAttachments(articleAttachmentList);


        //增加视图
        if (a.getFront()){
            Article viewArticle = new Article();
            viewArticle.setArticleView(article.getArticleView()+1);
            article.setArticleView(viewArticle.getArticleView());
            articleMapper.update(Maps.build(id).beanToMapForUpdate(viewArticle));
        }
        //猜你喜欢 查询6条标签
        if(articleTagList.size()!=0){
            Integer tagId = articleTagList.get(0).getTagId();
            //tag
            List<ArticleTag> listTag = articleTagMapper.getLike(Maps.build().put("tagId", tagId).getMap());
            List<ArticleVo> articleVos = new ArrayList<>();
            for (ArticleTag articleTag : listTag) {
                Article article1 = new Article();
                article1.setId(articleTag.getArticleId());
                Article detail = articleMapper.detail(Maps.build(articleTag.getArticleId()).getMap());
                ArticleVo articleVo = new ArticleVo();
                if (detail!=null) {
                    BeanUtils.copyProperties(detail,articleVo);
                    //随机查出两条数据
                    articleVos.add(articleVo);
                }
            }
            article.setArticleVo(articleVos);
        }
        return article;
    }


    public List<Article> getNotice(Article article){
        return articleMapper.getNotice(Maps.build().beanToMap(article));
    }



    public int count(Article article){
        return articleMapper.count(Maps.build().beanToMap(article));
    }

    public List<Article> getRotationArticle() {
        return articleMapper.query(Maps.build().put("rotation", 1).getMap());
    }
}
