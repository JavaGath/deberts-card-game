import { createApp, h } from 'vue'
import App from './App.vue'
import router from './router'
import store from './vuex/store'

createApp({
  created() {
    const userString = localStorage.getItem('user')
    if (userString) {
      const userData = JSON.parse(userString)
      this.$store.commit('SET_USER_DATA', userData)
    }
  },
  render: () => h(App)
})
  .use(store)
  .use(router)
  .mount('#app')
