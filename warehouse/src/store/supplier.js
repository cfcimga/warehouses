import {getAllSupplier} from "@/service/supplierService";

export default{
    namespaced:true,//开启命名空间
    state:{
        data:null,
        isLoading:false,
    },
    mutations:{
        //这里可以设置多种变异方式
        /*mutation是vuex数据变化的唯一方式,如果组件需要改变vuex的数据的,
            则通过commit提交事件来实现,例如在组件中使用 this.$store.commit("channels/setIsLoading",true)*/
        //state:原来的状态
        //payLoad:负荷,数据要改变的值
        setIsLoading(state,payLoad){
            state.isLoading = payLoad;
        },
        setData(state,payLoad){
            state.data = payLoad;
        },

    },
    actions:{
        //mutation中不能触发副作用操作
        /*
            副作用操作:
            .改动或使用外部数据
            .ajax
            .其他的异步操作

        */
        //处理副作用操作,需要在action里面来执行
        async fetchDatas(context){ //context表示当前仓库,相当于其他组件中的this.$store.
            //设置isLoading为true,记住mutation是vuex改变数据的唯一方式,所以这里不能直接修改
            context.commit("setIsLoading",true);   //用于显示加载动画
            let resp = await getAllSupplier();
            if(resp != null){
                context.commit("setData",resp);
            }
            context.commit("setIsLoading",false);

        },

    },
}