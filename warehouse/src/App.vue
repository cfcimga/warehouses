<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>
<script>
  export default {

    provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
      return {
        reload: this.reload
      }
    },
    data() {
      return{
        isRouterAlive: true                    //控制视图是否显示的变量
      }
    },
    created () {
      //在页面加载时读取sessionStorage里的状态信息
      if (sessionStorage.getItem("store") ) {
        this.$store.replaceState(Object.assign({}, this.$store.state,JSON.parse(sessionStorage.getItem("store"))))
      }

      //在页面刷新时将vuex里的信息保存到sessionStorage里
      window.addEventListener("beforeunload",()=>{
        sessionStorage.setItem("store",JSON.stringify(this.$store.state))
      })
    },
    methods: {
      reload () {
          this.isRouterAlive = false;            //先关闭，
          this.$nextTick(function () {
            this.isRouterAlive = true;         //再打开
          })
      }
    }
  }


</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}



</style>
