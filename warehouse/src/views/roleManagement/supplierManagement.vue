<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='splModificationTime'| value=='splCreationTime'">
                </el-date-picker>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='splType'" style="width: 88%">
                    <el-option label="一级" value= 1></el-option>
                    <el-option label="二级" value= 2></el-option>
                    <el-option label="三级" value= 3></el-option>
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
        <loading v-if="waiting==true"></loading>
        <div class="content" v-else>

            <el-row v-if="$store.state.myUser.data.uAuthority<=2">
                <el-button type="primary" @click="showAddForm"><i class="el-icon-upload el-icon--right"></i>添加</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="showEditForm">修改</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="deleteSupplier()">删除</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog :title="addOrEdit ? '添加供应商' : '修改供应商'" :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="supplier" ref="supplier" class="demo-ruleForm">
                        <el-form-item label="供应商编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="supplier.splId"></el-input>
                        </el-form-item>
                        <el-form-item label="供应商名称" label-width="120px" prop="splName">
                            <el-input   v-model="supplier.splName" oninput="if (value.length>50) value= value.slice(0,50) "></el-input>
                        </el-form-item>
                        <el-form-item label="联系人电话" label-width="120px" prop="splPhone">
                            <el-input   v-model="supplier.splPhone" oninput="if (value.length>11) value= value.slice(0,11) "
                                        onkeyup="this.value=this.value.replace(/\D/g,'')"
                                        onafterpaste="this.value=this.value.replace(/\D/g,'')"></el-input>
                        </el-form-item>
                        <el-form-item label="联系人名称" label-width="120px" prop="splLinkman">
                            <el-input   v-model="supplier.splLinkman" oninput="if (value.length>25) value= value.slice(0,25) "></el-input>
                        </el-form-item>
                        <el-form-item label="等级" label-width="120px" prop="splType" style="text-align: left">
                            <el-select v-model="spllType" placeholder="请选择等级" style="width: 100%">
                                <el-option label="一级" value= 1></el-option>
                                <el-option label="二级" value= 2></el-option>
                                <el-option label="三级" value= 3></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="splComment">
                            <el-input type="textarea" placeholder="请输入备注"   v-model="supplier.splComment" oninput="if (value.length>100) value= value.slice(0,100) "></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addOrEdit ? exitAdd() : exitEdit()">取 消</el-button>
                        <el-button type="primary" @click="addOrEdit ? submitForm('supplier') : editSupplier('supplier')" >确 定</el-button>
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
                    :default-sort = "{prop: 'splId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="splId"
                        label="供应商编号"
                        width="180"
                        sortable
                        fixed="left">
                </el-table-column>
                <el-table-column
                        prop="splName"
                        label="公司名称"
                        show-overflow-tooltip
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="splPhone"
                        label="联系电话"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="splLinkman"
                        label="联系人"
                        sortable
                        width="180">
                </el-table-column>

                <el-table-column
                        prop="splType"
                        label="等级"
                        sortable
                        width="180">

                    <template slot-scope="scope">
                        {{scope.row.splType | ctype}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="splComment"
                        label="备注"
                        sortable
                        show-overflow-tooltip
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.splComment || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="splCreatorId"
                        label="创建人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="splCreationTime"
                        label="创建时间"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="splModifierId"
                        label="最后修改人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="splModificationTime"
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
    import {getAllSupplier,addSupplier,editSupplier,deleteSupplier} from '@/service/supplierService.js'
    import loading from '@/components/loading.vue'
    import {getGrnBySplId} from '@/service/goodsreceivednoteService'

    export default {
        name: "supplierManagement",
        data(){
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
                routeName:{parentName:'角色管理',name:'供应商管理',needSlot:true},
                tableData:null,
                total: 0, //数组总数
                currentPage: 1, //当前页
                paginationData: [], //分页数组,用于存储每页所拥有的数据
                pagesize: 10, //每页数据
                searchContent: "",//搜索内容
                stashList: [],
                waiting:false,
                watchSearch: "",
                checkedrow:[],
                addFormVisible: false,
                editFormVisible: false,
                value:"",
                selection:[
                    {key:"供应商编号",value:"splId"},
                    {key:"供应商公司名称",value:"splName"},
                    {key:"联系人电话",value:"splPhone"},
                    {key:"联系人姓名",value:"splLinkman"},
                    {key:"等级",value:"splType"},
                    {key:"备注",value:"splComment"},
                    {key:"创建人编号",value:"splCreatorId"},
                    {key:"最后修改人编号",value:"splModifierId"},
                    {key:"创建时间",value:"splCreationTime"},
                    {key:"最后修改时间",value:"splModificationTime"},
                ],
                rules: {
                    splName: [
                        { required: true, message: '请输入供应商公司名称', trigger: 'blur' },
                    ],
                    splPhone: [
                        { required: true, message: '请输入联系人电话', trigger: 'blur' },
                        { min: 11, max: 11, message: "电话长度在 11位", trigger: "blur" },
                        {validator:checkPhone,message:"请输入正确的电话号码",trigger:"blur"}

                    ],
                    splLinkman: [
                        { required: true, message: '请输入联系人名称', trigger: 'blur' }
                    ],
                    splComment: [
                        { required: false, message: '请输入备注', trigger: 'blur' }
                    ],
                    splType: [
                        { required: true, message: '请选择等级', trigger: 'blur' }
                    ],
                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                supplier:{  //用于存储添加弹窗表单的输入数据
                    splId:null,
                    splName:null,
                    splPhone:null,
                    splLinkman:null,
                    splType:null,
                    splComment:null,
                    splCreatorId:null,
                    splCreationTime:null,
                    splModifierId:null,
                    splModificationTime:null
                },
                tempSupplier:{},      //暂存对象
                tempRow:{}
            }
        },
        created(){
            console.log(this.getTime());
            this.getList();
            this.tempSupplier = this.deepClone(this.supplier); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading

        },
        computed:{
            spllType:{
                get:function getType() {
                    let type = null;
                    switch (parseInt(this.supplier.splType)) {
                        case 1: type="一级";break
                        case 2: type="二级";break
                        case 3: type="三级";break
                    }
                    return type;
                },
                set: function setType(value) {
                    this.supplier.splType = value;
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
                this.supplier = this.deepClone(this.tempSupplier);
                this.supplier.splId= this.getTime().id;
                this.addFormVisible=true;  //显示表单
                this.addOrEdit=true;  //显示添加表单

            },
            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.supplier = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
                    this.addOrEdit = false;
                    console.log(this.supplier)
                    this.addFormVisible=true; //把弹窗显示出来
                }else{
                    this.$message.error('请先选择数据！！');
                }
            },
            exitEdit(){  //修改的时候点击取消的时触发
                this.addFormVisible=false;
            },
            exitAdd(){  //添加的时候点击取消的时触发
                this.addFormVisible=false;
            },
            editSupplier(formName){  //修改表单的确定按钮触发
                let time = this.getTime().time;
                this.supplier.splModificationTime = time;
                this.supplier.splModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let re = await getGrnBySplId(this.$refs.tb.selection[0].splId)
                        if(re.code==400){
                            let resp = await editSupplier(this.supplier);
                            console.log(resp)
                            if(resp.code == 200){
                                this.$alert('修改成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        this.getList();
                                    }
                                });
                                this.addFormVisible = false
                            }else{
                                this.$message.error('修改失败，数据或服务器出错！');
                                this.addFormVisible = false
                            }

                        }else{
                            this.$message({
                                type: 'error',
                                message: '修改失败，已经有未完成的入库单引用该供应商，不能对其进行修改!'
                            });
                        }

                    } else {
                        this.$message.error('表单提交失败，请输入完整表单！');
                        this.addFormVisible = false
                    }
                });

            },
            submitForm(formName){  //添加表单的触发事件
                let time = this.getTime().time;

                this.supplier.splCreationTime = time;
                this.supplier.splModificationTime = time;
                this.supplier.splCreatorId = this.$store.state.myUser.data.uId
                this.supplier.splModifierId = this.$store.state.myUser.data.uId;
                console.log(this.supplier)
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await addSupplier(this.supplier)
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
                            this.$message.error('添加失败，数据或服务器出错！');
                            this.addFormVisible = false
                        }
                    } else {
                        this.$message.error('表单提交失败，请输入完整表单！');
                        this.addFormVisible = false
                    }
                });
            },

            deleteSupplier(){  //删除按钮触发
                if (this.$refs.tb.selection.length != 0){

                    this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        let re = await getGrnBySplId(this.$refs.tb.selection[0].splId)
                        if(re.code==400){
                            let result = await deleteSupplier(this.$refs.tb.selection[0].splId);
                            if(result.code==200){
                                this.getList();
                                this.$message({
                                    type: 'success',
                                    message: '删除成功!'
                                });
                            }else{
                                this.$message({
                                    type: 'error',
                                    message: '服务器出错，请联系管理员！!'
                                });
                            }

                        }else{
                            this.$message({
                                type: 'error',
                                message: '删除失败，已经有未完成的入库单引用该供应商，不能对其进行删除!'
                            });
                        }

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
                this.waiting = true
                this.tableData = await getAllSupplier()
                this.waiting = false
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
                        return item.splId.includes(this.searchContent)
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
                let num = Math.floor(Math.random()*100).toString();
                obj.id = "spl"+year+month+day+hour+min+second+num;
                return obj

            }

        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="splCreationTime"|| self.value=="splModificationTime"){
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