package com.hncu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hncu.entity.Comment;
import com.hncu.mapper.CommentMapper;
import com.hncu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    public int create(Comment comment){
        comment.setStatus(1);
        return commentMapper.create(comment);
    }
    @Transactional
    public int delete(Integer id){
        return commentMapper.delete(Maps.build(id).getMap());
    }
    @Transactional
    public int update(Comment comment){
        System.out.println(comment);

        System.out.println(Maps.build().beanToMap(comment));
        return commentMapper.update(Maps.build(comment.getId()).beanToMapForUpdate(comment));
    }

    public PageInfo<Comment> query(Comment comment){
        if (comment!=null && comment.getPage() != null){
            PageHelper.startPage(comment.getPage(),comment.getLimit());
        }
        List<Comment> list = commentMapper.query(Maps.build().beanToMap(comment));

        for (int i=0;i<list.size();i++){
            if (list.get(i).getStatus()==1){
                list.get(i).setStatusName("待审核");
            }else {
                list.get(i).setStatusName("审核通过");
            }
        }
        return new PageInfo<> (list);
    }

    public List<Comment> getCommentArticle(Comment comment){
        List<Comment> list = commentMapper.query(Maps.build().beanToMap(comment));
        return list;
    }



    public Comment detail(Integer id){
        return commentMapper.detail(Maps.build(id).getMap());
    }

    public int count(Comment comment){
        return commentMapper.count(Maps.build().beanToMap(comment));
    }
}
