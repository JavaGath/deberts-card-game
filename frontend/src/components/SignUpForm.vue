<template>
  <div class="form-center">
    <form @submit.prevent="registry">
      <h1>¯\_(ツ)_/¯</h1>
      <BaseInput
        v-model="registrationData.username"
        label="Username"
        model-type="text"
      ></BaseInput>
      <BaseInput
        v-model="registrationData.email"
        label="Email address"
        model-type="email"
      ></BaseInput>
      <BaseInput
        v-model="registrationData.password"
        label="Password"
        model-type="password"
      ></BaseInput>
      <BaseInput
        v-model="registrationData.passwordCheck"
        label="repeat your password"
        model-type="password"
      ></BaseInput>
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
      }
    }
  },
  methods: {
    registry() {
      console.log(this.registrationData)
    }
  }
}
</script>
