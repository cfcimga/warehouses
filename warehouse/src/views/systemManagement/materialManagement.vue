<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='mtModificationTime'| value=='mtCreationTime'">
                </el-date-picker>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='mtType'" style="width: 88%">
                    <el-option label="办公类" value= 1></el-option>
                    <el-option label="劳保类" value= 2></el-option>
                    <el-option label="工具类" value= 3></el-option>
                    <el-option label="家电类" value= 4></el-option>
                    <el-option label="公告宣传类" value= 5></el-option>
                    <el-option label="水暖器材类" value= 6></el-option>
                    <el-option label="钢材类" value= 7></el-option>
                    <el-option label="电子元件类" value= 8></el-option>
                </el-select>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='mtSize'" style="width: 88%">
                    <el-option label="特小型" value= 1></el-option>
                    <el-option label="小型" value= 2></el-option>
                    <el-option label="中型" value= 3></el-option>
                    <el-option label="大型" value= 4></el-option>
                    <el-option label="特大型" value= 5></el-option>
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

            <el-row v-if="$store.state.myUser.data.uAuthority<=2">
                <el-button type="primary" @click="showAddForm"><i class="el-icon-upload el-icon--right"></i>添加</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="showEditForm">修改</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="deleteMaterial">删除</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog :title="addOrEdit? '添加物料':'修改物料'" :visible.sync="addFormVisible"  >
                    <el-form :rules="rules" :model="material" ref="material" class="demo-ruleForm" >
                        <el-form-item label="物料编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="material.mtId"></el-input>
                        </el-form-item>
                        <el-form-item label="物料名称" label-width="120px" prop="mtName">
                            <el-input   v-model="material.mtName" oninput="if (value.length>50) value= value.slice(0,50) "></el-input>
                        </el-form-item>
                        <el-form-item label="物料类型" label-width="120px" prop="mtType" style="text-align: left;">
                            <el-select v-model="mType" placeholder="请选择类型" style="width: 100%">
                                <el-option label="办公类" value= 1></el-option>
                                <el-option label="劳保类" value= 2></el-option>
                                <el-option label="工具类" value= 3></el-option>
                                <el-option label="家电类" value= 4></el-option>
                                <el-option label="公告宣传类" value= 5></el-option>
                                <el-option label="水暖器材类" value= 6></el-option>
                                <el-option label="钢材类" value= 7></el-option>
                                <el-option label="电子元件类" value= 8></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="物料尺寸" label-width="120px" prop="mtSize" style="text-align: left">
                            <el-select v-model="mtsize" placeholder="请选择尺寸" style="width: 100%">
                                <el-option label="特小型" value= 1></el-option>
                                <el-option label="小型" value= 2></el-option>
                                <el-option label="中型" value= 3></el-option>
                                <el-option label="大型" value= 4></el-option>
                                <el-option label="特大型" value= 5></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="mtComment">
                            <el-input type="textarea" placeholder="请输入备注"   v-model="material.mtComment" oninput="if (value.length>100) value= value.slice(0,100) "></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addOrEdit ? exitAdd() : exitEdit()">取 消</el-button>
                        <el-button type="primary" @click="addOrEdit ? submitForm('material') : editMaterial('material')" >确 定</el-button>
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
                    style="width: 100%"
                    @select-all="onSelectAll"
                    :default-sort = "{prop: 'mtId', order: 'descending'}">
                <el-table-column
                        type="selection"
                        label="显示编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="mtId"
                        label="物料编号"
                        width="180"
                        fixed="left"
                        sortable>
                </el-table-column>
                <el-table-column
                        prop="mtName"
                        label="物料名称"
                        show-overflow-tooltip
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="mtType"
                        label="物料类型"
                        sortable
                        width="180">
                    <template slot-scope="scope">
                        {{scope.row.mtType | ctype}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="mtSize"
                        label="物料尺寸"
                        sortable
                        width="180">
                    <template slot-scope="scope">
                        {{scope.row.mtSize | mSize}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="mtComment"
                        label="备注"
                        sortable
                        show-overflow-tooltip
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.mtComment || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="mtCreatorId"
                        label="创建人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="mtCreationTime"
                        label="创建时间"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="mtModifierId"
                        label="最后修改人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="mtModificationTime"
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
    import axios from 'axios'
    import {getAllMaterial,addMaterial,editMaterial,deleteMaterial} from '@/service/materialService.js'
    import {getStockByMtId} from '@/service/stockService.js'
    import loading from '@/components/loading.vue'

    export default {
        name: "materialManagement",
        data(){
            return {
                componentKey:false,
                routeName:{parentName:'系统管理',name:'物料管理',needSlot:true},
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
                    {key:"物料编号",value:"mtId"},
                    {key:"物料名称",value:"mtName"},
                    {key:"物料尺寸",value:"mtSize"},
                    {key:"物料类型",value:"mtType"},
                    {key:"备注",value:"mtComment"},
                    {key:"创建人编号",value:"mtCreatorId"},
                    {key:"最后修改人编号",value:"mtModifierId"},
                    {key:"创建时间",value:"mtCreationTime"},
                    {key:"最后修改时间",value:"mtModificationTime"},
                ],
                rules: {
                    mtName: [
                        { required: true, message: '请输入承运商公司名称', trigger: 'blur' },
                    ],
                    mtType: [
                        { required: true, message: '请输入联系人电话', trigger: 'blur' }
                    ],
                    mtSize: [
                        { required: true, message: '请输入联系人名称', trigger: 'blur' }
                    ],
                    mtComment: [
                        { required: false, message: '请输入备注', trigger: 'blur' }
                    ],
                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                material:{  //用于存储添加弹窗表单的输入数据
                    mtId:null,
                    mtName:null,
                    mtType:null,
                    mtSize:null,
                    mtComment:null,
                    mtCreatorId:null,
                    mtCreationTime:null,
                    mtModifierId:null,
                    mtModificationTime:null
                },
                tempMaterial:{},      //暂存对象
                tempRow:{}
            }
        },
        created(){
            console.log(this.getTime());
            this.getList();
            this.tempMaterial = this.deepClone(this.material); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading

        },
        computed:{
            mType:{
                get:function getType() {
                    let type = null;
                    switch (parseInt(this.material.mtType)) {
                        case 1: type="办公类";break
                        case 2: type="劳保类";break
                        case 3: type="工具类";break
                        case 4: type="家电类";break
                        case 5: type="公告宣传类";break
                        case 6: type="水暖器材类";break
                        case 7: type="钢材类";break
                        case 8: type="电子元件";break
                    }
                    return type;
                },
                set: function setType(value) {
                    this.material.mtType = value;
                }

            },
            mtsize:{
                get:function getSize(){
                    let str = null;
                    switch (parseInt(this.material.mtSize)) {
                        case 1: str="特小型"; break;
                        case 2: str="小型"; break;
                        case 3: str="中型"; break;
                        case 4: str="大型"; break;
                        case 5: str="特大型"; break;
                    }
                    return str;
                },
                set:function setSize(value) {
                    this.material.mtSize = value;
                }

            }
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
                this.material = this.deepClone(this.tempMaterial);//把material对象置空
                this.material.mtId= this.getTime().id;
                this.addOrEdit=true;  //显示添加表单
                this.addFormVisible=true;  //显示表单

            },
            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.material = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
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
            editMaterial(formName){  //修改表单的确定按钮触发
                let time = this.getTime().time;
                this.material.mtModificationTime = time;
                this.material.mtType = parseInt(this.material.mtType)
                this.material.mtSize = parseInt(this.material.mtSize)
                this.material.mtModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let re = await getStockByMtId(this.$refs.tb.selection[0].mtId)
                        if(re.code==400){
                            let resp = await editMaterial(this.material);
                            console.log(resp)
                            if(resp.code == 200) {
                                this.$message({
                                    type: 'success',
                                    message: '修改成功!'
                                });
                                this.getList()
                            }else {
                                this.$message({
                                    type: 'error',
                                    message: '服务器出错，修改失败!'
                                });
                            }
                        }else{
                                this.$message({
                                    type: 'error',
                                    message: '该物料在已经在仓库内有了库存，无法对其进行修改！'
                                });
                            }
                            this.addFormVisible = false

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

                this.material.mtCreationTime = time;
                this.material.mtModificationTime = time;
                this.material.mtType = parseInt(this.material.mtType)
                this.material.mtSize = parseInt(this.material.mtSize)
                this.material.mtCreatorId = this.$store.state.myUser.data.uId
                this.material.mtModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await addMaterial(this.material)
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
                            this.$alert('添加失败，数据或服务器出错！', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    this.addFormVisible = false
                                }
                            });
                        }
                    } else {
                        console.log('error submit!!');
                        this.addFormVisible = false
                        return false
                    }
                });
            },

            deleteMaterial(){  //删除按钮触发
                if (this.$refs.tb.selection.length != 0){

                    this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        let re = await getStockByMtId(this.$refs.tb.selection[0].mtId)
                        if(re.code==400){ //根据mtId在库存表无法找到该物料
                            let result = await deleteMaterial(this.$refs.tb.selection[0].mtId);
                            this.getList();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }else{
                            this.$message({
                                type: 'error',
                                message: '该物料在已经在仓库内有了库存，无法将其进行删除！'
                            });
                        }

                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '删除失败'
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
                this.tableData = await getAllMaterial();
                this.wait = false;
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
                        return item.mtId.includes(this.searchContent)
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
                obj.id = "mt"+year+month+day+hour+min+second+num;
                return obj

            }

        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="mtCreationTime"|| self.value=="mtModificationTime"){
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
                    case "1": str="办公类"; break;
                    case "2": str="劳保类"; break;
                    case "3": str="工具类"; break;
                    case "4": str="家电类"; break;
                    case "5": str="公告宣传类"; break;
                    case "6": str="水暖器材类"; break;
                    case "7": str="钢材类"; break;
                    case "8": str="电子元件类"; break;

                    default: str="--";
                }
                return str;
            },
            mSize(size){
                let str
                switch (size.toString()) {
                    case "1": str="特小型"; break;
                    case "2": str="小型"; break;
                    case "3": str="中型"; break;
                    case "4": str="大型"; break;
                    case "5": str="特大型"; break;


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