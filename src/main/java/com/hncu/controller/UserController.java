package com.hncu.controller;


import com.github.pagehelper.PageInfo;
import com.hncu.entity.User;
import com.hncu.service.impl.UserServiceImpl;
import com.hncu.utils.RequestUtils;
import com.hncu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    public Result  create(@RequestBody User user){
        userServiceImpl.create(user);
        return Result.ok(user);
    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        userServiceImpl.delete(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody  User user){
        userServiceImpl.update(user);
        return Result.ok(user);
    }
    @PostMapping("/query")
    public Map query(@RequestBody User user){
        PageInfo<User> pageInfo = userServiceImpl.query(user);
        System.out.println(pageInfo.getList());
        return Result.ok(pageInfo);
    }

    @PostMapping("/detail")
    public Result detail(Integer id){
        User detail = userServiceImpl.detail(id);
        return Result.ok(detail);
    }

    @PostMapping("/count")
    public Result count(@RequestBody User user){
        int count = userServiceImpl.count(user);
        return Result.ok(count);
    }

    @Value("${uploadfilepath}")
    String filePath;

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //获取文件名后缀
        String ex = originalFilename.substring(originalFilename.lastIndexOf(".")+1,originalFilename.length());
        String newFileNamePrefix= UUID.randomUUID().toString();
        String newFileName=newFileNamePrefix+"."+ex;
        System.out.println(filePath);
        file.transferTo(new File(filePath,newFileName));

        System.out.println(RequestUtils.getBasePath(request)+"upload/"+newFileName);
        //最后返回的是一个可以访问的全路径
        return Result.ok(RequestUtils.getBasePath(request)+"upload/"+newFileName);
    }

}

