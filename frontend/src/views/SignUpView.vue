<template>
  <div class="sign-up">
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
    <h1>Here we go</h1>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar'

export default {
  name: 'SignUpView',
  components: {
    NavBar
  },
  data: function () {
    return {
      rightMenuTop: [{ name: 'login', text: 'Sign In', router: true }],
      rightMenuBot: []
    }
  }
}
</script>

<style scoped></style>
