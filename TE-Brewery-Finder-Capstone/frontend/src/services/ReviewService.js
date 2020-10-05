import axios from 'axios';

export default {

    getReview(id) {
        return axios.get(`/reviews/${id}`, id);
    },
    createReview(review) {
        console.log(review);
        return axios.post('/reviews/add', review)
    },
    getReviewByBeer(beerId) {
        return axios.get(`/reviews/beer/${beerId}`, beerId)
    },
    getReviewByBrewery(breweryId) {
        return axios.get(`/brewery/${breweryId}/reviews`, breweryId)
    }

}