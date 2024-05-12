<template>
    <div v-title data-title="博客后台 | 栏目新增">
        <el-row :gutter="15">
            <el-form ref="formData" :model="formData" :rules="rules" size="medium" label-width="100px">
                <el-col :span="12">
                    <el-form-item label="栏目名称" prop="name">
                        <el-input v-model="formData.name"  placeholder="输入名称" clearable :style="{width: '100%'}"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="摘要" prop="summary">
                        <el-input v-model="formData.summary" placeholder="输入摘要内容" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="位置" prop="pos">
                        <el-select v-model="formData.pos" placeholder="请选择位置" :style="{width: '100%'}" @change="selectPosChange">
                            <el-option v-for="item in this.utils.channelPos"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" >
                    <el-form-item label="父栏目" prop="parentId">
                        <!-- <treeselect v-model="formData.parentId" :multiple="false" :options="treeDate" /> -->
                        <el-select v-model="formData.parentId" placeholder="请选择父分栏" :disabled="formData.pos != 'A'">
                            <el-option
                            v-for="item in treeDate"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                

                <el-col :span="12">
                    <el-form-item label="是否单页" prop="single" >
                        <el-switch
                                v-model="formData.single"
                                active-color="#13ce66"
                                inactive-color="#999999"
                                @change="switchSingleChange">
                        </el-switch>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item label="栏目图" prop="channelImg">
                        <el-upload
                                class="avatar-uploader"
                                action="string"
                                :show-file-list="false"
                                :http-request="uploadImg"
                                :before-upload="beforeAvatarUpload">
                            <img v-if="formData.channelImg" :src="formData.channelImg" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-col>
                <!-- <el-col :span="12">
                    <el-form-item label="外链" prop="url">
                        <el-input v-model="formData.url" placeholder="输入链接" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="SEO标题" prop="seoTitle">
                        <el-input v-model="formData.seoTitle" placeholder="输入SEO标题" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item label="SEO关键字" prop="seoKeyword">
                        <el-input v-model="formData.seoKeyword" placeholder="输入SEO关键字" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="SEO描述" prop="seoDescription">
                        <el-input v-model="formData.seoDescription" placeholder="输入SEO描述" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                </el-col> -->
                <el-col :span="24" v-if="formData.single">
                    <el-form-item label="正文" prop="content">
                        <v-md-editor v-model="formData.content" height="400px"></v-md-editor>
                    </el-form-item>
                </el-col>
                <el-col :span="15">
                    <el-form-item>
                        <el-button @click="$router.back()">取消</el-button>
                        <el-button type="primary" :loading="loading" @click="submitForm">保存</el-button>
                    </el-form-item>
                </el-col>
            </el-form>
        </el-row>
    </div>
</template>

<script>
    import {create,tree,upload} from "../../../api/channel";
    import {queryByPos} from "../../../api/front"
    // import the component
    // import Treeselect from '@riophae/vue-treeselect'
    // import the styles
    // import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    export default {
        name: "Add",
        // components: { Treeselect },
        data(){
            return{
                formData: {
                    name: '',
                    parentId: 0,
                    channelImg: '',
                    summary: '',
                    single: false,
                    url: '',
                    pos: 'A',
                    seoTitle: '',
                    seoKeyword: '',
                    seoDescription: '',
                    content: '',
                },
                // posList: ['A','B','C','D','E','F','G'],
                treeDate:[],
                loading: false,
                rules: {
                    name: [{required: true, message: '请输入名称', trigger: 'blur'}],

                }
            }
        },
        created(){
            queryByPos('A').then(data=>{
                this.treeDate=data.data
                this.treeDate.push({id:0, name:"无父栏目"})
            })
        },
        methods:{
            //事件
            selectPosChange(e) {
                // console.log(e)
                if (e != 'A') this.formData.parentId = 0
            },
            switchSingleChange(e) {
                // console.log(e)
                if (e == false) {
                    // console.log("content:"+this.formData.content)
                    this.formData.content = ''
                    // console.log("content:"+this.formData.content)
                }
            },
            submitForm(form){
                this.$refs['formData'].validate(valid=>{
                    if (valid) {
                        this.loading=true

                        if (this.formData.single){
                            this.formData.single='Y'
                        }else {
                            this.formData.single='N'
                        }
                        create(this.formData).then(data=>{
                            this.loading=false
                            this.$message.success(data.message)
                            this.$refs['formData'].resetFields()
                            this.$router.push('/channel')
                        }).catch(error=>{
                            this.loading=true
                            this.$message.error(error)
                        })
                    }
                })
            },
            close(){
                this.$emit("hideDialog")
            },
            uploadImg(param){
                const  formData = new FormData();
                formData.append("file",param.file)
                upload(formData).then(data=>{
                    this.formData.channelImg = data.data
                }).catch(error=>{
                    this.$message.error(error)
                })
            },
            //上传之前的验证
            beforeAvatarUpload(file){
                const isIMAGE = (file.type === 'image/jpeg') ||  (file.type === 'image/jpg') || (file.type ==='image/png')  || (file.type === 'image/webp') || (file.type === 'image/gif')
                const isLt2M = file.size / 1024 / 1024 < 10
                if (!isIMAGE) {
                    this.$message.error('上传文件只能是图片格式!')
                }
                if (!isLt2M) {
                    this.$message.error('上传文件大小不能超过 10MB!')
                }
                return isIMAGE && isLt2M
            },
        }
    }
</script>

<style scoped>

    .avatar-uploader{
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        width:80px;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 24px;
        color: #8c939d;
        width: 80px;
        height: 80px;
        line-height: 80px;
        text-align: center;
    }
    .avatar {
        width: 80px;
        height: 80px;
        display: block;
    }

</style>