import Vue from 'vue';
import vuex from 'vuex';
import carriers from './carriers.js'
import myUser from './myUser.js'
import customer from './customer.js'
import supplier from './supplier.js'
import user from './user.js'
import warehouse from './warehouse.js'
import reservoirarea from './reservoirarea.js'
import storagerack from './storagerack.js'

Vue.use(vuex);
var store = new vuex.Store({
  modules:{
    carriers,
    myUser,
    customer,
    supplier,
    user,
    warehouse,
    reservoirarea,
    storagerack
  }

});

export default store;
