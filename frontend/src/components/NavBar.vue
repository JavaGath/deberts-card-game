<template>
  <component :is="htmlType" class="bar" :class="htmlType">
    <div v-for="(button, index) in rightMenu" :key="index">
      <div v-if="button.buttonType === 'route'">
        <router-link class="menu-route" :to="{ name: button.name }">{{
          button.text
        }}</router-link>
      </div>
      <div v-if="button.buttonType === 'active'">
        <a class="menu-route" @click="callMethod(button.name)">{{
          button.text
        }}</a>
      </div>
      <div v-if="button.buttonType === 'label'">
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
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
    },
    callMethod(method) {
      this[method]()
    }
  }
}
</script>
