<template>
  <el-container class="navigation1" style="height: 60%; border: 1px solid #eee; ">
    <!--rgb(238, 241, 246)  -->
    <el-aside width="17%" style="background-color: white;">
      <div >
        <el-header id="title1">Warehouse</el-header>
        <div id="user1">
          <span class="headPortrait" ><img :src="headPortrait" alt=""></span>
          <span class="headPortrait1">{{$store.state.myUser.data.uName}}</span>
          <p class="p1">MENU NAVIGATION</p>
        </div>
      </div>
      <el-menu  class="menu1"  router="router" unique-opened>
        <el-submenu   v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.showflag!=false">
          <template slot="title">
            <i :class="item.icon"></i>
            {{item.name}}
          </template>
          <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path" >
            <template slot="title">
              <i :class="item2.icon"></i>
              {{item2.name}}
            </template>
          </el-menu-item>
        </el-submenu>

      </el-menu>
    </el-aside>

    <el-container>
      <el-header id="header1">
        <img :src="headPortrait" alt="" class="portrait">
        <el-dropdown>
          <span class="name">{{$store.state.myUser.data.uName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><router-link :to="{path:'/userInfoModification'}" class="name" >个人信息</router-link></el-dropdown-item>
            <el-dropdown-item><span  class="name" @click="$confirm('是否退出?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
          $router.push('/login');
        }).catch(() => {
          $message({
            type: 'info',
            message: '已取消'
          });
        });">退出</span></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </el-header>

      <router-view/>

      <div class="footer"><p style="float: left;">@2021 <span style="color: #746eff">Warehouse</span></p><p style="text-align: right"><span >帮助电话: 13692423236</span></p></div>

    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "index",
    data(){
      return{

      }
    },
    async created(){


      await this.$store.dispatch("warehouse/fetchDatas")
      await this.$store.dispatch("storagerack/fetchDatas")
      await this.$store.dispatch("reservoirarea/fetchDatas")
      // var userSelf = sessionStorage.getItem("userself")
      // if(userSelf){
      //   await  this.$store.dispatch("myUser/fetchUser",userSelf)
      // }
    },
    methods:{
      logout(){
        this.$confirm('是否退出?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
          if(sessionStorage.removeItem('flag')||
            sessionStorage.removeItem('store')||
          sessionStorage.removeItem('Authorization')){
            sessionStorage.removeItem('flag')
            sessionStorage.removeItem('store');
            sessionStorage.removeItem('Authorization')
          }

          this.$router.push('/login');
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }
  }
</script>
<style>
  .portrait{
    margin-top: 15px;
    margin-right: 10px;
    height: 30px;
    width: 30px;
    border-radius: 50%;
  }
  .footer{

    padding-left: 5px;
    height: 25px;
    font-size: 15px;
    text-align: left;
    line-height: 25px;
    font-weight: bold;

  }
  .name{
    position: relative;
    top: -10px;
    text-decoration: none;
    color:black;
  }
  .navigation1  {
    height: 100%;

  }
  .p1{
    margin-top: 20px;
    text-align: left;
    color: #b4b4b4;
    font-size: 10px;
  }
  #title1{
    font-weight: bold;
    text-align: center;
    text-align: center;
    font-size: 20px;
    color: white;
    background-color: #0d9bc1;
  }
  #user1{
    height: 140px;
    color: black;
    /*background-color: white;*/
  }

  #user1 .headPortrait img{
    display:inline-block;
    height: 80px;
    width: 80px;
    border-radius: 50%;
    background-color: antiquewhite;
  }
  #user1 .headPortrait1{
    display:block;
    font-size: 15px;
    font-weight: bold;

  }


  .menu1{
    /*background-color: #313131;*/
    text-align: left;
  }
  #header1{
    text-align: right;
    font-size: 12px;
    background-color: #0d9bc1;
    text-align: right;
    font-size: 12px;
    color: white
  }
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: white ;
  }
</style>

<script>
  export default {
    data() {
      return {

      }
    },

    created() {

      this.$store.dispatch("warehouse/fetchDatas")
      this.$store.dispatch("reservoirarea/fetchDatas")
      this.$store.dispatch("storagerack/fetchDatas")

      // this.headPortrait='../assets/headPortrait/man.jpg'
    },
    computed:{
      headPortrait(){
        return 'http://localhost:8181/backEndWarehouse/'+this.$store.state.myUser.data.uHeadPortrait;
      }
    }
  };
</script>