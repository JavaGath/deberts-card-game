import axios from 'axios'

const apiClient = axios.create({
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': 'localhost:8080'
  }
})

export default {
  signUp(body) {
    return apiClient.post('api/auth/signup', body).catch(function (error) {
      if (error.response.status === 409 || error.response.status === 500) {
        return error.response
      }
    })
  },
  login(body) {
    return apiClient.post('api/auth/login', body).catch(function (error) {
      if (error.response.status === 401 || error.response.status === 500) {
        return error.response
      }
    })
  }
}
