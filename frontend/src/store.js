import { createStore } from 'vuex'
import DebertsService from '@/services/DebertsService'
import axios from 'axios'

export default createStore({
  state: {
    user: null
  },
  getters: {},
  mutations: {
    SET_USER_DATA(state, userData) {
      this.state.user = userData
      localStorage.setItem('user', JSON.stringify(userData))
      axios.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${userData.token}`
    }
  },
  actions: {
    signup({ commit }, credentials) {
      return DebertsService.signUp(credentials).then(({ data }) => {
        commit('SET_USER_DATA', data)
      })
    }
  },
  modules: {}
})
