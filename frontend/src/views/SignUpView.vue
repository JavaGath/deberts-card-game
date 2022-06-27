<template>
  <div class="sign-up">
    <NavBar
      class="top"
      :right-menu="rightMenuTop"
      :html-type="'header'"
    ></NavBar>
    <SignUpForm class="form-center"></SignUpForm>
    <NavBar
      class="bot"
      :right-menu="rightMenuBot"
      :html-type="'footer'"
    ></NavBar>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar'
import SignUpForm from '@/components/SignUpForm'

export default {
  name: 'SignUpView',
  components: {
    NavBar,
    SignUpForm
  },
  data() {
    return {
      rightMenuTop: [{ name: 'login', text: 'Sign In', buttonType: 'route' }],
      rightMenuBot: []
    }
  }
}
</script>
