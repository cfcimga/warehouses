<template>
    <mainLayout :routeName="routeName">

        <div class="avatar-uploader" >
            <el-row>

                <el-upload
                        :action="'http://localhost:8181/backEndWarehouse/user/addImg/'+user.uId"
                        ref="upload"
                        :headers="headers"
                        :show-file-list="false"
                        :auto-upload="false"
                        :on-change="PreviewImg"
                        :on-success="handleAvatarSuccess"
                        :on-error="handleError"
                        :before-upload="beforeAvatarUpload"
                        accept = "image/jpeg,image/jpg,image/png"
                       >
                    <el-avatar :size="150" :src="imageUrl" ref="img" @error="false" style="margin: 10px">
                        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"/>
                    </el-avatar>
                </el-upload>

            </el-row>
            <el-row>
                <el-row>
                <!--<p>-->
                    <!--<label for="uId" style="width: 80px" >用户编号：</label>-->
                    <!--<el-input v-model="user.uId" disabled="" Id="uId"></el-input>-->
                <!--</p>-->
                <!--<p>-->
                    <!--<label for="name" style="width: 80px" ><span style="color: red">*</span>用户昵称：</label>-->
                    <!--<el-input v-model="user.uName" id="name"></el-input>-->
                <!--</p>-->
                <!--<p>-->
                    <!--<label for="uPhone" style="width: 80px">用户电话：</label>-->
                    <!--<el-input v-model="user.uPhone" id="uPhone"></el-input>-->
                <!--</p>-->
                <!--<p>-->
                    <!--<label  style="width: 80px">用户性别：</label>-->
                    <!--<el-select v-model="user.uSex" placeholder="请选择性别"  id="uSex" style="width: 400px">-->
                        <!--<el-option label="男" value="男"></el-option>-->
                        <!--<el-option label="女" value="女"></el-option>-->
                    <!--</el-select>-->
                <!--</p>-->
                <!--<p>-->
                    <!--<label for="uEmail" style="width: 80px; position: relative;left: 12px">Email：</label>-->
                    <!--<el-input v-model="user.uEmail" id="uEmail" style="width: 400px;position: relative;left: 12px" ></el-input>-->
                <!--</p>-->
                <!--<p>-->
                    <!--<label for="uPassword" style="width: 80px;position: relative;left: 16px"><span style="color: red">*</span>密码：</label>-->
                    <!--<el-input type="password" v-model="user.uPassword" id="uPassword" style="width: 400px;position:relative;left: 16px"></el-input>-->
                <!--</p>-->
                <!--<p>-->
                    <!--<label for="rePassword" style="width: 80px"><span style="color: red">*</span>确认密码：</label>-->
                    <!--<el-input type="password" v-model="user.rePassword" id="rePassword"></el-input>-->
                <!--</p>-->
                <!--<p>-->
                    <!--<el-button type="primary" @click="submitForm">立即修改</el-button>-->
                    <!--<el-button>取消</el-button>-->
                <!--</p>-->
                </el-row>
                <el-form ref="form" :model="user" :rules="rules" label-width="80px"  >
                    <el-form-item label="用户编号">
                        <el-input v-model="user.uId" disabled=""></el-input>
                    </el-form-item>
                    <el-form-item label="用户昵称" prop="uName">
                        <el-input v-model="user.uName" oninput="if (value.length>50) value= value.slice(0,50) "></el-input>
                    </el-form-item>
                    <el-form-item label="用户电话" prop="uPhone">
                        <el-input v-model="user.uPhone" oninput="if (value.length>11) value= value.slice(0,11) "
                                  onkeyup="this.value=this.value.replace(/\D/g,'')"
                                  onafterpaste="this.value=this.value.replace(/\D/g,'')"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="uSex">
                        <el-select v-model="user.uSex" placeholder="请选择性别">
                            <el-option label="男" value="男"></el-option>
                            <el-option label="女" value="女"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Email" prop="uEmail">
                        <el-input v-model="user.uEmail" oninput="if (value.length>25) value= value.slice(0,25) "></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="uPassword">
                        <el-input type="password" v-model="user.uPassword" oninput="if (value.length>15) value= value.slice(0,15) "></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePassword">
                        <el-input type="password" v-model="user.rePassword" oninput="if (value.length>15) value= value.slice(0,15) "></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('form')">立即修改</el-button>
                        <el-button @click="resetForm()">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-row>


        </div>

    </mainLayout>

</template>
<script>
    import mainLayout from '@/components/mainLayout'
    import {editUser,deleteImg} from '@/service/myUserService.js'
    import loading from '@/components/loading.vue'
    import axios from 'axios'
    export default {
        name:'userInfoManagement.vue',
        inject:['reload'],
        data() {
            var checkEmail = (rule, value, cb) => {
                // 验证邮箱的正则表达式
                const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
                if (regEmail.test(value)) {
                    // 合法的邮箱
                    return cb();
                }
                cb(new Error("请输入合法的邮箱"));
            };
            var checkPhone = (rule, value, cb) => {
                // 验证邮箱的正则表达式
                const regPhone =  /^([1]\d{10}|([\(（]?0[0-9]{2,3}[）\)]?[-]?)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?)$/;
                if (regPhone.test(value)) {
                    // 合法的号码
                    return cb();
                }
                cb(new Error("请输入合法的号码"));
            };
            return {
                routeName:{parentName:'系统管理',name:'个人信息管理',needSlot:false},
                imageUrl: '',   //http://localhost:8181/backEndWarehouse/headPortrait/16177110979001612227922(1).jpg
                localFile:'',
                authorization:null,
                waiting:false,
                authSrc:null,
                isUpload:false, //判断是否上传了头像
                user:{  //用于表单的展示数据
                    uId:null,
                    uName:null,
                    uPassword:null,
                    rePassword:null,
                    uPhone:null,
                    uAuthority:null,
                    uHeadPortrait:null,
                    uEmail:null,
                    uSex:null,
                    uCreatorId:null,
                    uCreationTime:null,
                    uModifierId:null,
                    uModificationTime:null
                },
                dataUrl:'',
                rules: {
                    uName: [
                        { required: true, message: '请输入用户名称', trigger: 'blur' },
                    ],
                    uPhone: [
                        { required: true, message: '请输入用户电话', trigger: 'blur' },
                        { min: 11, max: 11, message: "电话长度在 11位", trigger: "blur" },
                        {validator:checkPhone,message:"请输入正确的电话号码",trigger:"blur"}
                    ],
                    uEmail: [
                        { required: false, message: '请输入备注', trigger: 'blur' },
                        {validator: checkEmail, message: "请输入正确的邮箱地址", trigger: "blur"}
                    ],
                    uSex: [
                        { required: true, message: '请选择性别', trigger: 'blur' }
                    ],
                    uPassword: [
                        { required: true, message: '请输入密码', trigger: 'change' }
                    ],
                    rePassword:[
                        { required: true, message: '请输入确认密码', trigger: 'change' }
                    ],
                },
            };
        },
        components:{
          mainLayout,
            loading
        },
        created(){

          this.user = this.deepClone(this.$store.state.myUser.data);

          this.user.rePassword = this.user.uPassword;
          this.imageUrl = 'http://localhost:8181/backEndWarehouse/'+this.user.uHeadPortrait;
          console.log('图片地址:'+this.imageUrl)
        },
        mounted(){

        },
        computed:{
            headers(){
                let authorization = sessionStorage.getItem('Authorization');
                return {"authorization":authorization}
            }
        },
        methods: {
            resetForm() {
                this.user = this.deepClone(this.$store.state.myUser.data)
                this.imageUrl = 'http://localhost:8181/backEndWarehouse/'+this.user.uHeadPortrait;
                const mainImg = this.$refs.upload
                console.log(mainImg)
                if (mainImg && mainImg.length) {
                    mainImg.forEach(item => {
                        item.clearFiles()
                    })
                }
                this.isUpload = false;
                this.user.rePassword = this.deepClone(this.user.uPassword)
            },

            handleError() {
                this.$message.error("上传失败,请重新上传图片!");
            },
            deepClone(obj) {  //深层拷贝
                let _obj = JSON.stringify(obj),
                    objClone = JSON.parse(_obj);
                return objClone;
            },

           async onSubmit(){
                let result = await deleteImg("headPortrait/man.jpg")
               console.log(result)
            },
            PreviewImg(event){
                let URL = window.URL || window.webkitURL;
                this.imageUrl = URL.createObjectURL(event.raw);
                this.isUpload = true;

                console.log(this.imageUrl)
            },
            async handleAvatarSuccess(res, file) {
                // this.imageUrl = URL.createObjectURL(file.raw);
                let tempUrl = this.user.uHeadPortrait;
                this.user.uHeadPortrait = res;
                console.log(res)
                console.log(this.user.uHeadPortrait)
                this.imageUrl = "";
                let resp = await editUser(this.user);
                if(resp.code == 200){
                    if(tempUrl != "headPortrait/man.jpg" || tempUrl != "headPortrait/lady.jpeg"){
                        let result = await deleteImg(tempUrl)
                        console.log(result)
                    }
                    await this.$store.dispatch("myUser/fetchUser",this.user)
                    this.user = this.$store.state.myUser.data;
                    this.imageUrl = 'http://localhost:8181/backEndWarehouse/'+this.user.uHeadPortrait;
                    this.user.rePassword = this.user.uPassword;
                    this.$alert('修改成功', '提示', {
                        confirmButtonText: '确定',

                    });
                }else{

                    let result = await deleteImg(res)
                    this.$message.error('修改失败！！');
                }
                this.isUpload = false;  //把上传状态重置

            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg'  || 'image/png' || 'image/jpg';
                const isLt2M = file.size / 1024 / 1024 < 2;
                console.log(file)
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG/JPEG/PNG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            submitForm(formName){
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        if (this.user.rePassword == this.user.uPassword){
                            this.user.uModificationTime = this.getInfo().time;
                            this.user.uModifierId = this.$store.state.myUser.data.uId;
                            if(this.isUpload){
                                this.$refs.upload.submit();

                            }else{
                                let resp = await editUser(this.user);
                                console.log("123")
                                if(resp.code == 200){
                                    await this.$store.dispatch("myUser/fetchUser",this.user)
                                    this.user = this.$store.state.myUser.data;
                                    this.user.rePassword = this.user.uPassword;
                                    this.$alert('修改成功', '提示', {
                                        confirmButtonText: '确定',

                                    });
                                }else{
                                    this.$message.error(resp.msg);
                                }
                            }




                        } else{
                            this.$message.error('密码与确认密码不一致，请确认一致后再提交');
                        }
                    }
                })
            },
            // submitForm(){
            //    if(this.user.uName.trim()=="" || this.user.uPassword.trim()=="" || this.user.rePassword.trim()==""){
            //        this.$message.error('请把带星号的表项填写完整再点击提交！');
            //    }else{
            //        if (this.user.rePassword == this.user.uPassword){
            //            console.log(this.user)
            //            this.user.uModificationTime = this.getInfo().time;
            //            this.user.uModifierId = this.$store.state.myUser.data.uId;
            //
            //            this.$refs.upload.submit();
            //        } else{
            //            this.$message.error('密码与确认密码不一致，请确认一致后再提交');
            //        }
            //    }
            // },
            getInfo(){  //获取当前时间以及id
                let now = new Date()
                let obj={}
                let year = now.getFullYear();
                let month = now.getMonth()+1;
                if(month<"10"){
                    month = "0"+month;
                }
                let day = now.getDate();
                if(day<"10"){
                    day = "0"+day;
                }
                let hour = now.getHours()
                if(hour<"10"){
                    hour = "0"+hour;
                }
                let min = now.getMinutes();
                if(min<"10"){
                    min = "0"+min;
                }
                let second = now.getSeconds();
                if(second<"10"){
                    second = "0"+second;
                }
                obj.time = year+'-'+month+'-'+day+' '+hour+':'+min+':'+second
                let num = Math.floor(Math.random()*10000).toString();
                obj.id = parseInt(year+month+day+num);
                return obj

            }
        }
    }
</script>

<style scoped>
    .avatar-uploader {
        border: 1px solid #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        overflow: hidden;
        margin: 10px;
        text-align: center;
    }

    .el-form {
        text-align: left;
        margin-left: 27%;
        /*left: 50%;*/
        /*top: 50%;*/
        /*transform: translate(-50%,-50%);*/
    }
     .el-input{
        width: 400px;
         margin-right: 10px;

    }
     .el-upload:hover {
        border-color: #409EFF;
    }
    .content .el-row .el-button:hover {
        color: #414242
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        height: 178px;
        text-align: center;
    }
    .avatar {
        height: 178px;

    }
</style>

