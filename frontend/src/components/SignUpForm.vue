<template>
  <div class="form-center">
    <form @submit.prevent="signUp">
      <h1>¯\_(ツ)_/¯</h1>
      <BaseInput
        v-model="registrationData.username"
        label="Username"
        model-type="text"
      ></BaseInput>
      <p v-if="error.username" aria-live="assertive" class="error">
        {{ error.username }}
      </p>
      <BaseInput
        v-model="registrationData.email"
        label="Email address"
        model-type="email"
      ></BaseInput>
      <p v-if="error.email" aria-live="assertive" class="error">
        {{ error.email }}
      </p>
      <BaseInput
        v-model="registrationData.password"
        label="Password"
        model-type="password"
      ></BaseInput>
      <BaseInput
        v-model="registrationData.passwordCheck"
        label="Repeat your password"
        model-type="password"
      ></BaseInput>
      <p v-if="error.password" aria-live="assertive" class="error">
        {{ error.password }}
      </p>
      <BaseInput
        model-type="submit"
        model-value="Leak data (づ｡◕‿‿◕｡)づ"
      ></BaseInput>
    </form>
  </div>
</template>

<script>
import BaseInput from '@/components/BaseInput'

export default {
  name: 'RegistryForm',
  components: {
    BaseInput
  },
  data() {
    return {
      registrationData: {
        username: '',
        email: '',
        password: '',
        passwordCheck: ''
      },
      error: {
        username: '',
        email: '',
        password: '',
        counter: 0
      }
    }
  },
  methods: {
    checkPassword() {
      if (
        this.registrationData.password !== this.registrationData.passwordCheck
      ) {
        this.error.password = 'Passwords do NOT match!!!'
        this.error.counter++
      } else {
        this.error.password = ''
        this.error.counter = 0
      }
    },
    checkEmail() {
      const regex = /^[a-zA-Z\d._-]+@[a-zA-Z\d.-]+.[a-zA-Z]{2,4}$/
      if (
        this.registrationData.email.length < 6 ||
        this.registrationData.email.length > 51
      ) {
        this.error.email = 'Email must be between 6 and 50 characters long.'
        this.error.counter++
        return
      } else {
        this.error.email = ''
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
    checkUsername() {
      if (
        this.registrationData.username.length < 6 ||
        this.registrationData.username.length > 51
      ) {
        this.error.username = 'Login must be between 6 and 50 characters long.'
        this.error.counter++
      } else {
        this.error.username = ''
        this.error.counter = 0
      }
    },
    signUp() {
      this.checkUsername()
      this.checkPassword()
      this.checkEmail()
      if (this.error.counter === 0) {
        /* eslint-disable no-console */
        console.log('registrationData is:', this.registrationData)
        /* eslint-enable no-console */

        this.$store.dispatch('signup', this.registrationData)
        /*if (response.status === 200) {
          this.error.username = ''
        } else {
          this.error.username = ''
        }*/
      }
    }
  }
}
</script>

<style>
.error {
  color: red;
}
</style>
