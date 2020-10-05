import axios from 'axios';



export default {
    register(beer) {
        return axios.post('/beer/registration', beer);
    },

    getBeer(id) {
        return axios.get(`/beer/${id}`, id);
    },

    getBeers() {
        return axios.get(`/beer`);
    },

    updateBeer(beer, id) {
        return axios.put(`/beer/${id}`, beer);
    },

    deleteBeer(id) {
        return axios.delete(`/beer/${id}`, id);
    }
}