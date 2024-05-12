docker build -t gg/blog:1.0 ./blog-back

docker run --name blog-backend \
-v ./blog-back/upload:/blog/upload \
-v ./blog-back/application.yml:/blog/application.yml \
-p 8081:8081 \
--network blog-net \
-d gg/blog:1.0