# docker compose 部署博客项目

* blog-mysql 博客数据库
    * 挂载数据卷 config/ mysql配置文件
    * 挂载数据卷 data/ mysql数据保存位置
    * 挂载数据卷 initdb/ mysql数据库初始化所执行的脚本（data/必须为空才会执行）
* blog-back 博客后端
    * 挂载数据卷 upload/ 保存上传文件
    * 挂载数据卷 application.yml 后端配置文件
    * Dockerfile 将jar包构建镜像
* blog-front 博客前端
    * 挂载数据卷 nginx/ nginx配置文件
    * 挂载数据卷 dist/ 前端部署代码


[前端代码分支](https://github.com/grainmad/springboot-vue-blog/tree/blog-front)
``` shell
git clone --branch blog-front https://github.com/grainmad/springboot-vue-blog.git blog-front
```

[后端代码分支](https://github.com/grainmad/springboot-vue-blog/tree/blog-backend)
``` shell
git clone --branch blog-backend https://github.com/grainmad/springboot-vue-blog.git blog-backend
```