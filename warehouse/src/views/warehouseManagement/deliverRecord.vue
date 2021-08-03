<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='dpModificationTime'| value=='dpCreationTime'">
                </el-date-picker>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='dpState'" style="width: 88%">
                    <el-option label="未发货" value= 1></el-option>
                    <el-option label="承运中" value= 2></el-option>
                    <el-option label="已运达" value= 3></el-option>
                    <el-option label="退回中" value= 4></el-option>
                    <el-option label="退回入库中" value= 5></el-option>
                    <el-option label="已退回" value= 6></el-option>
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

            <el-row>
                <el-button type="primary" icon="el-icon-edit" @click="showEditForm">发货</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="comfirmReceipt">确认送达</el-button>
                <el-button type="primary" icon="el-icon-edit" @click="goodsReturn">退货</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog title="发货单" :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="dispatchlist" ref="dispatchlist" class="demo-ruleForm">
                        <el-form-item label="发货单编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="dispatchlist.dpId"></el-input>
                        </el-form-item>
                        <el-form-item label="发货目的地" label-width="120px" prop="" style="text-align: left">
                            <el-input   v-model="dispatchlist.outdeliverlist ? dispatchlist.outdeliverlist.odlAddress : dispatchlist.outdeliverlist" disabled ></el-input>
                        </el-form-item>

                        <el-form-item label="承运商" label-width="120px" prop="dpCId" style="text-align: left">
                            <!--<el-input   v-model="dispatchlist.dpCId" oninput="if (value.length>25) value= value.slice(0,25) "></el-input>-->
                            <el-select v-model="sltCarrier" placeholder="请选择承运商" style="width: 100%" :disabled="dispatchlist.dpState!=1">
                                <el-option
                                        v-for="item in carrier"
                                        :key="item.cId"
                                        :label="item.cName"
                                        :value="item.cId">

                                </el-option>
                            </el-select>
                        </el-form-item>

                        <!--<el-form-item label="发货时间" label-width="120px" prop="dpTime" style="text-align: left">-->
                            <!--<el-date-picker-->
                                    <!--v-model="dptime"-->
                                    <!--type="date"-->
                                    <!--style="width: 100%"-->
                                    <!--placeholder="选择日期"-->
                                    <!--:disabled="dispatchlist.dpState!=1">-->
                            <!--</el-date-picker>-->
                        <!--</el-form-item>-->
                        <el-form-item label="快递单号" label-width="120px" prop="expressNumber" style="text-align: left">
                            <el-input   v-model="dispatchlist.expressNumber" :disabled="dispatchlist.dpState!=1" oninput="if (value.length>25) value= value.slice(0,25) "></el-input>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="dpComment" :disabled="dispatchlist.dpState!=1">
                            <el-input type="textarea" placeholder="请输入备注"  :disabled="dispatchlist.dpState!=1" v-model="dispatchlist.dpComment" oninput="if (value.length>100) value= value.slice(0,100) "></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addOrEdit ? exitAdd() : exitEdit()">取 消</el-button>
                        <el-button type="primary" @click="addOrEdit ? submitForm('dispatchlist') : editDispatchlist('dispatchlist')" v-if="dispatchlist.dpState==1">确 定</el-button>
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
                    :default-sort = "{prop: 'dpId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="dpId"
                        label="发货编号"
                        width="180"
                        fixed="left"
                        sortable>
                </el-table-column>
                <el-table-column
                        prop="dpOdlId"
                        label="出库单"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="dpCId"
                        label="承运商"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="dpTime"
                        label="发货时间"
                        sortable
                        width="180">
                </el-table-column>

                <el-table-column
                        prop="dpState"
                        label="发货状态"
                        sortable
                        width="180">
                    <template slot-scope="scope">
                        {{scope.row.dpState | ctype}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="dpComment"
                        label="备注"
                        sortable
                        show-overflow-tooltip
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.dpComment || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="dpCreatorId"
                        label="创建人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="dpCreationTime"
                        label="创建时间"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="dpModifierId"
                        label="最后修改人编号"
                        sortable
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="dpModificationTime"
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
    import {getAllDispatchlist,addDispatchlist,editDispatchlist,deleteDispatchlist,updateState} from '@/service/dispatchlistService.js'
    import loading from '@/components/loading.vue'
    import {getAllCarriers} from '@/service/carrierService.js'

    export default {
        name: "dispatchlistManagement",
        data(){
            return {
                routeName:{parentName:'角色管理',name:'发货管理',needSlot:true},
                tableData:null,
                total: 0, //数组总数
                carrier:null,
                currentPage: 1, //当前页
                paginationData: [], //分页数组,用于存储每页所拥有的数据
                pagesize: 10, //每页数据
                searchContent: "",//搜索内容
                stashList: [],
                watchSearch: "",
                checkedrow:[],
                waiting:false,
                addFormVisible: false,
                editFormVisible: false,
                value:"",
                selection:[
                    {key:"发货编号",value:"dpId"},
                    {key:"出库编号",value:"dpOdlId"},
                    {key:"发货时间",value:"dpTime"},
                    {key:"承运商编号",value:"dpCId"},
                    {key:"发货状态",value:"dpState"},
                    {key:"备注",value:"dpComment"},
                    {key:"创建人编号",value:"dpCreatorId"},
                    {key:"最后修改人编号",value:"dpModifierId"},
                    {key:"创建时间",value:"dpCreationTime"},
                    {key:"最后修改时间",value:"dpModificationTime"},
                ],
                rules: {
                    dpCId: [
                        { required: true, message: '请输入承运商公司名称', trigger: 'blur' },
                    ],
                    dpOdlId: [
                        { required: true, message: '请输入出库单编号', trigger: 'blur' },
                    ],
                    dpTime: [
                        { required: true, message: '请选择发货时间', trigger: 'blur' }
                    ],
                    dpState: [
                        { required: true, message: '请选择状态', trigger: 'change' }
                    ],
                    expressNumber: [
                        { required: true, message: '请输入快递单号', trigger: 'change' }
                    ],

                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                dispatchlist:{  //用于存储添加弹窗表单的输入数据
                    dpId:null,
                    dpOdlId:null,
                    dpTime:null,
                    dpCId:null,
                    dpState:null,
                    expressNumber:null,
                    dpComment:null,
                    dpCreatorId:null,
                    dpCreationTime:null,
                    dpModifierId:null,
                    dpModificationTime:null,
                    outdeliverlist:null
                },
                tempCarrier:{},      //暂存对象
                tempRow:{}
            }
        },
        async created(){
            console.log(this.getTime());
            this.carrier= await  getAllCarriers()
            this.getList();
            this.tempDispatchlist = this.deepClone(this.dispatchlist); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading

        },
        computed:{
            dptime:{
              get:function getTime() {
                  if(this.dispatchlist.dpTime){
                      return this.dispatchlist.dpTime
                  }else{
                      return null
                  }
              },
              set:function setTime(value) {
                  let d = new Date(value);
                  let a = new Date();
                  console.log(a,d)
                  let flag = a.getFullYear()>=d.getFullYear()&&a.getMonth()>=d.getMonth()&&a.getDate()>d.getDate();
                  if(flag){
                      this.$message.error('发货时间不能早于今天！！');
                  }else{
                      let month = d.getMonth()+1;
                      if(month<"10"){
                          month = "0"+month;
                      }
                      this.dispatchlist.dpTime=d.getFullYear() + '-' + month + '-' + d.getDate();
                  }

              }
            },
            sltCarrier:{
                get:function getCarrier() {
                    if(this.dispatchlist.dpCId){
                        let cr = this.carrier.filter(val=>{
                            return val.cId == this.dispatchlist.dpCId
                        })

                        return cr[0].cName
                    }else{
                        return null
                    }

                },
                set:function setCarrier(value){
                    this.dispatchlist.dpCId = value
                }

            },
            dpStatech:{
                get:function getDpState(){
                    let state = null;
                    switch (parseInt(this.dispatchlist.dpState)) {
                        case 1: state="未发货";break
                        case 2: state="承运中";break
                        case 3: state="已送达";break
                        case 4: state="退回中";break
                        case 5: state="退回入库中";break
                        case 6: state="已退回";break
                        default: state="--"
                    }
                    return state;
                },
                set:function setDpState(value){
                    this.this.dispatchlist.dpState = parseInt(value)
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
                this.dispatchlist = this.deepClone(this.tempDispatchlist);//把carrier的内容初始化
                this.addFormVisible=true;  //显示表单
                this.addOrEdit=true;  //显示添加表单
                this.dispatchlist.dpId= this.getTime().id;

            },
            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.dispatchlist = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
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
            editDispatchlist(formName){  //修改表单的确定按钮触发
                let time = this.getTime().time;
                this.dispatchlist.dpModificationTime = time;
                this.dispatchlist.dpState = 2;
                this.dispatchlist.dpTime = time;
                this.dispatchlist.dpModifierId = this.$store.state.myUser.data.uId;
                console.log(this.dispatchlist)
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {

                        let resp = await editDispatchlist(this.dispatchlist);
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
                            this.$message.error('修改失败，数据或服务器出错！！');
                            this.addFormVisible = false
                        }
                    } else {
                        this.$message.error('表单提交失败，请输入完整表单！');
                        this.addFormVisible = false
                    }
                });

            },
            async comfirmReceipt(){
                if(this.$refs.tb.selection.length != 0){
                    this.$confirm('确认送达？', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        this.dispatchlist = this.deepClone(this.$refs.tb.selection[0])
                        if(this.dispatchlist.dpState==2){
                            let resp = await updateState(3,this.dispatchlist.dpId)
                            if(resp.code==200){
                                this.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        this.getList();
                                    }
                                });
                            }else{
                                this.$message.error(resp.message);
                            }
                        }else{
                            this.$message.error('该单据状态不处于承运中，不能执行该操作！');
                        }
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }else{
                    this.$message.error('请先选择数据！！');
                }
            },
            async goodsReturn(){
                if(this.$refs.tb.selection.length != 0){
                    this.$confirm('确认退货?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        this.dispatchlist = this.deepClone(this.$refs.tb.selection[0])
                        if(this.dispatchlist.dpState==2){
                            let resp = await updateState(4,this.dispatchlist.dpId)
                            if(resp.code==200){
                                this.$alert('操作成功', '提示', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        this.getList();
                                    }
                                });
                            }else{
                                this.$message.error(resp.message);
                            }
                        }else{
                            this.$message.error('该单据状态不处于承运中，不能执行该操作！');
                        }

                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });

                }else{
                    this.$message.error('请先选择数据！！');
                }
            },
            submitForm(formName){  //添加表单的触发事件
                let time = this.getTime().time;
                this.dispatchlist.dpCreationTime = time;
                this.dispatchlist.dpModificationTime = time;
                this.dispatchlist.dpState = parseInt(this.dispatchlist.dpState)
                this.dispatchlist.dpCreatorId = this.$store.state.myUser.data.uId
                this.dispatchlist.dpModifierId = this.$store.state.myUser.data.uId;
                this.$refs[formName].validate( async (valid) => {
                    if (valid) {
                        let resp = await addDispatchlist(this.dispatchlist)
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

            deleteDispatchlist(){  //删除按钮触发
                if (this.$refs.tb.selection.length != 0){
                    this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then( async () => {
                        let result = await deleteCarrier(this.$refs.tb.selection[0].cId)
                        console.log(result)
                        if(result.code==400){
                            this.$message.error(result.message)
                        }else{
                            this.getList();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
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
                this.tableData = await getAllDispatchlist()
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
                        return item.cId.includes(this.searchContent)
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
                obj.id = "dp"+year+month+day+hour+min+second+num;
                return obj

            },
        },
        watch: {

            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="dpCreationTime"|| self.value=="dpModificationTime"){
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
                    case "1": str="未发货"; break;
                    case "2": str="承运中"; break;
                    case "3": str="已运达"; break;
                    case "4": str="退回中"; break;
                    case "5": str="退回入库中"; break;
                    case "6": str="已退回"; break;
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