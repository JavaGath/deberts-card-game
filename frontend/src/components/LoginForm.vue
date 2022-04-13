<template>
  <div class="login-form">
    <form>
      <label>Username or email address</label>
      <input type="text" />
      <br />
      <label>Password</label>
      <a href="/">Forgot password?</a>
      <input type="password" />
      <br />
      <input type="submit" value="Let's Play" />
    </form>
    <form>
      <label>New to deberts?</label>
      <a href="/sign-in">Create an account</a>
    </form>
  </div>
</template>

<script>
export default {
  name: 'LoginForm'
}
</script>

<style scoped></style>
