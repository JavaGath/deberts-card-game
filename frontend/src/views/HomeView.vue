<template>
  <div class="home">
    <NavBar
      v-if="test2()"
      class="top"
      :right-menu="rightMenuTop"
      :html-type="'header'"
    ></NavBar>
    <NavBar
      class="bot"
      :right-menu="rightMenuBot"
      :html-type="'footer'"
    ></NavBar>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar'
import '@/assets/styles/main.css'
import { authComputed } from '@/vuex/helpers'

export default {
  name: 'HomeView',
  components: {
    NavBar
  },

  data: function () {
    return {
      rightMenuTop: [
        { name: 'login', text: 'Sign In', router: true },
        { name: 'signUp', text: 'Sign Up', router: true }
      ],
      rightMenuBot: [{ name: '', text: 'Menu', router: false }]
    }
  },
  computed: {
    ...authComputed
  },
  methods: {
    test2() {
      return this.$store.getters['loggedIn']
    }
  }
}
</script>

<style>
nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a:active {
  color: #42b983;
}
</style>
