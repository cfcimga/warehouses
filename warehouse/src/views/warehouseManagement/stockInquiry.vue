<template>

    <mainLayout :routeName="routeName">
        <div slot="select" class="slt">
            <el-row >
                <el-date-picker
                        v-model="watchSearch"
                        type="date"
                        placeholder="选择日期" v-if="value=='modificationTime'| value=='creationTime'">
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
        <loading v-if="waiting==true"></loading>
        <div class="content" v-else>
            <el-table
                    :data="paginationData"
                    max-height="540px"
                    ref="tb"
                    @selection-change="handSelectionChange"
                    border
                    :cell-style="{'text-align':'center'}"
                    @select-all="onSelectAll"
                    :default-sort = "{prop: 'mtId', order: 'descending'}"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55" >
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
                        prop="currentStock"
                        label="当前库存"
                        sortable
                        width="120">
                    <template slot-scope="scope" >
                        <span :class="scope.row.currentStock>=scope.row.safeStock/4 ? 'bg1':'bg2'">
                            {{ scope.row.currentStock}}
                        </span>

                    </template>
                </el-table-column>
                <el-table-column
                        prop="safeStock"
                        label="安全库存"
                        sortable
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="srId"
                        label="货架编号"
                        :sortable="true"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="srName"
                        label="货架名称"
                        show-overflow-tooltip
                        sortable
                        width="180">
                </el-table-column>

                <el-table-column
                        prop="creationTime"
                        label="创建时间"
                        sortable
                        width="180">
                </el-table-column>
                <!--<el-table-column-->
                        <!--prop="modificationTime"-->
                        <!--label="最后修改时间"-->
                        <!--sortable-->
                        <!--width="180">-->
                <!--</el-table-column>-->
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
    import {getAllStock,addStock,editStock,deleteStock} from '@/service/stockService.js'
    import loading from '@/components/loading.vue'
    import {getAllMaterial} from "../../service/materialService";

    export default {
        name: "stockManagement",
        data(){
            return {
                routeName:{parentName:'仓库管理',name:'库存查询',needSlot:true},
                showIdFlag:false,
                tableData:null,
                waiting:false,
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
                    {key:"物料编号",value:"mtId"},
                    {key:"物料名称",value:"mtName"},
                    {key:"货架编号",value:"srId"},
                    {key:"货架名称",value:"srName"},
                    {key:"当前库存",value:"currentStock"},
                    {key:"安全库存",value:"safeStock"},
                    {key:"创建时间",value:"creationTime"},
                ],
                tempRow:{}
            }
        },
        async created(){
            this.getList();

        },
        components:{
            mainLayout,
            loading
        },
        computed:{


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
            handSelectionChange(row){   //这里用于把表单的多选功能改成单选
                this.checkedrow = row[0];
                if (row.length > 1) {
                    this.$refs.tb.clearSelection();
                    this.$refs.tb.toggleRowSelection(row.pop());
                }
            },
            async getList(){    //async与await必须要加上，否则根据js的异步语法规则会先运行axios之后的内容，完了之后才会执行axios中的内容
                //一般这里会有一个axios请求
                this.waiting = true;
                this.tableData = await getAllStock();
                this.waiting = false;
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
        },
        watch: {
            watchSearch: {
                handler(newValue, oldValue){
                    let self = this
                    if(newValue){
                        if(self.value=="creationTime"|| self.value=="modificationTime"){
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
    .bg1{
        background-color: #18ae13;
        padding: 5px 10px;
        color:white;
        border-radius: 5px;
    }
    .bg2{
        background-color: #d54b3b;
        padding: 5px 10px;
        color:white;
        border-radius: 5px;
    }

</style>