<template>
    <div v-title data-title="博客">
        <Header @child-even="parentEven" @child-evenTitle="parentEvenTitle"></Header>
        <article>
            <div class="lbox">
                <FSwiper></FSwiper>
                <RandomArticle></RandomArticle>
                <Table v-if="xx"></Table>
                <NewBlog v-if="xx" :search="newData" :childevenTitle="title"></NewBlog>
            </div>
            <div class="rbox">
              <AboutMe></AboutMe>
                <Notice></Notice>
                <OrderByView></OrderByView>
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
    import FSwiper from "../../components/front/FSwiper";
    import RandomArticle from "../../components/front/RandomArticle";
    import AboutMe from "../../components/front/AboutMe";
    import Table from "../../components/front/Table";
    import NewBlog from "../../components/front/NewBlog";
    import Notice from "../../components/front/Notice";
    import OrderByView from "../../components/front/OrderByView";
    import FriendLink from "../../components/front/FriendLink";
    import Footer from "../../components/front/Footer";
    import Tag from  '../../components/front/Tag'

    export default {
        name: "Index",
        components: {Footer,
            FriendLink, OrderByView, Notice, NewBlog, Table, AboutMe, FSwiper, Header, RandomArticle, Tag},

        data(){
            return{
                articles: [],
                pages: '',
                total: 0,
                pageNo: '',
                xx: true,
                newData: '',
                title:''
            }
        },
        beforeRouteUpdate(to,from ,next){
            this.xx = false;
            this.$nextTick(() => {
                this.xx = true
            })
            next()
        },
        methods:{
            parentEven(data) {
                console.log(data)
                this.newData = data;
            },
            parentEvenTitle(data){
                this.title=data
            }
        },
        mounted() {

        }
    }
</script>

<style scoped>

</style>