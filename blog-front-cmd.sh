docker run --name blog-front \
-v ./blog-front/dist:/usr/share/nginx/html:ro \
-v ./blog-front/nginx/nginx.conf:/etc/nginx/nginx.conf:ro \
-p 81:81 \
--network blog-net \
-d nginx