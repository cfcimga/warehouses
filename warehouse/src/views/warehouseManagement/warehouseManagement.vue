<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='whModificationTime'| value=='whCreationTime'">
                </el-date-picker>
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
        <loading v-if="$store.state.warehouse.isLoading==true"></loading>
        <div class="content" v-else>

            <el-row v-if="$store.state.myUser.data.uAuthority<2">
                <el-button type="primary" @click="showAddForm"><i class="el-icon-upload el-icon--right"></i>添加</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="showEditForm">修改</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="deleteWarehouse()">删除</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog :title="addOrEdit? '添加仓库':'修改仓库'"  :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="warehouse" ref="warehouse" class="demo-ruleForm">
                        <el-form-item label="仓库编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="warehouse.whId"></el-input>
                        </el-form-item>
                        <el-form-item label="仓库名称" label-width="120px" prop="whName">
                            <el-input   v-model="warehouse.whName" oninput="if (value.length>50) value= value.slice(0,50) "></el-input>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="whComment">
                            <el-input type="textarea" placeholder="请输入备注"   v-model="warehouse.whComment" oninput="if (value.length>100) value= value.slice(0,100) "></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addOrEdit ? exitAdd() : exitEdit()">取 消</el-button>
                        <el-button type="primary" @click="addOrEdit ? submitForm('warehouse') : editWarehouse('warehouse')" >确 定</el-button>
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
                    :default-sort = "{prop: 'whId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="whId"
                        label="仓库编号"
                        width="180"
                        fixed="left"
                        sortable>
                </el-table-column>
                <el-table-column
                        prop="whName"
                        label="仓库名称"
                        show-overflow-tooltip
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="whComment"
                        label="备注"
                        show-overflow-tooltip
                        sortable
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.whComment || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="whCreatorId"
                        label="创建人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="whCreationTime"
                        label="创建时间"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="whModifierId"
                        label="最后修改人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="whModificationTime"
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
    import {getAllWarehouse,addWarehouse,editWarehouse,deleteWarehouse} from '@/service/warehouseService.js'
    import loading from '@/components/loading.vue'
    import {getReservoirareaByWhId} from '@/service/reservoirareaService.js'

    export default {
        name: "warehouseManagement",
        data(){
            return {
                routeName:{parentName:'仓库管理',name:'仓库管理',needSlot:true},
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
                selection:[
                    {key:"仓库编号",value:"whId"},
                    {key:"仓库名称",value:"whName"},
                    {key:"备注",value:"whComment"},
                    {key:"创建人编号",value:"whCreatorId"},
                    {key:"最后修改人编号",value:"whModifierId"},
                    {key:"创建时间",value:"whCreationTime"},
                    {key:"最后修改时间",value:"whModificationTime"},
                ],
                rules: {
                    whName: [
                        { required: true, message: '请输入仓库名称', trigger: 'blur' },
                    ],
                    cComment: [
                        { required: false, message: '请输入备注', trigger: 'blur' }
                    ],
                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                warehouse:{  //用于存储添加弹窗表单的输入数据
                    whId:null,
                    whName:null,
                    whComment:null,
                    whCreatorId:null,
                    whCreationTime:null,
                    whModifierId:null,
                    whModificationTime:null
                },
                tempWarehouse:{},      //暂存对象
                tempRow:{}
            }
        },
        async created(){
            await this.$store.dispatch("warehouse/fetchDatas")
            console.log(this.getTime());
            this.getList();
            this.tempWarehouse = this.deepClone(this.warehouse); //先存好warehouse空对象
        },
        components:{
            mainLayout,
            loading

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
                this.warehouse = this.deepClone(this.tempWarehouse);//把carrier的内容初始化
                this.addFormVisible=true;  //显示表单
                this.addOrEdit=true;  //显示添加表单
                this.warehouse.whId= this.getTime().id;

            },
            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.warehouse = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
                    this.addOrEdit = false;
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
            editWarehouse(formName){  //修改表单的确定按钮触发
                let time = this.getTime().time;
                this.warehouse.whModificationTime = time;
                this.warehouse.whModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await editWarehouse(this.warehouse);
                        console.log(resp)
                        if(resp.code == 200){
                            this.$store.dispatch("warehouse/fetchDatas")
                            this.$alert('修改成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    this.getList();
                                }
                            });
                            this.addFormVisible = false
                        }else{
                            this.$message.error('修改失败，数据或服务器出错！！');
                            this.addFormVisible = false
                        }
                    } else {
                        this.$message.error('表单提交失败，请输入完整表单！');
                        this.addFormVisible = false
                    }
                });

            },
            submitForm(formName){  //添加表单的触发事件
                let time = this.getTime().time;

                this.warehouse.whCreationTime = time;
                this.warehouse.whModificationTime = time;
                this.warehouse.whCreatorId = this.$store.state.myUser.data.uId
                this.warehouse.whModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await addWarehouse(this.warehouse)
                        console.log(resp)
                        if(resp.code == 200){
                            this.$store.dispatch("warehouse/fetchDatas")
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

            deleteWarehouse(){  //删除按钮触发
                if (this.$refs.tb.selection.length != 0){
                    this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        let resp = await getReservoirareaByWhId(this.$refs.tb.selection[0].whId)
                        console.log(resp)
                        if(resp.code==400){
                            let result = await deleteWarehouse(this.$refs.tb.selection[0].whId)
                            await this.$store.dispatch("warehouse/fetchDatas")

                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            this.getList();
                        }else{
                            this.$message.error('该仓库已经有库区，不能直接删除！');
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
                this.tableData = this.$store.state.warehouse.data;
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
                obj.id = "wh"+year+month+day+hour+min+second+num;
                return obj

            },
        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="whCreationTime"|| self.value=="whModificationTime"){
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