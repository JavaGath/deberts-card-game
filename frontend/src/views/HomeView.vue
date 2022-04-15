<template>
  <div class="home">
    <NavBar class="top" :right-menu="rightMenuTop"></NavBar>
    <NavBar class="bot" :right-menu="rightMenuBot"></NavBar>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar'
import '@/assets/styles/main.css'

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
