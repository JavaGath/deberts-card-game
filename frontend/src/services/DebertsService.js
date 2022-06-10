import axios from 'axios'

const apiClient = axios.create({
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  }
})

export default {
  signUp(body) {
    return apiClient.post('api/auth/signup', body)
  }
}
