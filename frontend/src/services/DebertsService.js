import axios from 'axios'

const apiClient = axios.create({
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  }
})

export default {
  test(body) {
    return apiClient.post('http://localhost:8080/user/signup', body)
  }
}
