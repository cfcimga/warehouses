<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='srModificationTime'| value=='srCreationTime'">
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
        <loading v-if="$store.state.storagerack.isLoading==true"></loading>
        <div class="content" v-else>

            <el-row>
                <el-button type="primary" @click="showAddForm"><i class="el-icon-upload el-icon--right"></i>添加</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="showEditForm">修改</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="deleteStoragerack()">删除</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="show()">显示编号/名称</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog :title="addOrEdit? '添加货架':'修改货架'"  :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="storagerack" ref="storagerack" class="demo-ruleForm">
                        <el-form-item label="货架编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="storagerack.srId"></el-input>
                        </el-form-item>
                        <el-form-item label="货架名称" label-width="120px" prop="srName">
                            <el-input   v-model="storagerack.srName" oninput="if (value.length>50) value= value.slice(0,50) "></el-input>
                        </el-form-item>
                        <el-form-item label="所属仓库" label-width="120px" prop="srWhId" style="text-align: left;">
                            <el-select v-model="srWarehouse" placeholder="请选择所属仓库" v-if="!storagerack.srMtId" style="width: 100%">
                                <el-option
                                        v-for="item in $store.state.warehouse.data"
                                        :key="item.whId"
                                        :label="item.whName"
                                        :value="item.whId">

                                </el-option>
                            </el-select>
                            <el-select v-model="storagerack.place" placeholder="有物料的货架不能修改所属仓库" v-else disabled="" style="width: 100%">
                            </el-select>
                        </el-form-item>
                        <el-form-item label="所属库区" label-width="120px" prop="srRaId" style="text-align: left" >
                            <el-select v-model="srRervoirarea" placeholder="请选择所属库区" v-if="!storagerack.srMtId" style="width: 100%">
                                <el-option
                                        v-for="item in allReservoirarea"
                                        :key="item.raId"
                                        :label="item.raName"
                                        :value="item.raId">

                                </el-option>
                            </el-select>
                            <el-select v-model="storagerack.place" placeholder="有物料的货架不能修改所属库区" v-else disabled="" style="width: 100%">

                            </el-select>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="srComment">
                            <el-input type="textarea" placeholder="请输入备注"   v-model="storagerack.srComment" oninput="if (value.length>100) value= value.slice(0,100) "></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addOrEdit ? exitAdd() : exitEdit()">取 消</el-button>
                        <el-button type="primary" @click="addOrEdit ? submitForm('storagerack') : editStoragerack('storagerack')" >确 定</el-button>
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
                    :default-sort = "{prop: 'srId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55" >
                </el-table-column>
                <el-table-column
                        prop="srId"
                        label="货架编号"
                        width="180"
                        fixed="left"
                        sortable>
                </el-table-column>
                <el-table-column
                        prop="srName"
                        label="货架名称"
                        show-overflow-tooltip
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="srMtId"
                        label="所存物料"
                        :sortable="true"
                        v-if="showIdFlag==false"
                        show-overflow-tooltip
                        :key="1"
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.srMtName}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="srMtId"
                        label="所存物料编号"
                        :sortable="true"
                        v-else
                        :key="2"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="srWhId"
                        label="所属仓库"
                        sortable
                        show-overflow-tooltip
                        v-if="showIdFlag==false"
                        key="3"
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.srWhName}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="srWhId"
                        label="所属仓库编号"
                        sortable
                        v-else
                        key="4"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="srRaId"
                        label="所属库区"
                        show-overflow-tooltip
                        sortable
                        v-if="showIdFlag==false"
                        key="5"
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.srRaName}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="srRaId"
                        label="所属库区编号"
                        sortable
                        v-else
                        key="6"
                        width="180">

                </el-table-column>
                <el-table-column
                        prop="srComment"
                        label="备注"
                        sortable
                        show-overflow-tooltip
                        key="7"
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.srComment || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="srCreatorId"
                        label="创建人编号"
                        sortable
                        key="8"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="srCreationTime"
                        label="创建时间"
                        sortable
                        key="9"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="srModifierId"
                        label="最后修改人编号"
                        sortable
                        key="10"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="srModificationTime"
                        label="最后修改时间"
                        sortable
                        key="11"
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
    import {getAllStoragerack,addStoragerack,editStoragerack,deleteStoragerack} from '@/service/storagerackService.js'
    import loading from '@/components/loading.vue'
    import {getStockBySrId} from '@/service/stockService.js'
    import {getAllMaterial} from "../../service/materialService";

    export default {
        name: "reservoirAreaManagement",
        data(){
            return {
                routeName:{parentName:'仓库管理',name:'货架管理',needSlot:true},
                showIdFlag:false,
                tableData:null,
                material:null,
                warehouse:null,
                reservoirarea:null,
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
                    {key:"货架编号",value:"srId"},
                    {key:"货架名称",value:"srName"},
                    {key:"所存物料编号",value:"srMtId"},
                    {key:"所属仓库",value:"srWhId"},
                    {key:"所属库区",value:"srRaId"},
                    {key:"备注",value:"srComment"},
                    {key:"创建人编号",value:"srCreatorId"},
                    {key:"最后修改人编号",value:"srModifierId"},
                    {key:"创建时间",value:"srCreationTime"},
                    {key:"最后修改时间",value:"srModificationTime"},
                ],
                rules: {
                    srName: [
                        { required: true, message: '请输入货架名称', trigger: 'blur' },
                    ],
                    srWhId: [
                        { required: true, message: '请选择所属仓库', trigger: 'blur' }
                    ],
                    srRaId: [
                        { required: true, message: '请选择所属货架', trigger: 'blur' }
                    ],
                    srComment: [
                        { required: false, message: '请输入备注', trigger: 'blur' }
                    ]
                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                storagerack:{  //用于存储添加弹窗表单的输入数据
                    srId:null,
                    srName:null,
                    srMtId:null,
                    srMtName:null,
                    srWhId:null,
                    srWhId:null,
                    srRaId:null,
                    srRaName:null,
                    srComment:null,
                    srCreatorId:null,
                    srCreationTime:null,
                    srModifierId:null,
                    srModificationTime:null,
                    place:null
                },
                tempStoragerack:{},      //暂存对象
                tempRow:{}
            }
        },
        async created(){
            await this.$store.dispatch("storagerack/fetchDatas")
            this.material = await getAllMaterial()
            console.log(this.material);
            this.getList();
            this.tempStoragerack = this.deepClone(this.storagerack); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading
        },
        computed:{
            allReservoirarea:{
                get:function getAllRa(){
                    if(this.$store.state.reservoirarea.data){
                        let ra = this.$store.state.reservoirarea.data.filter(value => {
                            return this.storagerack.srWhId == value.raWhId
                        })
                        return ra
                    }else{
                        return null
                    }

                }
            },
            srRervoirarea:{
                get:function getRervoirarea() {
                    if(this.storagerack.srRaId){
                        let rarea = this.$store.state.reservoirarea.data;
                        let rvarea =rarea.filter(value=>{
                            let flag = value.raId == this.storagerack.srRaId
                            return flag
                        })

                        return rvarea[0].raName;

                    }else{
                        return null;
                    }
                },
                set: function setType(value) {
                    this.storagerack.srRaId = value;
                    this.storagerack.srRaName = this.srRervoirarea
                }
            },
            srWarehouse:{
                get:function getWarehouse() {
                    if(this.storagerack.srWhId){
                        let whouses = this.$store.state.warehouse.data;
                        let whouse =whouses.filter(value=>{
                            let flag = value.whId == this.storagerack.srWhId
                            return flag
                        })
                        return whouse[0].whName;
                    }else{
                        return null;
                    }
                },
                set: function setType(value) {
                    this.storagerack.srWhId = value;
                    console.log(this.srWarehouse)
                    this.srRervoirarea=null;
                    this.storagerack.srWhName = this.srWarehouse;
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
                this.storagerack = this.deepClone(this.tempStoragerack);//把carrier的内容初始化
                this.addFormVisible=true;  //显示表单

                this.addOrEdit=true;  //显示添加表单
                this.storagerack.srId= this.getTime().id;

            },
            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.storagerack = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
                    this.addOrEdit = false;
                    console.log(this.srWarehouse)
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
            editStoragerack(formName){  //修改表单的确定按钮触发
                let time = this.getTime().time;
                this.storagerack.srModificationTime = time;
                this.storagerack.srModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {

                        if(!this.$refs.tb.selection[0].srMtId){
                            let resp = await editStoragerack(this.storagerack);
                            console.log(resp)
                            if(resp.code == 200){
                                this.$store.dispatch("storagerack/fetchDatas")
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
                        }else{
                            this.$message.error('修改失败，存有物料的货架不能修改仓库与库区');
                        }

                    } else {
                        this.$message.error('表单提交失败，请输入完整表单！');
                        this.addFormVisible = false
                    }
                });

            },
            submitForm(formName){  //添加表单的触发事件
                let time = this.getTime().time;

                this.storagerack.srCreationTime = time;
                this.storagerack.srModificationTime = time;
                this.storagerack.srCreatorId = this.$store.state.myUser.data.uId
                this.storagerack.srModifierId = this.$store.state.myUser.data.uId;
                console.log(this.storagerack)
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await addStoragerack(this.storagerack)
                        console.log(resp)
                        if(resp.code == 200){
                            this.$store.dispatch("storagerack/fetchDatas")
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

            deleteStoragerack(){  //删除按钮触发
                if (this.$refs.tb.selection.length != 0){
                    this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        let resp = await getStockBySrId(this.$refs.tb.selection[0].srId)

                        console.log(resp)
                        if (resp.code==400){
                            let result = await deleteStoragerack(this.$refs.tb.selection[0].srId)
                            this.$store.dispatch("storagerack/fetchDatas")
                            this.getList();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }else{
                            this.$message.error('该货架已经存有物料，不能直接删除！');
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

                this.tableData = this.$store.state.storagerack.data;
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
                obj.id = "sr"+year+month+day+hour+min+second+num;
                return obj

            },
        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="srCreationTime"|| self.value=="srModificationTime"){
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
            async mttype(ctypes){
                let str
                // switch (parseInt(ctypes)) {
                //     case 1: str="办公类"; break;
                //     case 2: str="劳保类"; break;
                //     case 3: str="工具类"; break;
                //     case 4: str="家电类"; break;
                //     case 5: str="公告宣传类"; break;
                //     case 6: str="水暖器材类"; break;
                //     case 7: str="钢材类"; break;
                //     case 8: str="电子元件类"; break;
                //
                //     default: str="--";
                // }
                str = this.$store.state.warehouse.data.filter(value=>{
                    return value.whId ==ctypes
                })
                if(str){
                    return str[0].whName
                }else{
                    return "--"
                }


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