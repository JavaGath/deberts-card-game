<template>
  <div class="form-center">
    <form @submit.prevent="login">
      <h2>┐_(ツ)_┌━☆ﾟ.*･｡ﾟ</h2>
      <BaseInput
        v-model="registrationData.login"
        label="Username or Password"
        model-type="text"
      ></BaseInput>
      <p v-if="error.login" aria-live="assertive" class="error">
        {{ error.login }}
      </p>
      <!-- TODO: Service forgot password -->
      <a href="/">Forgot password?</a>
      <BaseInput
        v-model="registrationData.password"
        label="Password"
        model-type="password"
      ></BaseInput>
      <label>New to deberts?</label>
      <a href="/signup">Create an account</a>
      <BaseInput
        model-type="submit"
        model-value="Summon data *(۞_۞)*"
      ></BaseInput>
    </form>
  </div>
</template>

<script>
import BaseInput from '@/components/BaseInput'

export default {
  name: 'LoginForm',
  components: {
    BaseInput
  },
  data() {
    return {
      registrationData: {
        login: '',
        password: ''
      },
      error: {
        login: '',
        password: '',
        counter: 0
      }
    }
  },
  methods: {
    checkLogin() {
      const regex = /^[a-zA-Z\d._-]+@[a-zA-Z\d.-]+.[a-zA-Z]{2,4}$/
      if (
        this.registrationData.login.length < 6 ||
        this.registrationData.login.length > 51
      ) {
        this.error.login = 'Login must be between 6 and 50 characters long.'
        this.error.counter++
        return
      } else {
        this.error.login = ''
        this.error.counter = 0
      }

      if (!this.registrationData.email.match(regex)) {
        this.error.email = 'Please enter your mail in the correct format.'
        this.error.counter++
      } else {
        this.error.email = ''
        this.error.counter = 0
      }
    },
    login() {
      this.checkLogin()
      if (this.error.counter === 0) {
        this.$store.dispatch('login', this.registrationData).then(() => {
          this.error.username = ''
          this.$router.push({ name: 'home' })
        })
      }
    }
  }
}
</script>

<style scoped>
label {
  font-size: 13px;
  float: left;
}

.error {
  color: red;
}
</style>
