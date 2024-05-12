package com.hncu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hncu.entity.FriendLink;
import com.hncu.mapper.FriendLinkMapper;
import com.hncu.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 友情链接 服务实现类
 * </p>
 *
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Transactional
    public int create(FriendLink friendLink){
        return friendLinkMapper.create(friendLink);
    }
    @Transactional
    public int delete(Integer id){
        return friendLinkMapper.delete(Maps.build(id).getMap());
    }
    @Transactional
    public int update(FriendLink friendLink){
        System.out.println(friendLink);

        System.out.println(Maps.build().beanToMap(friendLink));
        return friendLinkMapper.update(Maps.build(friendLink.getId()).beanToMapForUpdate(friendLink));
    }

    public PageInfo<FriendLink> query(FriendLink friendLink){
        if (friendLink!=null && friendLink.getPage() != null){
            PageHelper.startPage(friendLink.getPage(),friendLink.getLimit());
        }
        List<FriendLink> list = friendLinkMapper.query(Maps.build().beanToMap(friendLink));
        return new PageInfo<FriendLink> (list);
    }


    public List<FriendLink> getFriendLink(FriendLink friendLink){
        List<FriendLink> list = friendLinkMapper.query(Maps.build().beanToMap(friendLink));
        return  list;
    }


    public FriendLink detail(Integer id){
        return friendLinkMapper.detail(Maps.build(id).getMap());
    }

    public int count(FriendLink friendLink){
        return friendLinkMapper.count(Maps.build().beanToMap(friendLink));
    }

}
