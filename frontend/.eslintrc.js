module.exports = {
  extends: ['eslint:recommended', 'plugin:vue/vue3-recommended', 'prettier'],

  plugins: ['prettier'],

  env: {
    browser: true,
    amd: true,
    node: true
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'prettier/prettier': ['error'],
    quotes: [2, 'single', 'avoid-escape']
  }
}
