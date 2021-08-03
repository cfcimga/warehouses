import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import store from './store'

import elementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(elementUI);

Vue.config.productionTip = false




axios.interceptors.request.use(
    config => {
        let token = sessionStorage.getItem('Authorization')
      if (token) {
        config.headers.authorization = token;
      }
      return config;
    },
    error => {
      return Promise.reject(error);
    });

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
