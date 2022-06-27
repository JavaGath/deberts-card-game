<template>
  <div class="home">
    <NavBar
      v-if="isLoggedIn()"
      class="top"
      :right-menu="rightMenuTopLoggedIn"
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
      rightMenuTopLoggedIn: [
        { name: 'logout', text: 'Logout', buttonType: 'active' },
        { name: '', text: this.getUsername(), buttonType: 'label' }
      ],
      rightMenuBot: [{ name: '', text: 'Menu', buttonType: 'active' }]
    }
  },
  computed: {
    ...authComputed
  },
  methods: {
    isLoggedIn() {
      return this.$store.getters['loggedIn']
    },
    getUsername() {
      return this.$store.getters['username']
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
