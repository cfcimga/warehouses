<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='grnModificationTime'| value=='grnCreationTime'">
                </el-date-picker>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='grnState'" style="width: 88%">
                    <el-option label="待审核" value= 1></el-option>
                    <el-option label="待入库" value= 2></el-option>
                    <el-option label="完成" value= 3></el-option>
                </el-select>
                <el-select v-model="watchSearch" placeholder="请先选择" v-else-if="value=='grnType'" style="width: 88%">
                    <el-option label="采购入库" value= 1></el-option>
                    <!--<el-option label="生产入库" value= 2></el-option>-->
                    <el-option label="退货入库" value= 3></el-option>
                    <!--<el-option label="调拨入库" value= 4></el-option>-->
                    <!--<el-option label="调拨入库" value= 5></el-option>-->
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
                <el-button type="primary" icon="el-icon-refresh" v-if="allOrselfFlag==true" @click="allOrself()">显示个人入库单</el-button>
                <el-button type="primary" icon="el-icon-refresh" v-else @click="allOrself()">显示所有入库单</el-button>

                <!--弹窗表单，用于添加或修改数据-->
                <el-dialog title="入库单" :visible.sync="addFormVisible">
                    <el-form :rules="rules" :model="goodsreceivednote" ref="goodsreceivednote" class="demo-ruleForm">
                        <el-form-item label="入库编号" label-width="120px" >
                            <el-input   value="编号系统自动生成，不能修改" disabled v-model="goodsreceivednote.grnId"></el-input>
                        </el-form-item>

                        <el-form-item label="入库类型" label-width="120px" prop="grnType" >
                            <el-select v-model="gType" placeholder="请选择入库类型" style="width: 100%" disabled>

                                <el-option label="采购入库" value= 1></el-option>
                                <!--<el-option label="生产入库" value= 2></el-option>-->
                                <el-option label="退货入库" value= 3></el-option>
                                <!--<el-option label="调拨入库" value= 4></el-option>-->

                            </el-select>
                        </el-form-item>
                        <el-form-item label="所属仓库" label-width="120px" prop="warehouseId" style="text-align: left;" >
                            <el-select v-model="grnWarehouse" placeholder="请选择所属仓库"  style="width: 100%" disabled>
                                <el-option
                                        v-for="item in $store.state.warehouse.data"
                                        :key="item.whId"
                                        :label="item.whName"
                                        :value="item.whId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="所属库区" label-width="120px" prop="reservoirareaId" style="text-align: left"  >
                            <el-select v-model="grnReservoirarea" placeholder="请选择所属库区"  style="width: 100%" disabled >
                                <el-option
                                        v-for="item in reservoirareas"
                                        :key="item.raId"
                                        :label="item.raName"
                                        :value="item.raId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="货架" label-width="120px" prop="grnSrId" style="text-align: left">
                            <el-select v-model="storageRackName" placeholder="请选择货架" style="width: 100%" disabled>
                                <el-option
                                        v-for="item in storageRacks"
                                        :key="item.srId"
                                        :label="item.srName"
                                        :value="item.srId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="发货单" label-width="120px" prop="grnDpId" style="text-align: left" v-if="grnDpIds">
                            <el-select v-model="grnDpIds" placeholder="请选择被退回的发货单" style="width: 100%" disabled>
                                <el-option
                                        v-for="item in allDpReturnNote"
                                        :key="item.dpId"
                                        :label="item.dpId"
                                        :value="item.dpId">

                                </el-option>
                            </el-select>
                        </el-form-item>
                        <!--<el-form-item label="订单编号" label-width="120px" :prop="goodsreceivednote.grnType==3 ? '' : 'grnOrderNum'">-->
                            <!--<el-input   v-model="goodsreceivednote.grnOrderNum" disabled oninput="if (value.length>25) value= value.slice(0,25) "></el-input>-->
                        <!--</el-form-item>-->
                        <el-form-item label="入库物料" label-width="120px" :prop="goodsreceivednote.grnType==3 ? '' : 'grnMaterialId'" style="text-align: left;">
                            <el-select v-model="grnMaterials" placeholder="请选择物料" style="width: 100%" disabled>
                                <el-option
                                        v-for="item in material"
                                        :key="item.mtId"
                                        :label="item.mtName"
                                        :value="item.mtId">

                                </el-option>
                            </el-select>
                        </el-form-item>




                        <el-form-item label="供应商" label-width="120px" prop="grnSplId" style="text-align: left" v-if="splName">
                            <el-select v-model="splName" placeholder="请选择供应商" style="width: 100%" disabled>
                                <el-option
                                        v-for="item in supplier"
                                        :key="item.splId"
                                        :label="item.splName"
                                        :value="item.splId">

                                </el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="入库数量" label-width="120px" :prop="goodsreceivednote.grnType==3 ? '' : 'grnInQuantity'" >
                            <el-input   v-model="goodsreceivednote.grnInQuantity"
                                        onkeyup="this.value=this.value.replace(/\D/g,'')"
                                        onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                        disabled>

                            </el-input>
                        </el-form-item>
                        <el-form-item label="审核人编号" label-width="120px" >
                            <el-input   v-model="goodsreceivednote.grnModifierId" disabled ></el-input>
                        </el-form-item>
                        <el-form-item label="审核时间" label-width="120px">
                            <el-input   v-model="goodsreceivednote.grnModificationTime" disabled ></el-input>
                        </el-form-item>
                        <el-form-item label="备注" label-width="120px" prop="grnComment">
                            <el-input type="textarea" placeholder="请输入备注"   v-model="goodsreceivednote.grnComment" oninput="if (value.length>25) value= value.slice(0,25) "
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
                    :cell-style="{'text-align':'center'}"
                    :default-sort = "{prop: 'grnId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55" >
                </el-table-column>
                <el-table-column
                        prop="grnId"
                        label="入库编号"
                        width="180"
                        fixed="left"
                        sortable>
                </el-table-column>
                <!--<el-table-column-->
                        <!--prop="grnOrderNum"-->
                        <!--label="订单编号"-->
                        <!--sortable-->
                        <!--width="150">-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="grnType"
                        label="入库类型"
                        :sortable="true"
                        width="120">
                    <template slot-scope="scope" >
                        {{ scope.row.grnType | ctype}}
                    </template>
                </el-table-column>
                <!--<el-table-column-->
                <!--prop="grnSplId"-->
                <!--label="供应商名称"-->
                <!--:sortable="true"-->
                <!--v-if="showIdFlag==false"-->
                <!--:key="1"-->
                <!--width="120">-->
                <!--<template slot-scope="scope" >-->
                <!--{{ scope.row.grnSplName}}-->
                <!--</template>-->
                <!--</el-table-column>-->
                <!--<el-table-column-->
                <!--prop="grnSplId"-->
                <!--label="供应商编号"-->
                <!--sortable-->
                <!--v-else-->
                <!--key="2"-->
                <!--width="180">-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="grnMaterialId"
                        label="物料名称"
                        sortable
                        v-if="showIdFlag==false"
                        key="4"
                        width="120">
                    <template slot-scope="scope" >
                        {{ scope.row.grnMaterialName}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="grnMaterialId"
                        label="物料编号"
                        sortable
                        v-else
                        key="5"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="grnSrId"
                        label="货架名称"
                        sortable
                        v-if="showIdFlag==false"
                        key="6"
                        width="120">
                    <template slot-scope="scope" >
                        {{ scope.row.grnSrName || "--"}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="grnSrId"
                        label="货架编号"
                        sortable
                        v-else
                        key="7"
                        width="180">
                </el-table-column>

                <!--<el-table-column-->
                        <!--prop="grnState"-->
                        <!--label="状态"-->
                        <!--sortable-->
                        <!--width="120">-->
                    <!--<template slot-scope="scope" >-->
                        <!--{{ scope.row.grnState | gState}}-->
                    <!--</template>-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="grnInQuantity"
                        label="入库数量"
                        sortable
                        width="120">
                </el-table-column>
                <!--<el-table-column-->
                        <!--prop="grnComment"-->
                        <!--label="备注"-->
                        <!--show-overflow-tooltip-->
                        <!--sortable-->
                        <!--width="180">-->
                    <!--<template slot-scope="scope" >-->
                        <!--{{ scope.row.grnComment || "&#45;&#45;"}}-->
                    <!--</template>-->
                <!--</el-table-column>-->
                <el-table-column
                        prop="grnCreatorId"
                        label="创建人编号"
                        sortable
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="grnCreationTime"
                        label="创建时间"
                        sortable
                        key="9"
                        width="180">
                </el-table-column>
                <el-table-column
                prop="grnModifierId"
                label="审核人编号"
                sortable
                show-overflow-tooltip
                key="10"
                width="120">
                </el-table-column>
                <el-table-column
                prop="grnModificationTime"
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
    import {findAllGoodsReceivedNote,addGoodsReceivedNote,editGoodsReceivedNote,deleteGoodsReceivedNote,updateState,updateStockAndStoragerack,updateStock} from '@/service/goodsreceivednoteService.js'
    import loading from '@/components/loading.vue'
    import {getAllMaterial} from "../../service/materialService";
    import {getAllSupplier} from "../../service/supplierService";
    import {editStock,addStock,getStockBySrId} from '@/service/stockService.js'
    import {updateMaterial} from '@/service/storagerackService.js'
    import {findReturnNote} from '@/service/dispatchlistService.js'

    export default {
        name: "reservoirAreaManagement",
        data(){
            return {
                routeName:{parentName:'仓库管理',name:'入库记录',needSlot:true},
                checkFlag:false,
                dpReturnNote:null,
                showIdFlag:false,
                allOrselfFlag:true,
                // warehouseId:null,
                // reservoirareaId:null,
                waiting:false,
                tableData:null,
                material:null,
                storagerack:null,
                supplier:null,
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
                    {key:"入库编号",value:"grnId"},
                    // {key:"订单编号",value:"grnOrderNum"},
                    {key:"入库类型",value:"grnType"},
                    {key:"供应商编号",value:"grnSplId"},
                    {key:"物料编号",value:"grnMaterialId"},
                    {key:"货架编号",value:"grnSrId"},
                    {key:"入库数量",value:"grnInQuantity"},
                    {key:"状态",value:"grnState"},
                    // {key:"备注",value:"grnComment"},
                    {key:"创建人编号",value:"grnCreatorId"},
                    {key:"审核人编号",value:"grnModifierId"},
                    {key:"创建时间",value:"grnCreationTime"},
                    {key:"审核时间",value:"grnModificationTime"},
                ],
                rules: {
                    grnOrderNum: [
                        { required: true, message: '请输入订单编号', trigger: 'blur' },
                    ],
                    grnType: [
                        { required: true, message: '请输入入库类型', trigger: 'blur' }
                    ],
                    grnSplId: [
                        { required: true, message: '请选择承运商', trigger: 'blur' }
                    ],
                    grnMaterialId: [
                        { required: true, message: '请选择物料', trigger: 'blur' }
                    ],
                    grnSrId: [
                        { required: true, message: '请选择货架', trigger: 'blur' }
                    ],
                    grnInQuantity: [
                        { required: true, message: '请输入入库数量', trigger: 'blur' }
                    ],
                    warehouseId: [
                        { required: true, message: '请选择所属仓库', trigger: 'blur' }
                    ],
                    reservoirareaId: [
                        { required: true, message: '请选择所属库区', trigger: 'blur' }
                    ],
                    grnComment: [
                        { required: false, message: '请输入备注', trigger: 'blur' }
                    ],
                    grnDpId:[
                        { required: true, message: '请选择发货单', trigger: 'blur' }
                    ],

                },
                addOrEdit:true,    //作为判断弹窗的确定按钮事件的依据，true是添加事件，false是修改事件
                goodsreceivednote:{  //用于存储添加弹窗表单的输入数据
                    grnId:null,
                    grnOrderNum:null,
                    grnType:null,
                    grnSplId:null,
                    grnSplName:null,
                    grnMaterialId:null,
                    grnMaterialName:null,
                    grnSrId:null,
                    grnSrName:null,
                    grnInQuantity:null,
                    grnState:null,
                    grnDpId:null,
                    grnComment:null,
                    grnCreatorId:null,
                    grnCreationTime:null,
                    grnModifierId:null,
                    grnModificationTime:null,
                    warehouseId:null,
                    warehouseName:null,
                    reservoirareaId:null,
                    reservoirareaName:null

                },
                tempGoodsreceivednote:{},      //暂存对象
                tempRow:{},
                showAllFlag:false,
                tempDpId:null
            }
        },
        async created(){

            await this.$store.dispatch("storagerack/fetchDatas")
            this.material = await getAllMaterial()
            this.supplier = await getAllSupplier()
            this.dpReturnNote = await findReturnNote(4);
            console.log(this.dpReturnNote)
            this.storagerack = await this.$store.state.storagerack.data;
            this.getList();
            this.tempGoodsreceivednote = this.deepClone(this.goodsreceivednote); //先存好carrier空对象
        },
        components:{
            mainLayout,
            loading
        },
        computed:{
            grnDpIds:{
                get:function getDp(){
                    return this.goodsreceivednote.grnDpId
                },
                set:function setDp(value){  //选择发货单的时候自动填好发货单内所需要的信息
                    this.goodsreceivednote.grnDpId = value;
                    let dp = this.dpReturnNote.filter(val=>{
                        return val.dpId == value
                    })
                    console.log(dp)
                    if(dp.length>0){
                        this.goodsreceivednote.grnMaterialName = dp[0].outdeliverlist.odlMaterialName
                        this.goodsreceivednote.grnOrderNum = dp[0].outdeliverlist.odlOrderNum;
                        this.goodsreceivednote.grnInQuantity = dp[0].outdeliverlist.odlOutQuantity
                        this.goodsreceivednote.grnMaterialId = dp[0].outdeliverlist.odlMaterialId
                    }

                }
            },
            allDpReturnNote:{
                get:function getDpReturnNote(){
                    if(this.dpReturnNote){
                        if(this.goodsreceivednote.warehouseId && this.dpReturnNote.length>0){
                            console.log("1236410")
                            let dpNote = this.dpReturnNote.filter(value=>{
                                return this.goodsreceivednote.warehouseId==value.outdeliverlist.warehouseId
                            })
                            return dpNote
                        }
                        else{
                            return null
                        }
                    }else{
                        return null
                    }

                },
            },
            grnReservoirarea:{
                get:function getReservoirarea() { //获取库区名称
                    if(this.goodsreceivednote.reservoirareaId){
                        let rarea = this.$store.state.reservoirarea.data;
                        let rvarea =rarea.filter(value=>{
                            let flag = value.raId == this.goodsreceivednote.reservoirareaId
                            return flag
                        })

                        return rvarea[0].raName;

                    }else{
                        return null;
                    }
                },

                set: function setReservoirarea(value) {
                    this.goodsreceivednote.reservoirareaId = value;
                    this.storageRackName = null;
                }
            },
            reservoirareas:{
                get:function getReservoirarea() {
                    if(this.goodsreceivednote.grnMaterialId){
                        let type = this.material.filter(val=>{
                            return val.mtId == this.goodsreceivednote.grnMaterialId
                        })

                        if(this.goodsreceivednote.warehouseId){
                            return this.$store.state.reservoirarea.data.filter(value=>{
                                return value.raWhId == this.goodsreceivednote.warehouseId && value.raType == type[0].mtType   //查找选中的仓库内的所有库区，再筛选库区类型与物料类型一致的库区
                            })
                        }else{
                            return null
                        }
                    }else{
                        return null
                    }

                }
            },
            grnWarehouse:{
                get:function getWarehouse() {  //获取仓库名称

                    if(this.goodsreceivednote.warehouseId){
                        console.log(this.goodsreceivednote.warehouseId)
                        let whouses = this.$store.state.warehouse.data;
                        console.log(typeof whouses)
                        let whouse =whouses.filter(value=>{
                            let flag = value.whId == this.goodsreceivednote.warehouseId
                            return flag
                        })
                        return whouse[0].whName;
                    }else{
                        return null;
                    }
                },
                set: function setWarehouse(value) {
                    this.goodsreceivednote.warehouseId = value;
                    console.log(this.goodsreceivednote.warehouseId)
                    this.grnReservoirarea = null;
                    this.goodsreceivednote.grnDpId = null
                }
            },
            storageRackName:{
                get:function getStoragerackName() {   //获取货架名称
                    if(this.goodsreceivednote.grnSrId){
                        let grnSr
                        grnSr=this.storagerack.filter(value=>{
                            let flag = value.srId == this.goodsreceivednote.grnSrId
                            return flag
                        })

                        return grnSr[0].srName;
                    }else{
                        return null;
                    }
                },
                set: function setStoragerackName(value) {
                    this.goodsreceivednote.grnSrId = value;
                    this.goodsreceivednote.grnSrName = this.storageRackName
                }
            },
            storageRacks:{
                get:function getStoragerack() {   //获取货架
                    let grnSr=null
                    if(this.goodsreceivednote.reservoirareaId){
                        let strr
                        strr = this.storagerack.filter(value=>{
                            return this.goodsreceivednote.reservoirareaId == value.srRaId
                        })
                        grnSr =strr.filter(value=>{
                            let flag = value.srMtId == this.goodsreceivednote.grnMaterialId || value.srMtId == null
                            return flag
                        })
                    }
                    return grnSr
                }

            },
            gType:{
                get:function getType() {
                    let type = null;
                    switch (parseInt(this.goodsreceivednote.grnType)) {
                        case 1: type="采购入库";break
                        case 2: type="生产入库";break
                        case 3: type="退货入库";break
                        case 4: type="调拨入库";break
                        case 5: type="其他入库";break
                    }
                    return type;
                },
                set: function setType(value) {
                    this.goodsreceivednote.grnType = value;
                    this.goodsreceivednote.grnMaterialName = null
                    this.goodsreceivednote.warehouseId = null
                    this.goodsreceivednote.warehouseName = null
                    this.goodsreceivednote.grnOrderNum = null
                    this.goodsreceivednote.grnInQuantity = null
                    this.goodsreceivednote.grnMaterialId = null

                    this.tempDpId = this.goodsreceivednote.grnDpId; //这个暂时存好，用于修改的时候，把退货入库的单改成其他入库方式时，修改回发货单的状态为退回中
                    this.grnDpIds = null
                }


            },
            grnMaterials:{
                get:function getMaterialName() {
                    if(this.goodsreceivednote.grnMaterialId){
                        let grnMt =this.material.filter(value=>{
                            let flag = value.mtId == this.goodsreceivednote.grnMaterialId
                            return flag
                        })

                        return grnMt[0].mtName;
                    }else{
                        return null;
                    }
                },
                set: function setType(value) {
                    this.goodsreceivednote.grnMaterialId = value;
                    this.goodsreceivednote.grnMaterialName = this.grnMaterials
                }
            },  //物料
            splName:{
                get:function getsplName() {
                    if(this.goodsreceivednote.grnSplId){
                        let splNm =this.supplier.filter(value=>{
                            let flag = value.splId == this.goodsreceivednote.grnSplId
                            return flag
                        })
                        return splNm[0].splName;
                    }else{
                        return null;
                    }
                },
                set: function setsplName(value) {
                    this.goodsreceivednote.grnSplId = value;
                    this.goodsreceivednote.grnSplName = this.splName;
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
                    this.goodsreceivednote = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到goodsreceivednote内，作为表单数据
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

            showEditForm(){   //点击修改按钮时触发
                if(this.$refs.tb.selection.length != 0){
                    this.tempRow =this.deepClone(this.$refs.tb.selection[0]); //深层拷贝，防止表单修改后自动影响到表格table中的内容
                    this.goodsreceivednote = this.deepClone(this.$refs.tb.selection[0]);   //深层拷贝； 把选中的内容放到carrier内，作为表单数据
                    this.addOrEdit = false;
                    this.showAllFlag = false
                    this.checkFlag = false
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
                this.tableData = await findAllGoodsReceivedNote();
                console.log(this.tableData)
                if(!this.allOrselfFlag){
                    this.tableData = this.tableData.filter(value=>{
                        return value.grnCreatorId == this.$store.state.myUser.data.uId && value.grnState==3
                    })
                }else{
                    this.tableData = this.tableData.filter(value=>{
                        return value.grnState==3
                    })
                }
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
                obj.id = "grn"+year+month+day+hour+min+second+num;
                return obj

            },
        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="grnCreationTime"|| self.value=="grnModificationTime"){
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
                            if(self.value == "grnInQuantity" ){
                                return item[self.value].toString() ==  newValue
                            }else{
                                return item[self.value].toString().includes(newValue)
                            }

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

                str = this.$store.state.warehouse.data.filter(value=>{
                    return value.whId ==ctypes
                })
                if(str){
                    return str[0].whName
                }else{
                    return "--"
                }
            },
            gState(state){
                let gstate = "--"
                switch(parseInt(state)){
                    case 1: gstate="待审核";break
                    case 2: gstate="待入库";break
                    case 3: gstate="完成";break
                    default: gstate="--"

                }
                return gstate
            },
            ctype(ctype){
                let type = null;
                switch (parseInt(ctype)) {
                    case 1: type="采购入库";break
                    case 2: type="生产入库";break
                    case 3: type="退货入库";break
                    case 4: type="调拨入库";break
                    case 5: type="其他入库";break
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
        justify-content: left;
        background-color: #e6e7ef;
        border-color: #e6e7ef;
        margin-bottom: 15px;
        font-weight: bold;
        color:#4e4f4f;
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