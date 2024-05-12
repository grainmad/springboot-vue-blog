<template>
    <div class="whitebg bloglist">
        <h2 class="htitle">标签查询结果</h2>
        <ul>

            <!--单图-->
            <li v-for="(item,index) in articles" :key="index.id">
                <h3 class="blogtitle"><router-link :to='"/detail/"+item.article.id' target="_blank">{{item.article.title}}</router-link></h3>
                <span class="blogpic imgscale"><i><router-link :to='"/detail/"+item.id'>{{item.article.channel.name}}</router-link></i>
                    <router-link :to='"/detail/"+item.articleId'>
                        <img v-if="item.article.titleImg==null ||item.article.titleImg=='' " width="130px" height="120px" src="@/assets/images/flower.jpg" alt="">
                        <img v-else  :src="item.article.titleImg" alt="">
                    </router-link>
                </span>
                <p class="blogtext">{{item.article.summary}} </p>
                <p class="bloginfo">
                    <i v-if="item.article.user.avatar" class="avatar"><img :src="item.article.user.avatar"></i>
                    <i v-else class="avatar"><img src="../../assets/images/avatar.jpeg"></i>
                    <span class="el-icon-user">&emsp;{{item.article.user.nickName}}</span>
                    <span class="el-icon-time">&emsp;{{item.article.createDate}}</span>
                    <span class="el-icon-view">&emsp;{{ item.article.articleView}}</span>
                    <el-tag v-for="it in item.article.selectTagNameList" type="success" :key="it">{{it}}</el-tag>
                </p>
                <!-- <router-link :to='"/detail/"+item.id'  class="viewmore"> 阅读更多</router-link > -->
            </li>
        </ul>

        <div>
            <el-pagination
                    background
                    layout="total,prev, pager, next,jumper"
                    @current-change="handleCurrentChange"
                    :current-page="pageNo"
                    :page-size="8"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>

    import {getArticleByTag} from "@/api/articleTag";
    export default {

        name: "TagBlog",
        data(){
            return{
                articles: [],
                articleTag:{
                    articleId: '',
                    tagId: ''
                },
                page: '',
                pageNo: 1,
                pages: '',
                total: 0,
            }
        },
        mounted(){
            this.articleTag.tagId=this.$route.params.id

            let  param =this.articleTag
            this.pageNo=1
            param.page=this.pageNo
            this.list(param)

        },
        methods:{
            handleCurrentChange(val){
                let  param =this.articleTag
                this.pageNo=val
                param.page=this.pageNo
                this.list(param)
            },
            list(param){
                getArticleByTag(param).then(data=>{

                    // console.log("article "+param)
                    // console.log(data)
                    this.articles=data.list
                    this.total=data.total
                }).catch(error=>{
                    this.$message.error(error)
                })
            },
        },
    }
</script>

<style scoped>




</style>