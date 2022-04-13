<template>
  <div class="login">
    <h1>Sign in to deberts</h1>
    <LoginForm msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
import LoginForm from '@/components/LoginForm.vue'

export default {
  name: 'LoginView',
  components: {
    LoginForm
  }
}
</script>

<style scoped></style>
