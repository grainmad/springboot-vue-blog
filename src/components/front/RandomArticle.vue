<!-- 随机获得两条博客展示 -->

<template>

    <div class="headline">
        <ul>
            <li><router-link :to='"/detail/"+title1.id'  :title="title1.title">
                <img :src="title1.titleImg" :alt="title1.title">
                <span>{{title1.title}}</span>
            </router-link></li>
            <li><router-link :to='"/detail/"+title2.id' :title="title2.title">
                <img :src="title2.titleImg" :alt="title2.title">
                <span>{{title2.title}}</span>
            </router-link></li>
        </ul>
    </div>
</template>

<script>

    import {getRandomArticle} from '@/api/front'
    export default {
        name: "RandomArticle",
        data(){
            return{
                articles:[],
                title1: [],
                title2: []
            }
        },
        created() {
            getRandomArticle().then(data=>{
                this.articles=data.data
                this.title1=this.articles[0]
                this.title2=this.articles[1]
            })
        },

    }
</script>

<style scoped>

</style>