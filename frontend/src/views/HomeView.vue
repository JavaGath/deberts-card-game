<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <LoginForm msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
import LoginForm from '@/components/LoginForm.vue'

export default {
  name: 'HomeView',
  components: {
    LoginForm
  }
}
</script>
