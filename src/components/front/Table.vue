<template>
    <div class="tab_box whitebg">

        <template>
        <div class="tab_buttons">
            <ul>
                <li v-for="(item,index) in channels" :key="index"  @click="curlist(item.id)" :class="{newscurrent: cur==item.id}">{{item.name}}</li>
            </ul>
        </div>
        <div class="newstab">
            <div class="newsitem" :v-show="cur==cur">
                <div class="newspic">
                    <ul>
                        <li v-if="xxxx == null" ><img src="../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg"><span >没有了</span></li>
                        <li v-else><router-link :to='"/detail/"+xxxx.id' target="_blank"><img :src="xxxx.titleImg"><span >{{xxxx.title}}</span></router-link></li>
                        <li v-if="yyyy == null" ><img src="../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg"><span >没有了</span></li>
                        <li v-else><router-link :to='"/detail/"+yyyy.id' target="_blank" ><img :src="yyyy.titleImg"><span>{{yyyy.title}}</span></router-link></li>
                    </ul>
                </div>
                <ul class="newslist">
                    <li v-for="(item,index) in articles" :key="index">
                    <i></i>
                    <router-link :to='"/detail/"+item.id' target="_blank">{{item.title}} </router-link>
                    <p>{{item.summary}}</p> 
                        
                    </li>
                </ul>
            </div>
        </div>
        </template>
    </div>
</template>

<script>

    import {queryByPos,getChannelArticlePos} from '@/api/front'

    export default {
        name: "Table",
        data(){
            return{
                articles:[],
                channels : [],
                xxxx: null,
                yyyy: null,
                // xxxx: {
                //     id: '',
                //     title:'没有了',
                //     titleImg:'../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg'
                // },
                // yyyy: {
                //     id: '',
                //     title:'没有了',
                //     titleImg:'../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg'
                // },
                cur:''
            }
        },
        mounted() {
            queryByPos('b').then(data=>{
                this.channels=data.data
                // console.log(this.channels)
                this.curlist(this.channels[0].id)
            }).catch(error=>{
                this.$message.error(error)
            })
            

        },
        methods:{
            curlist(id){
                this.cur=id
                getChannelArticlePos(id).then(data=>{
                    this.articles=data.data

                    if (data.data[0] != null) {
                        this.xxxx=data.data[0]
                    } else {
                        // this.xxxx.id = '',
                        // this.xxxx.title = '没有了',
                        // this.xxxx.titleImg = '../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg'
                        this.xxxx = null
                    }

                    if (data.data[1] != null) {
                        this.yyyy=data.data[1]
                    } else {
                        // this.yyyy.id = '',
                        // this.yyyy.title = '没有了',
                        // this.yyyy.titleImg = '../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg'
                        this.yyyy = null
                    }

                }).catch(error=>{
                    this.$message.error(error)
                })

            }
        }
    }
</script>

<style scoped>

</style>