<template>
  <div>
    <NavBar
      :html-type="'header'"
      :right-menu="rightMenuTop"
      class="top"
    ></NavBar>
    <h1>Page is not found, try again!</h1>
    <NavBar
      :html-type="'footer'"
      :right-menu="rightMenuBot"
      class="navbar bot"
    ></NavBar>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar'

export default {
  name: 'NotFoundView',
  components: {
    NavBar
  },
  data: function () {
    return {
      rightMenuTop: [],
      rightMenuBot: []
    }
  }
}
</script>

<style scoped>
.navbar {
  overflow: hidden;
  position: fixed;
  bottom: 0;
  width: 100%;
}

h1 {
  color: white;
  font-size: 80px;
  text-transform: uppercase;
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  text-align: center;
}
</style>
