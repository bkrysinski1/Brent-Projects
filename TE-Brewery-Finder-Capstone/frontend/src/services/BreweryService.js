import axios from 'axios';

export default {

    getBrewery(id) {
        return axios.get(`/brewery/${id}`, id);
    },
    
    getBreweries() {
        return axios.get('/brewery')
    },

    register(brewery) {
        return axios.post('/brewery/registration', brewery)
    },

    updateBrewery(brewery, id) {
        return axios.put(`/brewery/${id}`, brewery);
    },

    assignBrewer(id,brewerId) {
        return axios.put(`/brewery/${id}/brewer/${brewerId}`);
    },

    deleteBrewery(id) {
        return axios.delete(`/brewery/${id}`,id);
    },

    getBreweryBeerList(id) {
        return axios.get(`/brewery/${id}/beer`, id)
    }
}