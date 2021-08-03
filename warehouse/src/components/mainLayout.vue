<template>
    <div id="main">
        <p id="p1"><span >{{routeName.parentName}}</span> </p>
            <p id="p2"><span class="ttl"> <i class="el-icon-s-home"></i> Home > {{routeName.parentName}} > {{routeName.name}}</span> </p>
        <div class="slt" v-show="routeName.needSlot==true"><slot name="select"></slot></div>
        <el-main id="background">
            <slot></slot>
        </el-main>
    </div>
</template>

<script>
    export default {
        name: "mainLayout",
        props:{
            routeName:{
                require:true,
            }
        },
        created() {
            console.log("nihao")
        }

    }
</script>

<style scoped>

    #main{
        background-color: #f2f3fb;
        height: 800px;
        border: #dfdfdf 1px solid;

    }
    #p1{
        margin:12px 10px;
        float:left;
        font-size: 23px;
        text-align: left;
        line-height: 100%;
        position: relative;

    }
    #p2{
        text-align: right;
    }
    #background{
        background-color: white;
        margin: 12px 18px;
        box-shadow: 0px -3px 3px 0px #b4b4b4;
        border-radius: 3px;

    }
    .slt{
        height:40px;
        background-color: white;
        margin: 15px 5px;
        box-shadow: 0px -1px 0px #b4b4b4;
    }
    #p2 .ttl{
        /*left: 76%;*/
        font-size: 10px;
        margin-right: 5px;
        /*position: relative;*/
    }
</style>