<template>
    <div class="banbox ">
        <swiper class="swiper" :options="swiperOption"  style="height: 260px;" >
            <!-- <swiper-slide v-for="(item,index) in sliders" :key="index">
                <router-link :to="item.url" target="_blank" >
                    <img :src="item.img" width="100%" alt="">
                </router-link>
            </swiper-slide> -->
            <swiper-slide v-for="item in rotaionArticle" :key="item.id">
                <router-link :to='"/detail/"+item.id'  :title="item.title">
                    <img class="rimg" :src="item.titleImg" width="100%" :alt="item.title">
                    <span class="rtitle">{{item.title}}</span>
                </router-link>
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
            <div class="swiper-button-prev" slot="button-prev"></div>
            <div class="swiper-button-next" slot="button-next"></div>
        </swiper>
    </div>
</template>

<script>
    import { swiper, swiperSlide } from 'vue-awesome-swiper'
    import {getRotaionArticle} from '@/api/front'
    import 'swiper/swiper-bundle.css'
    export default {
        name: "FSwiper",
        components:{
            swiper,swiperSlide
        },
        data(){
            return{
                swiperOption: {
                    // speed: 600,
                    // parallax: true,
                    // effect: 'fade',
                    spaceBetween: 30,
                    centeredSlides: true,
                    autoplay: {
                        delay: 2500,
                        disableOnInteraction: false
                    },
                    pagination: {
                        el: '.swiper-pagination',
                        clickable: true
                    },
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev'
                    }
                },
                // sliders:[
                //     {
                //         img: "http://localhost:8081/cms/upload/fe36883d-7d0d-4139-9d52-ab88db56e94c.png",
                //         url:"http://www.baidu.com"
                //     },
                //     {

                //         img: "http://localhost:8081/cms/upload/ai.png",
                //         url:"http://www.baidu.com"
                //     },
                //     {
                //         img: "http://localhost:8081/cms/upload/test.jpg",
                //         url:"http://www.baidu.com"
                //     }

                // ],
                rotaionArticle:null
            }
        },
        created() {
            getRotaionArticle().then(data=>{
                this.rotaionArticle = data.data;
            })
        }
    }
</script>

<style scoped>

</style>