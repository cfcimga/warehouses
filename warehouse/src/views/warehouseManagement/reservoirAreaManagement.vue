<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='raModificationTime'| value=='raCreationTime'">
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
        <loading v-if="$store.state.reservoirarea.isLoading==true"></loading>
        <div class="content" v-else>

            <el-row v-if="$store.state.myUser.data.uAuthority<2">
                <el-button type="primary" @click="showAddForm" v-if="$store.state.myUser.data.uAuthority<=2"><i class="el-icon-upload el-icon--right"></i>添加</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="showEditForm" v-if="$store.state.myUser.data.uAuthority<=2">修改</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="deleteReservoirarea()" v-if="$store.state.myUser.data.uAuthority<=2">删除</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="show()">显示编号/名称</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog :title="addOrEdit? '添加库区':'修改库区'"  :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="reservoirarea" ref="reservoirarea" class="demo-ruleForm">
                        <el-form-item label="库区编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="reservoirarea.raId"></el-input>
                        </el-form-item>
                        <el-form-item label="库区名称" label-width="120px" prop="raName">
                            <el-input   v-model="reservoirarea.raName" oninput="if (value.length>50) value= value.slice(0,50) "></el-input>
                        </el-form-item>
                        <el-form-item label="类型" label-width="120px" prop="raType" style="text-align: left">
                            <el-select v-model="raaType" placeholder="请选择类型" style="width: 100%" v-if="disableFlag">
                                <el-option label="办公类" value= 1></el-option>
                                <el-option label="劳保类" value= 2></el-option>
                                <el-option label="工具类" value= 3></el-option>
                                <el-option label="家电类" value= 4></el-option>
                                <el-option label="公告宣传类" value= 5></el-option>
                                <el-option label="水暖器材类" value= 6></el-option>
                                <el-option label="钢材类" value= 7></el-option>
                                <el-option label="电子元件类" value= 8></el-option>
                            </el-select>
                            <el-select v-model="reservoirarea.place" placeholder="有货架的库区不能修改库区类型" v-else disabled="" style="width: 100%">

                            </el-select>
                        </el-form-item>
                        <el-form-item label="所属仓库" label-width="120px" prop="raWhId" style="text-align: left">
                            <el-select v-model="raWarehouse" placeholder="请选择所属仓库" style="width: 100%" v-if="disableFlag">
                                <el-option
                                    v-for="item in $store.state.warehouse.data"
                                    :key="item.whId"
                                    :label="item.whName"
                                    :value="item.whId">
                                </el-option>
                            </el-select>
                            <el-select v-model="reservoirarea.place" placeholder="有货架的库区不能修改所属仓库" v-else disabled="" style="width: 100%">

                            </el-select>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="raComment">
                            <el-input type="textarea" placeholder="请输入备注"   v-model="reservoirarea.raComment" oninput="if (value.length>100) value= value.slice(0,100) "></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addOrEdit ? exitAdd() : exitEdit()">取 消</el-button>
                        <el-button type="primary" @click="addOrEdit ? submitForm('reservoirarea') : editReservoirarea('reservoirarea')" >确 定</el-button>
                    </div>
                </el-dialog>

            </el-row>

            <el-table
                    :data="paginationData"
                    max-height="540px"
                    ref="tb"
                    :cell-style="{'text-align':'center'}"
                    @selection-change="handSelectionChange"
                    border
                    @select-all="onSelectAll"
                    :default-sort = "{prop: 'raId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="raId"
                        label="库区编号"
                        width="180"
                        fixed="left"
                        sortable>
                </el-table-column>
                <el-table-column
                        prop="raName"
                        label="库区名称"
                        show-overflow-tooltip
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="raType"
                        label="类型"
                        sortable
                        width="180">
                    <template slot-scope="scope">
                        {{scope.row.raType | rtype}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="raWhId"
                        label="所属仓库"
                        show-overflow-tooltip
                        sortable
                        v-if="showIdFlag==false"
                        :key="1"
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.raWhName}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="raWhId"
                        label="所属仓库编号"
                        sortable
                        :key="2"
                        v-else
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="raComment"
                        label="备注"
                        show-overflow-tooltip
                        sortable
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.raComment || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="raCreatorId"
                        label="创建人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="raCreationTime"
                        label="创建时间"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="raModifierId"
                        label="最后修改人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="raModificationTime"
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
    import {getAllReservoirarea,addReservoirarea,editReservoirarea,deleteReservoirarea} from '@/service/reservoirareaService.js'
    import loading from '@/components/loading.vue'
    import {getStoragerackBySrRaId} from '@/service/storagerackService.js'

    export default {
        name: "reservoirAreaManagement",
        data(){
            return {
                routeName:{parentName:'仓库管理',name:'库区管理',needSlot:true},
                tableData:null,

                total: 0, //数组总数
                currentPage: 1, //当前页
                paginationData: [], //分页数组,用于存储每页所拥有的数据
                pagesize: 10, //每页数据
                searchContent: "",//搜索内容
                stashList: [],
                watchSearch: "",
                showIdFlag:false,
                disableFlag:null,
                checkedrow:[],
                addFormVisible: false,
                editFormVisible: false,
                value:"",
                selection:[
                    {key:"库区编号",value:"raId"},
                    {key:"库区名称",value:"raName"},
                    {key:"所属仓库",value:"raWhIde"},
                    {key:"类型",value:"raType"},
                    {key:"备注",value:"raComment"},
                    {key:"创建人编号",value:"raCreatorId"},
                    {key:"最后修改人编号",value:"raModifierId"},
                    {key:"创建时间",value:"raCreationTime"},
                    {key:"最后修改时间",value:"raModificationTime"},
                ],
                rules: {
                    raName: [
                        { required: true, message: '请输入库区名称', trigger: 'blur' },
                    ],
                    raWhId: [
                        { required: true, message: '请选择所属仓库', trigger: 'blur' }
                    ],
                    raComment: [
                        { required: false, message: '请输入备注', trigger: 'blur' }
                    ],
                    raType: [
                        { required: true, message: '请选择类型', trigger: 'blur' }
                    ],
                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                reservoirarea:{  //用于存储添加弹窗表单的输入数据
                    raId:null,
                    raName:null,
                    raWhId:null,
                    raWhName:null,
                    raType:null,
                    raComment:null,
                    raCreatorId:null,
                    raCreationTime:null,
                    raModifierId:null,
                    raModificationTime:null,
                    place:null
                },
                tempReservoirarea:{},      //暂存对象
                tempRow:{}
            }
        },
        async created(){
            await this.$store.dispatch("reservoirarea/fetchDatas")
            console.log(this.getTime());
            this.getList();
            this.tempReservoirarea = this.deepClone(this.reservoirarea); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading

        },
        computed:{

            raaType:{
                get:function getType() {
                    let type = null;
                    switch (parseInt(this.reservoirarea.raType)) {
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
                    this.reservoirarea.raType = value;
                }
            },
            raWarehouse:{
                get:function getWarehouse() {
                    if(this.reservoirarea.raWhId){
                        let whouses = this.$store.state.warehouse.data;
                        let whouse =whouses.filter(value=>{
                            let flag = value.whId == this.reservoirarea.raWhId
                            return flag
                        })
                        return whouse[0].whName;
                    }else{
                        return null;
                    }
                },
                set: function setType(value) {
                    this.reservoirarea.raWhId = value;
                    this.reservoirarea.raWhName = this.raWarehouse;
                }
            }
        },
        methods:{
            show(){
                this.showIdFlag = !this.showIdFlag;
            },
            onSelectAll(){
                this.$refs.tb.clearSelection()
            },
            deepClone(obj) {  //深层拷贝
                let _obj = JSON.stringify(obj),
                    objClone = JSON.parse(_obj);
                return objClone;
            },
            showAddForm(){  //点击添加按钮时触发
                this.reservoirarea = this.deepClone(this.tempReservoirarea);//把carrier的内容初始化
                this.addFormVisible=true;  //显示表单
                this.addOrEdit=true;  //显示添加表单
                this.reservoirarea.raId= this.getTime().id;
                this.disableFlag = true

            },
            async showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.reservoirarea = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
                    this.addOrEdit = false;
                    let resp = await getStoragerackBySrRaId(this.$refs.tb.selection[0].raId)
                    if(resp.code==200){
                        this.disableFlag=false
                    }else{
                        this.disableFlag=true
                    }
                    console.log(this.reservoirarea.raWhId)
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
            editReservoirarea(formName){  //修改表单的确定按钮触发
                let time = this.getTime().time;
                this.reservoirarea.raModificationTime = time;
                this.reservoirarea.raType = parseInt(this.reservoirarea.raType)
                this.reservoirarea.raModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await editReservoirarea(this.reservoirarea);
                        console.log(resp)
                        if(resp.code == 200){
                            this.$store.dispatch("reservoirarea/fetchDatas")
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

                this.reservoirarea.raCreationTime = time;
                this.reservoirarea.raModificationTime = time;
                this.reservoirarea.raType = parseInt(this.reservoirarea.raType)
                this.reservoirarea.raCreatorId = this.$store.state.myUser.data.uId
                this.reservoirarea.raModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await addReservoirarea(this.reservoirarea)
                        console.log(resp)
                        if(resp.code == 200){
                            this.$store.dispatch("reservoirarea/fetchDatas")
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

            deleteReservoirarea(){  //删除按钮触发
                if (this.$refs.tb.selection.length != 0){
                    this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        let resp = await getStoragerackBySrRaId(this.$refs.tb.selection[0].raId)
                        console.log(resp)
                        if (resp.code==400){
                            let result = await deleteReservoirarea(this.$refs.tb.selection[0].raId)
                            console.log(result)
                            this.$store.dispatch("reservoirarea/fetchDatas")
                            this.getList();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }else{
                            this.$message.error('该库区已经有货架了，不能直接删除！');
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

                this.tableData = this.$store.state.reservoirarea.data;
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
                obj.id = "c"+year+month+day+hour+min+second+num;
                return obj

            },
        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="raCreationTime"|| self.value=="raModificationTime"){
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
            rtype(ctypes){
                let str
                switch (parseInt(ctypes)) {
                    case 1: str="办公类"; break;
                    case 2: str="劳保类"; break;
                    case 3: str="工具类"; break;
                    case 4: str="家电类"; break;
                    case 5: str="公告宣传类"; break;
                    case 6: str="水暖器材类"; break;
                    case 7: str="钢材类"; break;
                    case 8: str="电子元件类"; break;

                    default: str="--";
                }
                return str;
            },
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