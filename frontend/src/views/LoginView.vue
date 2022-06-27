<template>
  <div class="login">
    <NavBar
      class="top"
      :right-menu="rightMenuTop"
      :html-type="'header'"
    ></NavBar>
    <LoginForm class="form-center"></LoginForm>
    <NavBar
      class="bot"
      :right-menu="rightMenuBot"
      :html-type="'footer'"
    ></NavBar>
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
      rightMenuTop: [{ name: 'signUp', text: 'Sign Up', buttonType: 'route' }],
      rightMenuBot: []
    }
  }
}
</script>

<style scoped></style>
