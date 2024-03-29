import Vue from 'vue'
import App from './App.vue'
import router from './router'
import enLocale from 'element-ui/lib/locale/lang/en'
import locale from 'element-ui/lib/locale'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/global.css'


Vue.config.productionTip = false
Vue.use(ElementUI, {size: "small"});

locale.use(enLocale);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
