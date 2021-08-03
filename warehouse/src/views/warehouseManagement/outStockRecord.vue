<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='odlModificationTime'| value=='odlCreationTime'">
                </el-date-picker>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='odlState'" style="width: 88%">
                    <el-option label="待审核" value= 1></el-option>
                    <el-option label="待出库" value= 2></el-option>
                    <el-option label="完成" value= 3></el-option>
                </el-select>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='odlType'" style="width: 88%">
                    <el-option label="销售出库" value= 1></el-option>
                    <!--<el-option label="领用出库" value= 2></el-option>-->
                    <!--<el-option label="外协出库" value= 3></el-option>-->

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
                <el-button type="primary" icon="el-icon-info" @click="showAll()" >查看</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="show()">显示编号/名称</el-button>
                <el-button type="primary" icon="el-icon-refresh" v-if="allOrselfFlag==true" @click="allOrself()">显示个人出库单</el-button>
                <el-button type="primary" icon="el-icon-refresh" v-else @click="allOrself()">显示所有出库单</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog title="出库单" :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="outdeliverlist" ref="outdeliverlist" class="demo-ruleForm">
                        <el-form-item label="出库编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="outdeliverlist.odlId"></el-input>
                        </el-form-item>
                        <!--<el-form-item label="订单编号" label-width="120px" prop="odlOrderNum">-->
                            <!--<el-input   v-model="outdeliverlist.odlOrderNum" disabled></el-input>-->
                        <!--</el-form-item>-->
                        <el-form-item label="出库地址" label-width="120px" prop="odlAddress">
                            <el-input   v-model="outdeliverlist.odlAddress" disabled oninput="if (value.length>25) value= value.slice(0,25) "></el-input>
                        </el-form-item>
                        <el-form-item label="出库物料" label-width="120px" prop="odlMaterialId" style="text-align: left;">
                            <el-select v-model="odlMaterials" placeholder="请选择物料" style="width: 100%" disabled>
                                <el-option
                                        v-for="item in material"
                                        :key="item.mtId"
                                        :label="item.mtName"
                                        :value="item.mtId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="客户" label-width="120px" prop="grnSplId" style="text-align: left" >
                            <el-select v-model="ctmName" placeholder="请选择客户" style="width: 100%" disabled>
                                <el-option
                                        v-for="item in customer"
                                        :key="item.ctmId"
                                        :label="item.ctmName"
                                        :value="item.ctmId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="入库类型" label-width="120px" prop="odlType" >
                            <el-select v-model="oType" placeholder="请选择入库类型" style="width: 100%" disabled>
                                <el-option label="销售出库" value= 1></el-option>
                                <!--<el-option label="领用出库" value= 2></el-option>-->
                                <!--<el-option label="外协出库" value= 3></el-option>-->
                            </el-select>
                        </el-form-item>
                        <el-form-item label="所属仓库" label-width="120px" prop="warehouseId" style="text-align: left;" >
                            <el-select v-model="odlWarehouse" placeholder="请选择所属仓库"  style="width: 100%" disabled>
                                <el-option
                                        v-for="item in $store.state.warehouse.data"
                                        :key="item.whId"
                                        :label="item.whName"
                                        :value="item.whId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="所属库区" label-width="120px" prop="reservoirareaId" style="text-align: left"  >
                            <el-select v-model="odlReservoirarea" placeholder="请选择所属库区"  style="width: 100%" disabled >
                                <el-option
                                        v-for="item in reservoirareas"
                                        :key="item.raId"
                                        :label="item.raName"
                                        :value="item.raId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="所属货架" label-width="120px" prop="odlSrId" style="text-align: left"  >
                            <el-select v-model="storageRackName" placeholder="请选择货架" style="width: 100%" disabled>
                                <el-option
                                        v-for="item in storageRacks"
                                        :key="item.srId"
                                        :label="item.srName"
                                        :value="item.srId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="入库数量" label-width="120px" prop="odlOutQuantity" >
                            <el-input   v-model="outdeliverlist.odlOutQuantity"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')"
                                        onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                        disabled>

                            </el-input>
                        </el-form-item>
                        <el-form-item label="审核人编号" label-width="120px">
                            <el-input   v-model="outdeliverlist.odlModifierId" disabled ></el-input>
                        </el-form-item>
                        <el-form-item label="审核时间" label-width="120px" >
                            <el-input   v-model="outdeliverlist.odlModificationTime" disabled ></el-input>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="grnComment">
                            <el-input type="textarea" placeholder="请输入备注"   v-model="outdeliverlist.odlComment" oninput="if (value.length>25) value= value.slice(0,25) "
                                      disabled></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="exitEdit()">取 消</el-button>
                    </div>
                </el-dialog>

            </el-row>

            <el-table
                    :data="paginationData"
                    max-height="540px"
                    ref="tb"
                    @selection-change="handSelectionChange"
                    border
                    @select-all="onSelectAll"
                    :default-sort = "{prop: 'odlId', order: 'descending'}"
                    :cell-style="{'text-align':'center'}"
                    style="width: 100%;">
                <el-table-column
                        type="selection"
                        width="55" >
                </el-table-column>
                <el-table-column
                        prop="odlId"
                        label="出库编号"
                        width="180"
                        fixed="left"
                        sortable>
                </el-table-column>
                <!--<el-table-column-->
                        <!--prop="odlOrderNum"-->
                        <!--label="订单编号"-->
                        <!--sortable-->
                        <!--width="150">-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="odlType"
                        label="出库类型"
                        :sortable="true"
                        width="120">
                    <template slot-scope="scope" >
                        {{ scope.row.odlType | ctype}}
                    </template>
                </el-table-column>
                <!--<el-table-column-->
                        <!--prop="odlCustomerId"-->
                        <!--label="客户名称"-->
                        <!--:sortable="true"-->
                        <!--v-if="showIdFlag==false"-->
                        <!--:key="1"-->
                        <!--width="120">-->
                    <!--<template slot-scope="scope" >-->
                        <!--{{ scope.row.odlCustomerName}}-->
                    <!--</template>-->
                <!--</el-table-column>-->
                <!--<el-table-column-->
                        <!--prop="odlCustomerId"-->
                        <!--label="客户编号"-->
                        <!--sortable-->
                        <!--v-else-->
                        <!--key="2"-->
                        <!--width="180">-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="odlMaterialId"
                        label="物料名称"
                        sortable
                        v-if="showIdFlag==false"
                        key="4"
                        width="120">
                    <template slot-scope="scope" >
                        {{ scope.row.odlMaterialName }}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="odlMaterialId"
                        label="物料编号"
                        sortable
                        v-else
                        key="5"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="odlSrId"
                        label="货架名称"
                        sortable
                        v-if="showIdFlag==false"
                        key="6"
                        width="120">
                    <template slot-scope="scope" >
                        {{ scope.row.odlSrName || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="odlSrId"
                        label="货架编号"
                        sortable
                        v-else
                        key="7"
                        width="180">
                    <template slot-scope="scope" >
                        {{ scope.row.odlSrId || "--"}}
                    </template>
                </el-table-column>

                <!--<el-table-column-->
                        <!--prop="odlState"-->
                        <!--label="状态"-->
                        <!--sortable-->
                        <!--width="120">-->
                    <!--<template slot-scope="scope" >-->
                        <!--{{ scope.row.odlState | oState}}-->
                    <!--</template>-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="odlOutQuantity"
                        label="出库数量"
                        sortable
                        width="120">
                </el-table-column>
                <!--<el-table-column-->
                        <!--prop="odlComment"-->
                        <!--label="备注"-->
                        <!--sortable-->
                        <!--show-overflow-tooltip-->
                        <!--width="180">-->
                    <!--<template slot-scope="scope" >-->
                        <!--{{ scope.row.odlComment || "&#45;&#45;"}}-->
                    <!--</template>-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="odlCreatorId"
                        label="创建人编号"
                        sortable
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="odlCreationTime"
                        label="创建时间"
                        sortable
                        key="9"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="odlModifierId"
                        label="审核人编号"
                        sortable
                        key="10"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="odlModificationTime"
                        label="审核时间"
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
    import {findAllOutdeliverlist,addOutdeliverlist,editOutdeliverlist,deleteOutdeliverlist,reduceStock,deleteStockAndStoragerack} from '@/service/outdeliverlistService.js'
    import loading from '@/components/loading.vue'
    import {getAllMaterial} from "../../service/materialService";
    import {getAllCustomer} from "../../service/customerService";
    import {editDispatchlist,addDispatchlist} from '@/service/dispatchlistService.js'
    import {editStock,deleteStock,getStockBySrId} from '@/service/stockService.js'
    import {updateMaterial} from '@/service/storagerackService.js'

    export default {
        name: "outStockRecord",
        data(){
            return {
                routeName:{parentName:'仓库管理',name:'出库记录',needSlot:true},
                checkFlag:false,
                allOrselfFlag:true,
                showIdFlag:false,
                // warehouseId:null,
                // reservoirareaId:null,
                waiting:false,
                tableData:null,
                material:null,
                storagerack:null,
                customer:null,
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
                value:"",  //绑定搜索的属性值
                selection:[
                    {key:"出库编号",value:"odlId"},
                    // {key:"订单编号",value:"odlOrderNum"},
                    {key:"出库类型",value:"odlType"},
                    {key:"客户编号",value:"odlCustomerId"},
                    {key:"物料编号",value:"odlMaterialId"},
                    {key:"货架编号",value:"odlSrId"},
                    {key:"出库数量",value:"odlOutQuantity"},
                    {key:"状态",value:"odlState"},
                    // {key:"备注",value:"odlComment"},
                    {key:"创建人编号",value:"odlCreatorId"},
                    {key:"审核人编号",value:"odlModifierId"},
                    {key:"创建时间",value:"odlCreationTime"},
                    {key:"审核时间",value:"odlModificationTime"},
                ],
                rules: {
                    odlOrderNum: [
                        { required: true, message: '请输入订单编号', trigger: 'blur' },
                    ],
                    odlType: [
                        { required: true, message: '请输入出库类型', trigger: 'blur' }
                    ],
                    odlCustomerId: [
                        { required: true, message: '请选择客户', trigger: 'blur' }
                    ],
                    odlMaterialId: [
                        { required: true, message: '请选择物料', trigger: 'blur' }
                    ],
                    odlSrId: [
                        { required: true, message: '请选择所属货架', trigger: 'blur' }
                    ],
                    odlOutQuantity: [
                        { required: true, message: '请输入出库数量', trigger: 'blur' }
                    ],
                    warehouseId: [
                        { required: true, message: '请选择所属仓库', trigger: 'blur' }
                    ],
                    reservoirareaId: [
                        { required: true, message: '请选择所属库区', trigger: 'blur' }
                    ],
                    odlComment: [
                        { required: false, message: '请输入备注', trigger: 'blur' }
                    ],

                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                outdeliverlist:{  //用于存储添加弹窗表单的输入数据
                    odlId:null,
                    odlOrderNum:null,
                    odlType:null,
                    odlCustomerId:null,
                    odlCustomerName:null,
                    odlMaterialId:null,
                    odlMaterialName:null,
                    odlSrId:null,
                    odlSrName:null,
                    odlOutQuantity:null,
                    odlState:null,
                    odlComment:null,
                    odlCreatorId:null,
                    odlCreationTime:null,
                    odlModifierId:null,
                    odlModificationTime:null,
                    warehouseId:null,
                    odlAddress:null,
                    warehouseName:null,
                    reservoirareaId:null,
                    reservoirareaName:null

                },
                tempOutdeliverlist:{},      //暂存对象
                tempRow:{},
                showAllFlag:false
            }
        },
        async created(){

            await this.$store.dispatch("storagerack/fetchDatas")
            this.material = await getAllMaterial()
            this.customer = await getAllCustomer()
            this.storagerack = await this.$store.state.storagerack.data;
            this.getList();
            this.tempOutdeliverlist = this.deepClone(this.outdeliverlist); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading
        },
        computed:{
            odlReservoirarea:{
                get:function getReservoirarea() { //获取库区名称
                    if(this.outdeliverlist.reservoirareaId){
                        let rarea = this.$store.state.reservoirarea.data;
                        let rvarea =rarea.filter(value=>{
                            let flag = value.raId == this.outdeliverlist.reservoirareaId
                            return flag
                        })

                        return rvarea[0].raName;

                    }else{
                        return null;
                    }
                },

                set: function setReservoirarea(value) {
                    this.outdeliverlist.reservoirareaId = value;
                    this.storageRackName = null;
                }
            },
            reservoirareas:{
                get:function getReservoirarea() {
                    if(this.outdeliverlist.odlMaterialId){
                        let type = this.material.filter(val=>{
                            return val.mtId == this.outdeliverlist.odlMaterialId
                        })

                        if(this.outdeliverlist.warehouseId){
                            return this.$store.state.reservoirarea.data.filter(value=>{
                                return value.raWhId == this.outdeliverlist.warehouseId && value.raType == type[0].mtType   //查找选中的仓库内的所有库区，再筛选库区类型与物料类型一致的库区
                            })
                        }else{
                            return null
                        }
                    }else{
                        return null
                    }

                }
            },
            odlWarehouse:{
                get:function getWarehouse() {  //获取仓库名称

                    if(this.outdeliverlist.warehouseId){
                        console.log(this.outdeliverlist.warehouseId)
                        let whouses = this.$store.state.warehouse.data;
                        console.log(typeof whouses)
                        let whouse =whouses.filter(value=>{
                            let flag = value.whId == this.outdeliverlist.warehouseId
                            return flag
                        })
                        return whouse[0].whName;
                    }else{
                        return null;
                    }
                },
                set: function setWarehouse(value) {
                    this.outdeliverlist.warehouseId = value;
                    console.log(this.outdeliverlist.warehouseId)
                    this.odlReservoirarea = null;
                }
            },
            storageRackName:{
                get:function getStoragerackName() {   //获取货架名称
                    if(this.outdeliverlist.odlSrId){
                        let odlSr
                        odlSr=this.storagerack.filter(value=>{
                            let flag = value.srId == this.outdeliverlist.odlSrId
                            return flag
                        })

                        return odlSr[0].srName;
                    }else{
                        return null;
                    }
                },
                set: function setStoragerackName(value) {
                    this.outdeliverlist.odlSrId = value;
                    this.outdeliverlist.odlSrName = this.storageRackName
                }
            },
            storageRacks:{
                get:function getStoragerack() {   //获取货架
                    let odlSr=null

                    if(this.outdeliverlist.reservoirareaId){
                        let strr
                        strr = this.storagerack.filter(value=>{
                            return this.outdeliverlist.reservoirareaId == value.srRaId
                        })
                        odlSr =strr.filter(value=>{    //筛选出来的货架全是有物料的
                            let flag = value.srMtId == this.outdeliverlist.odlMaterialId
                            return flag
                        })
                    }
                    return odlSr
                }

            },
            oType:{
                get:function getType() {
                    let type = null;
                    switch (parseInt(this.outdeliverlist.odlType)) {
                        case 1: type="销售出库";break
                        case 2: type="领用出库";break
                        case 3: type="外协出库";break
                    }
                    return type;
                },
                set: function setType(value) {
                    this.outdeliverlist.odlType = value;
                }


            },
            odlMaterials:{     //获取物料名称
                get:function getMaterialName() {
                    if(this.outdeliverlist.odlMaterialId){
                        let odlMt =this.material.filter(value=>{
                            let flag = value.mtId == this.outdeliverlist.odlMaterialId
                            return flag
                        })

                        return odlMt[0].mtName;
                    }else{
                        return null;
                    }
                },
                set: function setType(value) {
                    this.outdeliverlist.odlMaterialId = value;
                    this.outdeliverlist.odlMaterialName = this.odlMaterials
                }
            },  //物料
            ctmName:{
                get:function getctmName() {
                    if(this.outdeliverlist.odlCustomerName){
                        return this.outdeliverlist.odlCustomerName
                    }
                    else if(this.outdeliverlist.odlCustomerId){
                        let ctmNm =this.customer.filter(value=>{
                            let flag = value.ctmId == this.outdeliverlist.odlCustomerId
                            return flag
                        })
                        return ctmNm[0].ctmName;
                    }else{
                        return null;
                    }
                },
                set: function setctmName(value) {
                    this.outdeliverlist.odlCustomerId = value;
                    this.outdeliverlist.odlCustomerName = this.ctmName;
                }
            }  //供应商
        },
        methods:{
            allOrself(){
                this.allOrselfFlag=!this.allOrselfFlag;
                this.getList();
            },
            showAll(){  //查看按钮触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.outdeliverlist = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到goodsreceivednote内，作为表单数据
                    this.addOrEdit = false;
                    this.checkFlag=true
                    this.showAllFlag = true

                    this.addFormVisible=true; //把弹窗显示出来
                }else{
                    this.$message.error('请先选择数据！！');
                }
            },

            show(){
                this.showIdFlag = !this.showIdFlag;  //展示id/名称
            },
            onSelectAll(){   //把表头的全选框设定为清除选定内容
                this.$refs.tb.clearSelection()
            },
            deepClone(obj) {  //深层拷贝
                let _obj = JSON.stringify(obj),
                    objClone = JSON.parse(_obj);
                return objClone;
            },
            async showAddForm(){  //点击添加按钮时触发

                this.outdeliverlist = this.deepClone(this.tempOutdeliverlist);//把carrier的内容初始化
                this.addFormVisible=true;  //显示表单
                this.checkFlag = false
                this.addOrEdit=true;  //显示添加表单
                this.outdeliverlist.odlId= this.getTime("odl").id;
                this.showAllFlag = false;//表示不是查看按钮，能点击确定按钮

            },
            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.outdeliverlist = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
                    this.addOrEdit = false;
                    this.showAllFlag = false
                    this.checkFlag = false   //表示是修改按钮触发的修改弹窗
                    this.addFormVisible=true; //把弹窗显示出来
                }else{
                    this.$message.error('请先选择数据！！');
                }
            },
            exitEdit(){  //修改的时候点击取消的时触发
                this.addFormVisible=false;

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
                this.waiting=true
                this.tableData = await findAllOutdeliverlist();
                if(!this.allOrselfFlag){
                    this.tableData = this.tableData.filter(value=>{
                        return value.odlCreatorId == this.$store.state.myUser.data.uId && value.odlState==3
                    })
                }else{
                    this.tableData = this.tableData.filter(value=>{
                        return value.odlState==3
                    })
                }

                this.waiting = false
                // console.log(this.tableData)
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
        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="odlCreationTime"|| self.value=="odlModificationTime"){
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
                            if(self.value == "odlOutQuantity" ){
                                return item[self.value].toString() ==  newValue
                            }else{
                                return item[self.value].toString().includes(newValue)
                            }
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

                str = this.$store.state.warehouse.data.filter(value=>{
                    return value.whId ==ctypes
                })
                if(str){
                    return str[0].whName
                }else{
                    return "--"
                }
            },
            oState(state){
                let gstate = "--"
                switch(parseInt(state)){
                    case 1: gstate="待审核";break
                    case 2: gstate="待出库";break
                    case 3: gstate="完成";break
                    default: gstate="--"

                }
                return gstate
            },
            ctype(ctype){
                let type = null;
                switch (parseInt(ctype)) {
                    case 1: type="销售出库";break
                    case 2: type="领用出库";break
                    case 3: type="外协出库";break
                }
                return type;
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
    .bg{
        background-color: #18ae13;
        padding: 5px 10px;
        color:white;
        border-radius: 5px;
    }
    .slt .el-input{
        width: 88%;


    }
    .slt .el-select{
        width: 12%;
    }

</style>