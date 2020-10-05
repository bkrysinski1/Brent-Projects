import axios from 'axios';

export default {

  getUsers() {
    return axios.get('/users')
  },

  deleteUser(user) {
    if (user.role != 'admin') {
        return axios.delete(`/users/${user}`);
    }
  },

  deleteUserById(id) {
    return axios.delete(`/users/${id}`);
  },
  
  updateUserRole(id,role) {
    return axios.put(`/users/${id}`,role)
  }

}