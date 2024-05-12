package com.hncu.controller;


import com.github.pagehelper.PageInfo;
import com.hncu.entity.FriendLink;
import com.hncu.service.impl.FriendLinkServiceImpl;
import com.hncu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/friendLink")
public class FriendLinkController {

    @Autowired
    private FriendLinkServiceImpl friendLinkService;

    @PostMapping("/create")
    public Result create(@RequestBody FriendLink friendLink){
        friendLinkService.create(friendLink);
        return Result.ok(friendLink);
    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        friendLinkService.delete(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody  FriendLink friendLink){

        friendLinkService.update(friendLink);
        return Result.ok(friendLink);
    }
    @PostMapping("/query")
    public Map query(@RequestBody FriendLink friendLink){
        PageInfo<FriendLink> pageInfo = friendLinkService.query(friendLink);
        System.out.println(pageInfo.getList());
        return Result.ok(pageInfo);
    }
    @GetMapping("/getListLink")
    public Result getListLink(){
        FriendLink friendLink = new FriendLink();
        List<FriendLink> friendLink1 = friendLinkService.getFriendLink(friendLink);
        return Result.ok(friendLink1);
    }


    @PostMapping("/detail")
    public Result detail(Integer id){
        FriendLink detail = friendLinkService.detail(id);
        return Result.ok(detail);
    }

    @PostMapping("/count")
    public Result count(@RequestBody FriendLink friendLink){
        int count = friendLinkService.count(friendLink);
        return Result.ok(count);
    }
}

