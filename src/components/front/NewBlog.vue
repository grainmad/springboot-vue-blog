<template>
    <div class="whitebg bloglist" >

        <h2 class="htitle" >最新博文</h2>
        <ul>
            <!-- 置顶 -->
            <li v-for="(item,index) in articleTops" :key="index.id">
                <h3 class="blogtitle"><router-link :to='"/detail/"+item.id'  arget="_blank"><b>【置顶】</b>{{item.title}}</router-link></h3>
                <span class="blogpic imgscale"><i><router-link :to='"/detail/"+item.id'>{{item.channel.name}}</router-link></i>
                    <router-link :to='"/detail/"+item.id' target="_blank">
                        <img v-if="item.titleImg==null ||item.titleImg=='' " width="130px" height="120px" src="../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg" alt="">
                        <img v-else  width="130px" height="130px"  :src="item.titleImg" alt="">
                    </router-link>
                </span>
                <p class="blogtext">{{item.summary}} </p>
                <p class="bloginfo">
                    <i class="avatar"><img :src="item.user.avatar"></i>
                    <!-- <span>{{item.user.nickName}}</span>
                    <span>{{item.createDate}}</span> -->
                    <span class="el-icon-user">&emsp;{{item.user.nickName}}</span>
                    <span class="el-icon-time">&emsp;{{item.createDate}}</span>
                    <span class="el-icon-view">&emsp;{{ item.articleView}}</span>
                    <el-tag v-for="it in item.selectTagNameList" type="success" :key="it">{{it}}</el-tag>
                    <!-- <el-tag type="success">标签二</el-tag> -->
                    
                </p>
                <!-- <router-link :to='"/detail/"+item.id' target="_blank"  class="viewmore"> 阅读更多</router-link > -->
            </li>


            <!-- 非置顶 -->
            <li v-for="(item,index) in articles" :key="index">
                <a href="#search" name="content"></a>
                <h3 class="blogtitle"><router-link :to='"/detail/"+item.id' target="_blank">{{item.title}}</router-link></h3>
                <span class="blogpic imgscale"><i><router-link :to='"/detail/"+item.id'>{{item.channel.name}}</router-link></i>
                    <router-link :to='"/detail/"+item.id' target="_blank">
                        <img v-if="item.titleImg==null ||item.titleImg=='' " width="130px" height="120px" src="../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg" alt="">
                        <img v-else width="130px" height="130px"  :src="item.titleImg" alt="">
                    </router-link>
                </span>
                <p class="blogtext">{{item.summary}} </p>
                <p class="bloginfo">
                    <i v-if="item.user.avatar" class="avatar"><img :src="item.user.avatar"></i>
                    <i v-else class="avatar"><img src="../../assets/images/avatar.jpeg"></i>
                    <!-- <span>{{item.user.nickName}}</span>
                    <span>{{item.createDate}}</span> -->
                    <span class="el-icon-user">{{item.user.nickName}}</span>&emsp;
                    <span class="el-icon-time">{{item.createDate}}</span>&emsp;
                    <span class="el-icon-view">{{ item.articleView}}</span>&emsp;
                    <el-tag v-for="it in item.selectTagNameList" type="success" :key="it">{{it}}</el-tag>
                </p>
                <!-- <router-link :to='"/detail/"+item.id' target="_blank" class="viewmore"> 阅读更多</router-link > -->
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
    import {getPageList,getNewBlogTop,search} from "@/api/front";
    import evenBus from  '@/utils/evenBus'
    export default {
        name: "NewBlog",
        props:['search','childevenTitle'],
        watch: {
            search: function(newVal,oldVal){
                this.articles=newVal.list
                this.total=newVal.total
                this.truth=true
            },
            childevenTitle: function(newVal,oldVal){
               this.queryForm.title=newVal
            },

        },
        data(){
            return{
                articles: [],
                articleTops: [],
                searchs:[],
                truth:false,
                page: '',
                pageNo: 1,
                pages: '',
                total: 0,
                enven:'',
                queryForm:{
                    name:'',
                    userName: ''
                },
            }
        },
        mounted(){
            this.getEvenData()
            this.list(this.pageNo)
            getNewBlogTop().then(data=>{
                this.articleTops =data.data
            })
        },
        methods:{
            handleCurrentChange(val){
                this.pageNo=val
                if (this.truth) {
                    this.getSearchPage(val)
                }else {
                    this.list(this.pageNo)
                }
            },
            list(param){
                getPageList(param).then(data=>{
                    this.articles=data.list
                    this.total=data.total
                }).catch(error=>{
                    this.$message.error(error)
                })
            },
            searchPage(param){
                search(param).then(data=>{
                    this.articles=data.list
                    this.total=data.total
                }).catch(error=>{
                    this.$message.error(error)
                })
            },
            getSearchPage(val){
                console.log('向父组件传值')
                let  param =this.queryForm
                this.pageNo =val
                param.page=this.pageNo
                this.searchPage(param)
                console.log('传参数的里面')
                console.log(this.articles)
            },
            getEvenData(){
                evenBus.$on('a',function (val) {
                    this.enven=val
                })
            }
        }
    }
</script>

<style scoped>




</style>