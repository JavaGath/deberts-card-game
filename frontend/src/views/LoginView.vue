<template>
  <div class="login">
    <NavBar
      class="top"
      :right-menu="rightMenuTop"
      :html-type="'header'"
    ></NavBar>
    <NavBar
      class="bot"
      :right-menu="rightMenuBot"
      :html-type="'footer'"
    ></NavBar>
    <h1>Sign in to deberts</h1>
    <LoginForm msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
import LoginForm from '@/components/LoginForm.vue'
import NavBar from '@/components/NavBar'

export default {
  name: 'LoginView',
  components: {
    LoginForm,
    NavBar
  },
  data: function () {
    return {
      rightMenuTop: [{ name: 'signUp', text: 'Sign Up', router: true }],
      rightMenuBot: []
    }
  }
}
</script>

<style scoped></style>
