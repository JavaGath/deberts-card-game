import { createRouter, createWebHistory } from 'vue-router'
import HomeView from './views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('./views/LoginView.vue')
  },
  {
    path: '/signup',
    name: 'signUp',
    component: () => import('./views/SignUpView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const loggedIn = localStorage.getItem('user')
  if (to.matched.some((record) => record.meta.requiresAuth) && !loggedIn) {
    next({ name: 'login' })
  }
  next()
})

export default router
