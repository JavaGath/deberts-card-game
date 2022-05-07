<template>
  <div class="form-center">
    <form>
      <h1>¯\_(ツ)_/¯</h1>
      <label>Username</label>
      <br />
      <input type="text" />
      <label>Email address</label>
      <br />
      <input type="email" />
      <label>Password</label>
      <br />
      <input type="password" />
      <label>Repeat your password</label>
      <br />
      <input type="password" />
      <input type="submit" value="Leak Data (づ｡◕‿‿◕｡)づ" />
    </form>
  </div>
</template>

<script>
export default {
  name: 'RegistryForm'
}
</script>

<style scoped>
label,
input {
  display: flex;
}

label {
  font-size: 10px;
  float: left;
}

input {
  background-color: white;
  background-position: 10px 10px;
  background-repeat: no-repeat;
  padding-left: 6px;
  margin-bottom: 15px;
  height: 20px;
  font-size: 18px;
}

input[type='submit'] {
  display: inline;
  height: 30px;
  float: none;
  background-color: #e476fa;
}
</style>
