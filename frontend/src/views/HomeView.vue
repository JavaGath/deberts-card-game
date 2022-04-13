<template>
  <div class="home">
    <nav>
      <router-link to="/"> Home </router-link> |
      <router-link to="/login"> Sign In </router-link> |
      <router-link to="/sign-up"> Sign Up </router-link>
    </nav>
    <img alt="Vue logo" src="../assets/logo.png" />
    <nav>
      <router-link to="/"> About </router-link>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style>
nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a:active {
  color: #42b983;
}
</style>
