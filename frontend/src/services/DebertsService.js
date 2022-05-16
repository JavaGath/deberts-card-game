import axios from 'axios'

const apiClient = axios.create({
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  }
})

export default {
  signUp(body) {
    console.log(body)
    return apiClient.post('http://localhost:8080/user/signup', body)
  }
}
