package com.hncu.controller;


import com.github.pagehelper.PageInfo;
import com.hncu.entity.Channel;
import com.hncu.entity.User;
import com.hncu.service.impl.ChannelServiceImpl;
import com.hncu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelServiceImpl channelServiceImpl;

    @PostMapping("/create")
    public Result create(@RequestBody Channel channel,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        channel.setCreateUser(user.getId());
        if (channel.getParentId()==null){
            channel.setParentId(0);
        }
        channelServiceImpl.create(channel);
        return Result.ok(channel);
    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        channelServiceImpl.delete(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody  Channel channel){
        channelServiceImpl.update(channel);
        return Result.ok(channel);
    }
    @PostMapping("/query")
    public Map query(@RequestBody Channel channel){
        PageInfo<Channel> pageInfo = channelServiceImpl.query(channel);
        return Result.ok(pageInfo);
    }

    @PostMapping("/tree")
    public Result tree(String pos){
//        System.out.println("tree"+pos);
        List<Channel> list = channelServiceImpl.getChannelPos(pos);
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (Channel channel : list) {
            //首先获取顶级栏目
            if (channel.getParentId()==0){
                Map<String,Object> map =new HashMap<>();
                map.put("id",channel.getId());
                map.put("label",channel.getName());
                List<Map<String,Object>> children=new ArrayList<>();
                for (Channel entity : list) {
                    if (entity.getParentId() == channel.getId() ){
                        Map<String,Object> subMap=new HashMap<>();
                        subMap.put("id",entity.getId());
                        subMap.put("label",entity.getName());
                        children.add(subMap);
                    }
                }
                if (!children.isEmpty()) map.put("children",children);
                mapList.add(map);
            }
        }
        return Result.ok(mapList);
    }

    @PostMapping("/detail")
    public Result detail(Integer id){
        Channel detail = channelServiceImpl.detail(id);
        return Result.ok(detail);
    }
    @PostMapping("/count")
    public Result count(@RequestBody Channel channel){
        int count = channelServiceImpl.count(channel);
        return Result.ok(count);
    }
}

