import index from '@/views/index.vue'

export default{
    routes:[
        {
            path:"/",
            name:"index",
            component:index,
            icon:'el-icon-s-home',
            showflag:false,

        },
        {
          path:'/home',
          name:'Home',
          component:index,
          icon:'el-icon-s-home',
          children:[
              {
                  path:"/inform",
                  name:"通知",
                  icon:'el-icon-bell',
                  component: ()=>import('@/views/inform.vue')            //()=>import('@/view/pageOne.vue')
              }
          ]
        },
        {
            path:"/role",
            name:'角色管理',
            icon:'el-icon-user-solid',
            component:index,
            // show:true,
            // redirect:"pageTwo",
            children:[     //如果使用嵌套路由的话，父路由指向的组件里面需要有router-view标签，否则是点子标签是没用的，如果有了那标签之后，点子组件就会在父组件的router-view标签中显示
                {
                    path:"/carrierManagement",
                    name:"承运商管理",
                    icon:'el-icon-truck',
                    component: ()=>import('@/views/roleManagement/carrierManagement.vue')            //()=>import('@/view/pageOne.vue')
                },
                {
                    path:"/customerManagement",
                    name:"客户管理",
                    icon:'el-icon-s-custom',
                    component:()=>import('@/views/roleManagement/customerManagement.vue')
                },
                {
                    path:"/supplierManagement",
                    name: '供应商管理',
                    icon:'el-icon-wallet',
                    component: ()=>import('@/views/roleManagement/supplierManagement.vue')
                },
            ]
        },
        {
            path:"/systemManagement",
            name:'系统管理',
            icon:'el-icon-s-tools',
            component:index,
            children:
            [
                {
                    path:"/materialManagement",
                    name: '物料管理',
                    icon:'el-icon-collection',
                component: ()=>import('@/views/systemManagement/materialManagement.vue')
                },
                {
                    path:"/userInfoModification",
                    name: '个人信息管理',
                    icon:'el-icon-s-management',
                    component: ()=>import('@/views/systemManagement/userInfoModification.vue')
                },
                {
                    path:"/userManagement",
                    name: '用户信息管理',
                    icon:'el-icon-user',
                    component: ()=>import('@/views/systemManagement/userManagement.vue')
                }
            ]
        },
        {
            path:"/warehouseManagement",
            name:'仓库管理',
            icon:'el-icon-s-cooperation',
            component:index,
            children:[
                {
                    path:"/warehouseManagement",
                    name:'仓库管理',
                    icon:'el-icon-house',
                    component:()=>import('@/views/warehouseManagement/warehouseManagement.vue'),
                },
                {
                    path:"/reservoirAreaManagement",
                    name:'库区管理',
                    icon:'el-icon-place',
                    component:()=>import('@/views/warehouseManagement/reservoirAreaManagement.vue'),
                },
                {
                    path:"/storageRackManagement",
                    name:'货架管理',
                    icon:'el-icon-receiving',
                    component:()=>import('@/views/warehouseManagement/storageRackManagement.vue'),
                },
                {
                    path:"/goodsReceived",
                    name:'入库管理',
                    icon:'el-icon-document-add',
                    component:()=>import('@/views/warehouseManagement/goodsReceived.vue'),
                },
                {
                    path:"/outdeliver",
                    name:'出库管理',
                    icon:'el-icon-document-remove',
                    component:()=>import('@/views/warehouseManagement/outdeliver.vue'),
                },
                {
                    path:"/stockInquiry",
                    name:'库存管理',
                    icon:'el-icon-date',
                    component:()=>import('@/views/warehouseManagement/stockInquiry.vue'),
                },
                {
                    path:"/inventoryRecord",
                    name:'入库记录',
                    icon:'el-icon-document',
                    component:()=>import('@/views/warehouseManagement/inventoryRecord.vue'),
                },
                {
                    path:"/outStockRecord",
                    name:'出库记录',
                    icon:'el-icon-document',
                    component:()=>import('@/views/warehouseManagement/outStockRecord.vue'),
                },
                {
                    path:"/deliverRecord",
                    name:'发货管理',
                    icon:'el-icon-tickets',
                    component:()=>import('@/views/warehouseManagement/deliverRecord.vue'),
                },






            ]
        },
        {
            path:"/login",
            name:"登录",
            showflag:false,
            component:() => import('@/views/login.vue'),
        },
        {
            path:"*",
            name:"无法找到页面",
            showflag:false,
            component:() => import('@/views/NotFound.vue'),
        }




    ],
    mode:"history",

}