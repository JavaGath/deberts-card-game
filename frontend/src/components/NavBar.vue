<template>
  <component :is="htmlType" class="bar" :class="htmlType">
    <div v-for="(button, index) in rightMenu" :key="index">
      <div v-if="button.router === true">
        <router-link class="menu" :to="{ name: button.name }">{{
          button.text
        }}</router-link>
      </div>
      <div v-else>
        <a class="menu">{{ button.text }}</a>
      </div>
    </div>
  </component>
</template>

<script>
import '@/assets/styles/main.css'
export default {
  name: 'NavBar',
  props: {
    rightMenu: {
      type: Array,
      required: true
    },
    htmlType: {
      type: String,
      required: true
    }
  }
}
</script>
