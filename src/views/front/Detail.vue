<template>
    <div v-title data-title="文章">
        <Header></Header>
        <article>
            <div class="lbox">
                <div class="content_box whitebg">
                    <h2 class="htitle"><span class="con_nav">您现在的位置是：<a href="/">首页</a>&gt;<a href="/">{{articles.title}}</a></span>正文</h2>
                    <h1 class="con_tilte">{{articles.title}}</h1>
                    <p class="bloginfo">
                        <i class="avatar">
                            <img v-if="user.avatar" :src="user.avatar">
                            <img v-else src="../../assets/images/avatar.jpeg">
                        </i>
                        <span class="el-icon-user">&emsp;{{user.nickName}}</span>
                        <span class="el-icon-time">&emsp;{{articles.createDate}}</span>
                        <span class="el-icon-view">&emsp;{{articles.articleView}}人已观看</span>
                        <el-tag v-for="it in articles.selectTagNameList" type="success" :key="it">{{it}}</el-tag>
                    </p>
                    <p class="con_info"><b>摘要</b>{{articles.summary}}</p>
                    <div class="con_text">
                        <v-md-preview :text="articles.content"></v-md-preview>
                    </div>

                </div>

                <div id="comments" class="comments-area" v-if="articles.commentStatus==0 || articles.commentStatus==null">
                    <ol class="comment-list">
                        <li class="comments-anchor">
                            <!--<ul id="anchor-comment-40">-->
                                <!---->
                            <!--</ul>-->
                            <div id="respond" class="comment-respond">
                                <h3 id="reply-title" class="comment-reply-title"><span id="reply-title-word">发表评论</span></h3>
                                <Form></Form>
                            </div>
                        </li>

                        <li class="comment" v-for=" (item,index) in comments" :key="index">
                            <div id="div-comment-2044" class="comment-body">
                                <div class="comment-author vcard">
                                    <img class="avatarss" src="../../assets/images/avatar.png" alt="avatar">
                                        <!-- <el-avatar size="large" src="../../assets/images/avatar.png"></el-avatar> -->
                                    <div style="margin-left: 90px">
                                        <span class="el-icon-user" >
                                            &emsp;<strong>{{item.author}}</strong>
                                        </span>
                                        <span style="margin-left:20px" class="el-icon-time">
                                            &emsp;{{item.createDate}}
                                        </span>
                                        <p>{{item.content}}</p>
                                    </div>
                                    
                                </div>
                            </div>
                            <div id="anchor-2044"></div>
                            <ul class="children">
                            </ul>
                        </li>
                    </ol>
                </div>
            </div>

            <div class="rbox">
                <OrderByView></OrderByView>
                <Like></Like>
                <Tag></Tag>
                <FriendLink></FriendLink>
            </div>
        </article>
        <Footer></Footer>
    </div>
</template>

<script>
    import  '@/assets/css/base.css'
    import  '@/assets/css/m.css'
    import Header from "../../components/front/Header";
    import OrderByView from "../../components/front/OrderByView";
    import FriendLink from "../../components/front/FriendLink";
    import Footer from "../../components/front/Footer";
    import Like from  '../../components/front/Like'
    import Tag from "../../components/front/Tag";

    import {getArticle,getCommentArticle} from "../../api/front";
    import Form from "../../components/front/Form";

    export default {
        name: "Detail",
        components: {
            Form,
            Tag, Footer, FriendLink, OrderByView , Header, Like
        },
        data(){
            return{
                articles: {},
                user: [],
                comments:[],
                xx: true
            }
        },
        created(){

            console.log(this.$route.params.id)
            getArticle(this.$route.params.id).then(data=>{
                console.log(data.data)
                if(data){
                    this.articles =data.data
                    this.user=data.data.user
                }
            }).catch(error=>{
                this.$message.error(error)
            })
            getCommentArticle(this.$route.params.id).then(data=>{
                this.comments=data.data
            })
        },
        beforeRouteUpdate(to,from ,next){
            getArticle(to.params.id).then(data=>{
                if(data){
                    this.articles =data.data
                    this.user=data.data.user
                }
            })
            this.xx = false;
            this.$nextTick(() => {
                this.xx = true
            })
            next()
        },
        methods:{

        }
    }
</script>

<style scoped>
    .comment-list li {
        position: relative;
        background: #fff;
        margin: 0 0 10px 0;
        padding: 15px 20px 30px 20px;
        border: 1px solid #ddd;
        box-shadow: 0 1px 1px rgba(0, 0, 0, 0.04);
        border-radius: 2px;
        word-wrap: break-word;
        word-break: break-all;
    }


    .reply a {
        background: #fff;
        line-height: 25px;
        display: block;
        padding: 0 10px;
        border: 1px solid #ddd;
        border-radius: 2px;
    }

    .avatarss{
        position: relative;
        float: left;
        width: 60px;
        height: 60px;
        border-radius: 15px;
        padding: 5px;
        display: block;
        background: -webkit-linear-gradient(bottom left, rgba(0,153,255,.9),
        rgba(42,228,197,.7)); background: -o-linear-gradient(bottom left, rgba(0,153,255,.9), rgba(42,228,197,.7)); background: linear-gradient(to top right, rgba(0,153,255,.9), rgba(42,228,197,.7));
    }

    #respond {
        background: #fff;
        margin: 0 0 10px 0;
        padding: 10px 20px;
        border: 1px solid #ddd;
        box-shadow: 0 1px 1px rgba(0, 0, 0, 0.04);
        border-radius: 2px;
    }


</style>