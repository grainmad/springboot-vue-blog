<template>
    <div class="whitebg lanmu">
        <img v-if="channel.channelImg==null ||channel.channelImg=='' " width="130px" height="100px" src="../../assets/images/263d87adb0f1f2d66e9ca3e2f0f9f907.jpeg">
        <img v-else :src="channel.channelImg">
        <h1>{{channel.name}}</h1>
        <p>{{channel.summary}}</p>
    </div>
</template>

<script>

    export default {
        name: "ChannelInfo",
        props:['channel'],
        data(){
            return{}
        }
    }
</script>

<style scoped>

</style>