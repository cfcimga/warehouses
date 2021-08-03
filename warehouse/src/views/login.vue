<template>
    <div class="maxbox">
        <div id="app">
            <!--vue-particles-->
            <div
                    color="#dedede"
                    :particleOpacity="0.7"
                    :particlesNumber="80"
                    shapeType="circle"
                    :particleSize="4"
                    linesColor="#dedede"
                    :linesWidth="1"
                    :lineLinked="true"
                    :lineOpacity="0.4"
                    :linesDistance="150"
                    :moveSpeed="3"
                    :hoverEffect="true"
                    hoverMode="grab"
                    :clickEffect="true"
                    clickMode="push"
                    class="beijing"
            ></div>
        </div>
        <!--

        下面介绍参数配置文件 app.js 文件的使用：

        color: String类型。默认'#dedede'。粒子颜色。
        particleOpacity: Number类型。默认0.7。粒子透明度。
        particlesNumber: Number类型。默认80。粒子数量。
        shapeType: String类型。默认'circle'。可用的粒子外观类型有："circle","edge","triangle", "polygon","star"。
        particleSize: Number类型。默认80。单个粒子大小。
        linesColor: String类型。默认'#dedede'。线条颜色。
        linesWidth: Number类型。默认1。线条宽度。
        lineLinked: 布尔类型。默认true。连接线是否可用。
        lineOpacity: Number类型。默认0.4。线条透明度。
        linesDistance: Number类型。默认150。线条距离。
        moveSpeed: Number类型。默认3。粒子运动速度。
        hoverEffect: 布尔类型。默认true。是否有hover特效。
        hoverMode: String类型。默认true。可用的hover模式有: "grab", "repulse", "bubble"。
        clickEffect: 布尔类型。默认true。是否有click特效。
        clickMode: String类型。默认true。可用的click模式有: "push", "remove", "repulse", "bubble"
        class="beijing" 设置背景图片

        <style lang="less">
        // 这一个部分是插件的哦
        .beijing {
        background-image: url("./start.jpg");
        background-size: cover;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        }
        </style>
        -->
        <el-form
                :model="loginForm"
                status-icon
                :rules="rules"
                ref="loginForm"
                label-width="100px"
                class="demo-ruleForm"
        >
            <h1 class="title">
                <i class="el-icon-menu"></i>
                仓库管理系统-登录
            </h1>

            <el-form-item label="账号" prop="username">
                <el-input type="text" v-model="loginForm.username"
                          onkeyup="this.value=this.value.replace(/\D/g,'')"
                          onafterpaste="this.value=this.value.replace(/\D/g,'')">
                </el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
            </el-form-item>

            <!--<el-form-item label="确认密码" prop="checkPass">-->
                <!--<el-input type="text" v-model="loginForm.checkPass" autocomplete="off"></el-input>-->
            <!--</el-form-item>-->

            <el-form-item>
                <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                <el-button @click="resetForm('loginForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>


<script>
    export default {
        data() {
            // 包含特殊字符哦
            const checkSpecificKey = function(str) {
                // 正则表达
                var specialKey = "[`~!#$^&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]‘'";
                for (var i = 0; i < str.length; i++) {
                    if (specialKey.indexOf(str.substr(i, 1)) != -1) {
                        //specialKey是否含有某一个你输入的特殊字符
                        return false; //如果你输入的账号中有特殊字符 直接退出来哦
                    }
                }
                return true;
            };

            // 验证密码的函数
            const pass = (rule, value, callback) => {
                if (value === "") {
                    //为空的验证
                    callback(new Error("请输入密码"));
                }
                // else if (value.length < 3 || value.length > 8) {
                //     //这是密码长度的验证
                //     callback(new Error("密码长度 3 - 8 位"));
                // } else if (!checkSpecificKey(value)) {
                //     callback(new Error("密码不能包含特殊字符"));
                // }
                else {
                    callback();
                }
            };

            // 确认密码的验证函数
            // const checkPass = (rule, value, callback) => {
            //     // rule是传入的验证规则 value是用户输入的值 callback是一个回调函数
            //     if (value === "") {
            //         // 如果等于空
            //         console.log(value);
            //         callback(new Error("请再次输入密码11")); // 输出提示
            //     } else if (value !== this.loginForm.password) {
            //         // 如果确认密码 和 密码不同
            //         callback(new Error("两次输入密码不一致1"));
            //     }
            //     // 如果直接调用 不传入任何错误信息 就是成功 绿色的勾勾
            //     callback();
            // };

            return {
                // 登录表单的数据
                loginForm: {
                    username: 123456,
                    password: "9999",
                },
                // 验证的规则（一份数据）
                rules: {
                // 验证用户名
                    username: [
                // 非空验证
                        { required: true, message: "请输入账号", trigger: "change" },
                        // { min: 6, max: 10, message: "账号长度在 6 - 8 位", trigger: "blur" }
                    ],
                    password: [
                    // 非空验证
                        { required: true, validator: pass, trigger: "change" }
                    ],
                    // 验证确认密码
//                     checkPass: [
// // 自定义验证函数
//                         { required: true, validator: checkPass, trigger: "blur" }
//                     ]

                    /*
                    验证规则字段说明：
                    { required: true/false 必填, message: "错误的提示信息", trigger: "触发验证的方式" }
                    { min: 最小长度, max: 最大长度, message: "错误的提示信息", trigger: "触发验证的方式" }
                    { validator： 自定义验证规则函数名， trigger: 'blur'}
                    */
                }
            };
        },
        created(){
            if(sessionStorage.getItem('flag')||
                sessionStorage.getItem('store')||
                sessionStorage.getItem('Authorization')){
                sessionStorage.removeItem('flag')
                sessionStorage.removeItem('store');
                sessionStorage.removeItem('Authorization')
            }
        },


        methods: {
            // 点击登录按钮 触发这个函数
            submitForm(formName) {
            // 获取表单组件 调用验证方法
                this.$refs[formName].validate(async valid => {
            // 如果所有验证通过 valid就是true
                    if (valid) {
            // 后续就可以把收集的账号和密码 一起发送给后端 验证用户名和密码的正确性。
            // 收集账号和密码
                        let params = {
                            uId: this.loginForm.username,
                            uPassword: this.loginForm.password
                        };
                        let v=this;
                        let flag = await this.$store.dispatch("myUser/fetchUser",params)
                        if(flag){
                            // sessionStorage.setItem("userSelf",params);
                            v.$router.push('/inform');
                            v.$message.success('登录成功');
                        }else{
                            v.$message.error('登录失败,用户名或密码错误！');
                        }

                        // 发送请求 把参数发给后端（把用户名和密码发给后端 验证是否正确）
                        // console.log(params)
                        // 直接跳转到后端主页
                        // this.$router.push("/");
                    } else {
                        // 否则就是false
                        this.$message.error("验证失败！");
                        return false;
                    }
                });
            },
            // 点击重置按钮 触发这个函数
            resetForm(formName) {
                this.$refs.loginForm.resetFields() //获取整个表单组件 调用重置方法
                // this.$refs[formName].resetFields();
            }
        }

    };
</script>
<style >

    .beijing {
        background-image: url("../assets/login1.png");
        background-size: cover;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }

    #app {
        /*//最大的容器*/
        width: 100%;
        height: 100%;
        /*margin: 10 auto;*/
        text-align: center;

    }
    #app .el-form {
        /*//第二大的容器*/
        /*// 垂直 水平 居中*/
        width: 450px;
        height: 350px;
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        margin: auto;
    }
    #app .el-form .el-form-item {
        /*//这是那三行的错误哦*/
        margin-top: 28px;
    /*//让变淡之间是有间距的哦*/

    }
    #app .el-form .el-form-item .el-form-item__label {
        color: #ffffff;
    }
    #app .el-form .title {
        /*//超市管理系统的标题*/
        color: #ffffff;
        padding-left: 40px;
    /*//让变体居中 这里使用的是padding-left*/
    }
</style>