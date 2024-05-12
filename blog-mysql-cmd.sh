docker run --name blog-mysql \
-v ./blog-mysql/data:/var/lib/mysql \
-v ./blog-mysql/config:/etc/mysql/conf.d \
-v ./blog-mysql/initdb:/docker-entrypoint-initdb.d \
-e TZ=Asia/Shanghai \
-e MYSQL_ROOT_PASSWORD=123456 \
-p 3306:3306 \
--network blog-net \
-d mysql

# 数据存储位置有数据，则不会触发初始化执行脚本