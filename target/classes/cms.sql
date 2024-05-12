CREATE DATABASE `cms` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `cms`;

create table tb_article
(
    id             int auto_increment
        primary key,
    channel_id     int           not null comment '栏目id',
    title          varchar(100)  null comment '标题',
    title_img      varchar(100)  null comment '标题图',
    summary        text          null comment '摘要',
    url            varchar(100)  null comment '外链URL',
    content        mediumtext    null comment '正文',
    status         int           null comment '0待发布1发布',
    comment_status int           null comment '0/Null允许评论，不允许评论',
    rotation       int           null comment '0/NULL非轮播 1 轮播',
    top            int           null comment '0/null不置顶，1置顶',
    orderby        int           null comment '排序',
    create_date    date          null comment '创建时间',
    create_user    int           null comment '创建人',
    update_date    date          null,
    article_view   int default 0 null
)
    comment '文章' collate = utf8mb4_general_ci;

create index id_articleId_id
    on tb_article (id);

create table tb_article_attachment
(
    id         int auto_increment comment 'ID'
        primary key,
    article_id int          null comment '文章ID',
    url        varchar(100) null comment '路径'
)
    comment '文章附件' collate = utf8mb4_general_ci;

create table tb_article_tag
(
    id         int auto_increment
        primary key,
    article_id int         null,
    tag_id     int         null,
    tag_name   varchar(50) null
)
    comment '文章标签' collate = utf8mb4_general_ci;

create table tb_channel
(
    id              int auto_increment comment 'ID'
        primary key,
    name            varchar(100) null comment '名称',
    parent_id       int          null comment '上级栏目',
    channel_img     varchar(255) null comment '栏目图片',
    summary         varchar(200) null comment '摘要',
    single          char         null comment '是否单页 .Y为单页，Null为不单页',
    url             varchar(100) null comment '外链URL',
    seo_title       varchar(100) null comment 'SEO标题',
    seo_keyword     varchar(200) null comment 'SEO关键字',
    seo_description varchar(200) null comment 'SEO描述',
    content         text         null comment '正文',
    create_user     int          null comment '创建人',
    create_date     date         null comment '创建时间',
    pos             char         null comment '位置'
)
    comment '栏目' collate = utf8mb4_general_ci;

create table tb_comment
(
    id          int auto_increment
        primary key,
    author      varchar(100) null comment '评论者',
    email       varchar(100) null comment '邮箱',
    create_date date         null comment '创建时间',
    content     text         null comment '正文',
    status      int          null comment '0 待批准，1已通过2未通过',
    article_id  int          null comment '文章ID'
)
    comment '评论' collate = utf8mb4_general_ci;

create table tb_friend_link
(
    id     int auto_increment comment 'ID'
        primary key,
    url    varchar(100) null comment '链接URL',
    title  varchar(100) null,
    target varchar(50)  null
)
    comment '友情链接' collate = utf8mb4_general_ci;

create table tb_tag
(
    id       int auto_increment
        primary key,
    tag_name varchar(50) null
)
    collate = utf8mb4_general_ci;

create table tb_user
(
    id          int auto_increment comment 'ID'
        primary key,
    user_name   varchar(100) null comment '用户名',
    password    varchar(100) null comment '密码',
    nick_name   varchar(100) null comment '名称',
    email       varchar(100) null comment '邮箱',
    avatar      varchar(100) null comment '头像',
    create_date date         null comment '创建时间',
    status      char         null comment 't代表正常，f代表禁用，d代表删除'
)
    collate = utf8mb4_general_ci;

INSERT INTO tb_user (user_name, password, nick_name, email, avatar, create_date, status) VALUES ('root', 'password', 'admin', '', '', '2024-05-11', 't');

INSERT INTO tb_channel (name, parent_id, channel_img, summary, single, url, seo_title, seo_keyword, seo_description, content, create_user, create_date, pos) VALUES ('通知栏', 0, '', '', 'N', '', '', '', '', '', 1, '2024-05-11', 'C');

