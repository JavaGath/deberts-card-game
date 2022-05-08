<template>
  <div class="form-center">
    <form>
      <h1>¯\_(ツ)_/¯</h1>
      <BaseInput label="Username" input-type="text"></BaseInput>
      <BaseInput label="Email address" input-type="email"></BaseInput>
      <BaseInput label="Password" input-type="password"></BaseInput>
      <BaseInput label="Repeat your password" input-type="password"></BaseInput>
      <BaseInput
        input-type="submit"
        input-value="Leak Data (づ｡◕‿‿◕｡)づ"
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
  }
}
</script>
