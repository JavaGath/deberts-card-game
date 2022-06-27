import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
  state: {
    user: { name: '' }
  },
  getters: {
    loggedIn(state) {
      return (
        state.user.name !== '' ||
        (localStorage.getItem('user') !== '' &&
          localStorage.getItem('user') !== null)
      )
    },
    username(state) {
      if (state.user.name !== '') {
        return state.user.name
      } else if (
        localStorage.getItem('user') !== '' &&
        localStorage.getItem('user') !== null
      ) {
        const result = JSON.parse(localStorage.getItem('user'))
        return result.name
      } else {
        return ''
      }
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
    login({ commit }, credentials) {
      return commit('SET_USER_DATA', credentials)
    },
    logout({ commit }) {
      commit('CLEAR_USER_DATA')
    }
  },
  modules: {}
})
