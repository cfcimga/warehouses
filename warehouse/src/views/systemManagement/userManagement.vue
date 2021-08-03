<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='uModificationTime'| value=='uCreationTime'">
                </el-date-picker>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='uAuthority'" style="width: 88%">
                    <el-option label="一级" value= 1></el-option>
                    <el-option label="二级" value= 2></el-option>
                    <el-option label="三级" value= 3></el-option>
                </el-select>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='uSex'" style="width: 88%">
                    <el-option label="男" value= "男"></el-option>
                    <el-option label="女" value= "女"></el-option>
                </el-select>
                <el-input v-model="watchSearch" placeholder="watch监听搜索" size="small" v-else></el-input>

                <el-select v-model="value" placeholder="请先选择" id="slt">
                    <el-option
                            v-for="item in selection"
                            :key="item.key"
                            :label="item.key"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-row>
        </div>
        <loading v-if="wait==true"></loading>
        <div class="content" v-else>

            <el-row v-if="$store.state.myUser.data.uAuthority==1">
                <el-button type="primary" @click="showAddForm"><i class="el-icon-upload el-icon--right"></i>添加</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="showEditForm">修改</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="deleteUser">删除</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog :title="addOrEdit? '添加用户':'修改用户'" :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="user" ref="user" class="demo-ruleForm" >
                        <el-form-item label="用户编号" label-width="120px" prop="uId">
                            <el-input   value="value.slice(0,8)"  v-model="user.uId"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')"
                                        onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                        oninput="if (value.length>8) value= value.slice(0,8) ">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="用户名称" label-width="120px" prop="uName">
                            <el-input   v-model="user.uName" oninput="if (value.length>25) value= value.slice(0,25) "></el-input>
                        </el-form-item>
                        <el-form-item label="权限" label-width="120px" prop="uAuthority" style="text-align: left">
                            <el-select v-model="userAuthority" placeholder="请选择权限等级" style="width: 100%">
                                <el-option label="一级" value= 1></el-option>
                                <el-option label="二级" value= 2></el-option>
                                <el-option label="三级" value= 3></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="联系电话" label-width="120px" prop="uPhone">
                            <el-input   v-model="user.uPhone" oninput="if (value.length>11) value= value.slice(0,11) "
                                        onkeyup="this.value=this.value.replace(/\D/g,'')"
                                        onafterpaste="this.value=this.value.replace(/\D/g,'')"></el-input>
                        </el-form-item>
                        <el-form-item label="email地址" label-width="120px" prop="uEmail">
                            <el-input    v-model="user.uEmail" oninput="if (value.length>25) value= value.slice(0,25) "></el-input>
                        </el-form-item>
                        <el-form-item label="性别" label-width="120px" prop="uSex" style="text-align: left">
                            <el-select v-model="user.uSex" placeholder="请选择性别" style="width: 100%">
                                <el-option label="男" value= "男"></el-option>
                                <el-option label="女" value= "女"></el-option>
                            </el-select>
                        </el-form-item>


                        <el-form-item label="密码" label-width="120px" prop="uPassword">
                            <el-input type="password"  v-model="user.uPassword" oninput="if (value.length>15) value= value.slice(0,15) "></el-input>
                        </el-form-item>
                        <el-form-item label="密码确认" label-width="120px" prop="rePassword">
                            <el-input type="password"  v-model="user.rePassword" oninput="if (value.length>15) value= value.slice(0,15) "></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addOrEdit ? exitAdd() : exitEdit()">取 消</el-button>
                        <el-button type="primary"  @click="addOrEdit ? submitForm('user') : editUser('user')">确 定</el-button>
                    </div>
                </el-dialog>

            </el-row>

            <el-table
                    :data="paginationData"
                    max-height="540px"
                    ref="tb"
                    @selection-change="handSelectionChange"
                    border
                    :cell-style="{'text-align':'center'}"
                    @select-all="onSelectAll"
                    :default-sort = "{prop: 'uId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="uId"
                        label="用户编号"
                        width="180"
                        sortable
                        fixed="left">
                </el-table-column>
                <el-table-column
                        prop="uName"
                        label="用户名称"
                        show-overflow-tooltip
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="uPhone"
                        label="联系电话"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="uAuthority"
                        label="权限"
                        sortable
                        width="180">
                    <template slot-scope="scope">
                        {{scope.row.uAuthority | ctype}}
                    </template>
                </el-table-column>

                <el-table-column
                        prop="uEmail"
                        label="email地址"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="uSex"
                        label="性别"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="uCreatorId"
                        label="创建人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="uCreationTime"
                        label="创建时间"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="uModifierId"
                        label="最后修改人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="uModificationTime"
                        label="最后修改时间"
                        sortable
                        width="180">
                </el-table-column>
            </el-table>

            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30]"
                    :page-size="pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    style="margin:10px 0">
            </el-pagination>


        </div>
    </mainLayout>
</template>

<script>
    import mainLayout from '@/components/mainLayout.vue'
    import {getAllUser,addUser,editUser,deleteUser} from '@/service/myUserService.js'
    import loading from '@/components/loading.vue'

    export default {
        name: "userManagement",
        data(){
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
            const pass = (rule, value, callback) => {
                if (value === "") {
                    //为空的验证
                    callback(new Error("请输入密码"));
                } else if (value.length < 3 || value.length > 10) {
                    //这是密码长度的验证
                    callback(new Error("密码长度 3 - 10 位"));
                } else if (!checkSpecificKey(value)) {
                    callback(new Error("密码不能包含特殊字符"));
                } else {
                    callback();
                }
            };
            // 确认密码的验证函数
            // const checkPass = (rule, value, callback) => {
            //     // rule是传入的验证规则 value是用户输入的值 callback是一个回调函数
            //     if (value === "") {
            //         // 如果等于空
            //         console.log(value);
            //         callback(new Error("请再次输入密码")); // 输出提示
            //     } else if (value !== this.user.uPassword) {
            //         // 如果确认密码 和 密码不同
            //         callback(new Error("两次输入密码不一致"));
            //     }
            //     // 如果直接调用 不传入任何错误信息 就是成功 绿色的勾勾
            //     callback();
            // };
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
                routeName:{parentName:'系统管理',name:'用户管理',needSlot:true},
                tableData:null,
                total: 0, //数组总数
                currentPage: 1, //当前页
                paginationData: [], //分页数组,用于存储每页所拥有的数据
                pagesize: 10, //每页数据
                searchContent: "",//搜索内容
                stashList: [],
                watchSearch: "",
                checkedrow:[],
                addFormVisible: false,
                editFormVisible: false,
                value:"",
                wait: false,
                selection:[
                    {key:"用户编号",value:"uId"},
                    {key:"用户名称",value:"uName"},
                    {key:"用户电话",value:"uPhone"},
                    {key:"权限",value:"uAuthority"},
                    {key:"email地址",value:"uEmail"},
                    {key:"性别",value:"uSex"},
                    {key:"创建人编号",value:"uCreatorId"},
                    {key:"最后修改人编号",value:"uModifierId"},
                    {key:"创建时间",value:"uCreationTime"},
                    {key:"最后修改时间",value:"uModificationTime"},
                ],
                rules: {
                    uId:[
                        { required: true, message: '请输入用户编号', trigger: 'blur' },
                        // { min: 6, max: 10, message: "账号长度在 6 - 8 位", trigger: "change" }
                    ],
                    uName: [
                        { required: true, message: '请输入用户名称', trigger: 'blur' },
                    ],
                    uPhone: [
                        { required: false, message: '请输入用户电话', trigger: 'blur' },
                        { min: 11, max: 11, message: "电话长度在 11位", trigger: "blur" },
                        {validator:checkPhone,message:"请输入正确的电话号码",trigger:"blur"}
                    ],
                    uAuthority: [
                        { required: true, message: '请输入用户权限', trigger: 'blur' }
                    ],
                    uEmail: [
                        { required: false, message: '请输入备注', trigger: 'blur' },
                        {validator: checkEmail, message: "请输入正确的邮箱地址", trigger: "blur"}
                    ],
                    uSex: [
                        { required: true, message: '请选择性别', trigger: 'blur' }
                    ],
                    uPassword: [
                        { required: true, validator: pass, trigger: "blur" }
                    ],
                    rePassword:[
                        { required: true, message: '请输入确认密码', trigger: "blur" }
                    ],
                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                user:{  //用于存储添加弹窗表单的输入数据
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
                tempUser:{},      //暂存对象
                tempRow:{}
            }
        },
        created(){
            // this.$store.dispatch('user/fetchDatas')
            console.log(this.getTime());
            this.getList();
            this.tempUser = this.deepClone(this.user); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading

        },
        computed:{
            userAuthority:{
                get:function getType() {
                    let type = null;
                    switch (parseInt(this.user.uAuthority)) {
                        case 1: type="一级";break
                        case 2: type="二级";break
                        case 3: type="三级";break
                    }
                    return type;
                },
                set: function setType(value) {
                    this.user.uAuthority = value;
                }

            },
        },
        methods:{
            onSelectAll(){
                this.$refs.tb.clearSelection()
            },
            deepClone(obj) {  //深层拷贝
                let _obj = JSON.stringify(obj),
                    objClone = JSON.parse(_obj);
                return objClone;
            },
            showAddForm(){  //点击添加按钮时触发
                this.user = this.deepClone(this.tempUser);
                this.addFormVisible=true;  //显示表单
                this.addOrEdit=true;  //显示添加表单
                // this.user.uId= this.getTime().id;

            },
            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    console.log(this.$refs.tb.selection[0])
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.user = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到user内，作为表单数据
                    this.user.rePassword =  this.deepClone(this.user.uPassword);
                    console.log(this.user.rePassword)
                    this.addOrEdit = false;
                    this.addFormVisible=true; //把弹窗显示出来
                }else{
                    this.$alert('请先选择数据！！', '提示', {
                        confirmButtonText: '确定',
                    });
                }
            },
            exitEdit(){  //修改的时候点击取消的时触发
                this.addFormVisible=false;
            },
            exitAdd(){  //添加的时候点击取消的时触发
                this.addFormVisible=false;
            },
            editUser(formName){  //修改表单的确定按钮触发
                let time = this.getTime().time;
                this.user.uModificationTime = time;
                this.user.uModifierId = this.$store.state.myUser.data.uId;
                this.user.uAuthority = parseInt(this.user.uAuthority);

                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        if (this.user.rePassword == this.user.uPassword){
                            let resp = await editUser(this.user);
                            console.log(resp)
                            if (resp.code == 200) {
                                this.$alert('修改成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        this.getList();
                                    }
                                });
                                this.addFormVisible = false
                            } else {
                                this.$alert('修改失败，数据或服务器出错！', '提示', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        this.getList();
                                    }
                                });
                                this.addFormVisible = false
                            }
                        }else{
                            this.$alert('密码与确认密码不一致，请重新确认！', '提示', {
                                confirmButtonText: '确定',

                            });
                        }
                    } else {
                        this.$alert('表单提交失败，请输入完整表单！', '提示', {
                            confirmButtonText: '确定',
                            callback: action => {
                                this.addFormVisible = false
                            }
                        });

                    }
                });

            },

            submitForm(formName){  //添加表单的触发事件
                let time = this.getTime().time;

                this.user.uCreationTime = time;
                this.user.uModificationTime = time;

                this.user.uCreatorId = this.$store.state.myUser.data.uId
                this.user.uModifierId = this.$store.state.myUser.data.uId;
                this.user.uAuthority = parseInt(this.user.uAuthority);
                if(this.user.uSex == '男'){
                    this.user.uHeadPortrait = 'headPortrait/man.jpg';
                }else{
                    this.user.uHeadPortrait = 'headPortrait/lady.jpeg';
                }
                console.log(this.user)
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        if(this.user.rePassword == this.user.uPassword){
                            let resp = await addUser(this.user)
                            console.log(resp)
                            if(resp.code == 200){
                                this.$alert('添加成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        this.getList();
                                    }
                                });
                                this.addFormVisible = false
                            }else{
                                this.$message.error(resp.msg);
                                this.addFormVisible = false

                            }
                        }
                        else{
                            this.$alert('密码与确认密码不一致，请重新确认！', '提示', {
                                confirmButtonText: '确定',

                            });
                        }

                    } else {
                        this.$message.error('请填写完整带星号的表单内容！');
                        this.addFormVisible = false
                    }
                });
            },

            deleteUser(){  //删除按钮触发
                if (this.$refs.tb.selection.length != 0){

                    this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        let result = await deleteUser(this.$refs.tb.selection[0].uId);
                        this.getList();
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }else{
                    this.$alert('请先选择数据！！', '提示', {
                        confirmButtonText: '确定',
                    });
                }
            },


            handSelectionChange(row){   //这里用于把表单的多选功能改成单选
                this.checkedrow = row[0];
                if (row.length > 1) {
                    this.$refs.tb.clearSelection();
                    this.$refs.tb.toggleRowSelection(row.pop());
                }
            },
            async getList(){    //async与await必须要加上，否则根据js的异步语法规则会先运行axios之后的内容，完了之后才会执行axios中的内容
                //一般这里会有一个axios请求
                this.wait = true;
                this.tableData = await getAllUser();
                this.wait = false;
                // this.tableData = this.$store.state.user.data;
                console.log(this.tableData)
                this.handleCurrentChange(this.currentPage)
                this.stashList = this.tableData  //暂存数组  当搜索为空时候  数组展示所有数据
            },
            handleSizeChange(val) {  //改变页面展示数量的时候触发
                this.pagesize = val
                this.tableList()
            },
            handleCurrentChange(val) { //改变页数的时候，触发
                this.currentPage = val
                this.tableList()
            },
            tableList() { //获取当前页的所有数据，存进paginationData
                this.paginationData = []  //分页数组  tableData 所有的数据
                for (let j = this.pagesize * (this.currentPage - 1);j < this.pagesize * this.currentPage; j++) {
                    if (this.tableData[j]) {
                        this.paginationData.push(this.tableData[j])
                    }
                }
                this.total = this.tableData.length
                console.log(this.paginationData)
            },
            search(){
                if(this.searchContent){
                    this.tableData = this.tableData.filter(item => {
                        return item.uId.includes(this.searchContent)
                    })
                }else{
                    this.tableData = this.stashList
                }
                this.tableList()
            },
            getTime(){  //获取当前时间
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
                let num = Math.floor(Math.random()*10).toString();
                obj.id = parseInt(year+month+day+hour+min+num);
                return obj

            }

        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="uCreationTime"|| self.value=="uModificationTime"){
                            let d = new Date(newValue);
                            let month = d.getMonth()+1;
                            if(month<"10"){
                                month = "0"+month;
                            }
                            newValue=d.getFullYear() + '-' + month + '-' + d.getDate();
                        }
                        console.log(newValue)
                        //这里要从暂存的所有数据中过滤 放到展示的数组中
                        self.tableData = self.stashList.filter(item => {

                            return item[self.value].toString().includes(newValue)
                        })

                    }else{
                        self.tableData = self.stashList
                    }
                    self.tableList()
                },
                deep:true
            },
            value:{
                handler(){
                    this.watchSearch=""
                }
            }
        },
        filters:{
            ctype(ctypes){
                let str
                switch (ctypes.toString()) {
                    case "1": str="一级"; break;
                    case "2": str="二级"; break;
                    case "3": str="三级"; break;
                    default: str="--";
                }
                return str;
            }
        }
    }
</script>

<style scoped>
    .content {


    }
    .content .el-row{
        display: flex;
    }
    .content .el-row .el-button {
        color: #4e4f4f;
        justify-content: left;
        background-color: #e6e7ef;
        border-color: #e6e7ef;
        margin-bottom: 15px;
        font-weight: bold;
    }
    .content .el-row .el-button:hover {
        background-color: #cfd0d8;
        border-color: #cfd0d8;
    }
    .slt .el-input{
        width: 88%;


    }
    .slt .el-select{
        width: 12%;
    }

</style>