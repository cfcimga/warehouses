<template>
    <div id="main">
        <p id="p1">{{routeName.parentName}}</p>
        <p id="p2"><span class="ttl"> <i class="el-icon-s-home"></i> {{routeName.parentName}} > {{routeName.name}}</span> </p>
        <el-main class="background">

            <el-row class="list">
                <el-row class="informs"><i class="el-icon-tickets" style="margin-right: 3px"></i><span>公告墙</span>
                    </el-row>
                <div style="height: 86%;">
                    <el-row v-for="(item,index) in paginationData" :key="index+''"  class="inform">
                        <i class="el-icon-message-solid"></i>
                        <span @click="showMdform(item)" class="hov">{{item.fTitle}}</span>
                        <span v-if="isRead(item.mdform)" class="finishRead">已读</span>
                        <span v-else class="notRead">未读</span>
                        <span style="color: #ff4038" class="del" @click="deleteInform(item.fId)" v-if="$store.state.myUser.data.uAuthority==1"><i class="el-icon-delete"></i></span>
                    </el-row>

                </div>
                <el-pagination
                        @current-change="handleCurrent"
                        :current-page="currentPage"
                        :page-size="pagesize"
                        layout="prev, pager, next"
                        :total="total">
                </el-pagination>

                <el-button type="primary" @click="showDialog" class="btm" v-if="$store.state.myUser.data.uAuthority==1">Add</el-button>
            </el-row>


            <el-dialog
                    title="通知"
                    :visible.sync="showFlag"
                    width="60%"
                    >
                <el-form :rules="rules" :model="inform" ref="inform" class="demo-ruleForm">
                    <el-form-item label="标题" label-width="120px" prop="fTitle">
                        <el-input   v-model="inform.fTitle" oninput="if (value.length>50) value= value.slice(0,50) "></el-input>
                    </el-form-item>
                    <el-form-item label="内容" label-width="120px" prop="fContent">
                        <el-input  type="textarea"  v-model="inform.fContent" oninput="if (value.length>1000) value= value.slice(0,1000) " ></el-input>
                    </el-form-item>

                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelDialog">取 消</el-button>
                    <el-button type="primary" @click="addInform('inform')">确 定</el-button>
              </span>
            </el-dialog>
            <el-dialog
                    :title="inform.fTitle"
                    :visible.sync="showForm"
                    width="60%">
                <el-form :rules="rules" :model="inform" class="demo-ruleForm">
                    <el-row style="border: 1px solid;height: 200px;border-radius: 3px;text-align: left;overflow: auto" >{{inform.fContent}}</el-row>
                    <el-row><span>发起人：</span>{{userName}} <span style="margin-left: 10px">时间：{{inform.fCreationTime}}</span> </el-row>

                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelForm">取 消</el-button>
                    <el-button type="primary" @click="addMdform()" v-if="okFlag">确 定</el-button>
              </span>
            </el-dialog>
        </el-main>
        <!--<el-main class="background">-->

        <!--</el-main>-->
    </div>


</template>

<script>
    import mainLayout from '@/components/mainLayout'
    import loading from '@/components/loading.vue'
    import {getAllInform,addInform,addMdInform,deleteInform} from '@/service/informService.js'
    import {findUserById} from '@/service/myUserService.js'
    export default {
        name: "inform",
        components:{
            mainLayout,
            loading
        },
        data(){
            return{
                okFlag:false,
                userName:null,
                waiting:false,
                showForm:false,
                Editortext:'',
                stashList:[],
                currentPage:1,
                paginationData:[],
                total:0,
                pagesize:10,
                tempInform:null,
                informs:null,
                routeName:{parentName:'Home',name:'通知',needSlot:false},
                showFlag:false,
                editorContent:null,
                inform:{
                    fId:null,
                    fTitle:null,
                    fContent:null,
                    fCreatorId:null,
                    fCreationTime:null,
                    mdform:null
                },
                rules: {
                    fTitle: [
                        { required: true, message: '请输入标题', trigger: 'blur' },
                    ],
                    fContent: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                },
            }
        },
        async created(){

            if(!sessionStorage.getItem('flag')){
                sessionStorage.setItem('flag',1)
                console.log("iamhere")
                location.reload()
            }
            this.tempInform = this.deepClone(this.inform);

            this.getList();
            console.log(this.tempInform);
        },

        methods:{
            deleteInform(fId){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then( async () => {
                    let resp = await deleteInform(fId);
                    console.log(resp)
                    if(resp.code==200){
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getList()
                    }else{
                        this.$message.error(resp.msg)
                    }
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            isRead(mdform){
              if(mdform.length>0){
                  let md = mdform.filter(value=>{
                      return value.uId == this.$store.state.myUser.data.uId
                  })
                  if(md.length>0){

                      return true  //有值，意味着已读
                  }else{
                      return false  //否则未读
                  }
              }else{
                  return false
              }
            },
            showDialog(){
                this.inform = this.deepClone(this.tempInform);
                this.showFlag=true
            },
            async showMdform(inform){
                  this.inform = inform
                  this.okFlag = !this.isRead(this.inform.mdform)
                  let user = await findUserById(inform.fCreatorId);
                  this.userName = user.uName;
                  this.showForm=true
            },
            async addMdform(){
                let mdform = {}
                mdform.fId = this.inform.fId
                mdform.uId = this.$store.state.myUser.data.uId
                mdform.state = 1;
                let resp = await addMdInform(mdform);
                console.log(resp)
                if(resp.code == 200){
                    this.$message.success("操作成功")

                    this.getList();
                    this.showForm=false
                }else{
                    this.$message.error(resp.msg);
                    this.showForm=false
                }

            },
            cancelForm(){
                this.showForm=false
            },
            addInform(formName){
                let obj = this.getTime('f');
                this.inform.fCreatorId = this.$store.state.myUser.data.uId;
                this.inform.fCreationTime = obj.time;
                this.inform.fId = obj.id;
                console.log(this.inform)
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await addInform(this.inform);
                        console.log(resp)
                        if(resp.code == 200){

                            this.$alert('发布成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    this.getList();
                                }
                            });
                            this.showFlag=false
                        }else{
                            this.$message.error(resp.msg);
                            this.showFlag=false
                        }
                    } else {
                        this.$message.error('表单提交失败，请输入完整表单！');
                        this.showFlag=false
                    }
                });
            },
            getTime(str){  //获取当前时间
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
                let num = Math.floor(Math.random()*100).toString();
                obj.id = str+year+month+day+hour+min+second+num;
                return obj
            },
            cancelDialog(){
                this.showFlag=false
            },
            deepClone(obj) {  //深层拷贝
                let _obj = JSON.stringify(obj),
                    objClone = JSON.parse(_obj);
                return objClone;
            },
            async getList(){    //async与await必须要加上，否则根据js的异步语法规则会先运行axios之后的内容，完了之后才会执行axios中的内容
                //一般这里会有一个axios请求
                this.waiting = true
                this.informs = await getAllInform();
                this.waiting = false

                console.log(this.informs)
                this.handleCurrent(this.currentPage)
                this.stashList = this.informs  //暂存数组  当搜索为空时候  数组展示所有数据
            },
            handleCurrent(val) { //改变页数的时候，触发
                this.currentPage = val
                this.tableList()
            },
            tableList() { //获取当前页的所有数据，存进paginationData
                this.paginationData = []  //分页数组  tableData 所有的数据
                for (let j = this.pagesize * (this.currentPage - 1);j < this.pagesize * this.currentPage; j++) {
                    if (this.informs[j]) {
                        this.paginationData.push(this.informs[j])
                    }
                }
                this.total = this.informs.length
                console.log(this.paginationData)
            },

        },

    }

</script>

<style scoped>
    #main{
        background-color: #f2f3fb;
        height: 820px;
        border: #dfdfdf 1px solid;

    }
    .hov:hover{
        color: #ff4038;
        cursor: pointer;
    }
    #p1{
        float:left;
        margin:12px 10px;
        font-size: 23px;
        text-align: left;
        line-height: 100%;
        position: relative;

    }
    .background{
        background-color: white;
        margin: 12px 18px;
        box-shadow: 0px -3px 3px 0px #b4b4b4;
        border-radius: 3px;

    }
    .list{
        height: 480px;
    }
    .list .btm{
        margin-top: 10px;
        position: absolute;
        left: 92%;
        top:87%;
    }
    .list .el-pagination{
        text-align: left;
    }
    .finishRead{
        font-weight: normal;
        color: white;
        font-size: 10px;
        margin-left: 10px;
        background-color: #18ae13;
        padding: 2px 8px;
        border-radius: 3px;
    }
    .notRead{
        font-weight: normal;
        color: white;
        font-size: 10px;
        margin-left: 10px;
        background-color: #d54b3b;
        padding: 2px 8px;
        border-radius: 3px;
    }
    .slt{
        height:40px;
        background-color: white;
        margin: 15px 5px;
        box-shadow: 0px -1px 0px #b4b4b4;
    }
    #p2{
        text-align: right;
    }
    #p2 .ttl{
        /*left: 76%;*/
        font-size: 10px;

        margin-right: 5px;
        /*position: relative;*/
    }
    .informs{
        font-size: 23px;
        text-align: left;
        margin-top: -15px;
    }
    .inform{
        font-size: 15px;
        text-align: left;
        padding-left: 15px;
        margin-top: 5px;
        background-color: #e8e9f1;
        border-radius: 5px;
        font-weight: bold;
        color: #4917ff;

    }
    .inform i{
        margin: 10px 5px 10px 10px;
    }

    .inform .del{
        color: #ff4038;
        display: none;
        position: absolute;
        right: 5px;
    }
    .inform:hover .del{
        display: inline  ;
    }
    .el-textarea__inner{
        height: 150px;
        overflow-y: auto;
        /* overflow-y: auto;兼容ie  */
    }

</style>