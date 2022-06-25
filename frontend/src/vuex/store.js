import { createStore } from 'vuex'
import DebertsService from '@/services/DebertsService'
import axios from 'axios'

export default createStore({
  state: {
    user: { name: '' }
  },
  getters: {
    loggedIn(state) {
      return state.user.name !== ''
    },
    username(state) {
      return state.user.name
    }
  },
  mutations: {
    SET_USER_DATA(state, userData) {
      state.user = userData
      localStorage.setItem('user', JSON.stringify(userData))
      axios.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${userData.token}`
    },
    CLEAR_USER_DATA() {
      localStorage.removeItem('user')
      location.reload()
    }
  },
  actions: {
    signup({ commit }, credentials) {
      return DebertsService.signUp(credentials).then(({ data }) => {
        commit('SET_USER_DATA', data)
      })
    },
    login({ commit }, credentials) {
      return DebertsService.login(credentials).then(({ data }) => {
        commit('SET_USER_DATA', data)
      })
    },
    logout({ commit }) {
      commit('CLEAR_USER_DATA')
    }
  },
  modules: {}
})
