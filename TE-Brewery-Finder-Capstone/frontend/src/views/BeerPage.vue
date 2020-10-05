<template>
    <div>
        <div class="bigOuter">
        <div class="list">
            <h1> Your Beer Selection : {{beer.beerName}}</h1>
            <h2>"{{beer.description}}"</h2>
            <h4> {{beer.abv}} ABV</h4>
            <h4>Beer Type: {{beer.beerType}}</h4>
            <h4>URL: {{beer.url}}</h4>
            <div class="darkness">{{beer.beerName}} Average Rating:<br> {{ (isNaN(this.avgStar)) ? 'No reviews, yet!' : this.makeBeerIcons(this.avgStar)}}
                <span class="outOf">(out of 5)</span>
            </div>
        </div>


            <button v-on:click="toggleReviewForm" class="reviewClicker">Review this beer</button>
            <form class="review-beer list" v-bind:class="{'hide-form':!showForm}" @submit.prevent="makeNewReview">
                <h3>Review form ~ </h3>
                <label for="title"> Review Title: </label>
                <input id="title" type="text" v-model="newReview.title" placeholder="Title here" />
                <label for="stars"> Rating stars: </label>
                <input id="stars" type="number" v-model="newReview.reviewStars" min="0" max="5" value="3" />
                <label for="body"> Review Body: </label>
                <textarea id="body" name="body" v-model="newReview.reviewBody" rows="4" placeholder="Review here o:<" > </textarea>
                <button type="submit">Submit Review</button>
            </form>
            
        </div>

            <div>
                <button v-on:click="toggleShowReviews" > See Beer's Reviews </button>
                    <br>
                    <div class="cardHolder">
                    <div class ="toggleViewClass" v-bind:class="{'hide-reviews':!showReviews}" v-for="review in reviews" v-bind:key="review.id" >
                        <div class="card">
                        <h6> Title</h6>
                        <h3>"{{review.title}}"</h3>
                        <h4> Stars:  {{review.reviewStars}} </h4>
                        <h6>Review...</h6>
                        <h4>{{review.reviewBody}} </h4>
                        </div> 
                    </div>
                    </div>
            </div>
</div>
</template>

<script>
import BeerService from "../services/BeerService"
import ReviewService from "../services/ReviewService"

export default {
    name: "beer-page",
    data () {
        return {
            beer: {
                beer_id: "",
                beerName: "",
                description: "",
                url: "",
                abv: "",
                beerType: ""
            },
            newReview: {
                userId: this.$store.state.user.id,
                beerId: Number(this.$route.params.id),
                title: '',
                reviewStars: 3,
                reviewBody: ''
            },
            reviews: {
                reviewId: '',
                beerId: '',
                title: '',
                stars: '',
                body: ''
            },
            showForm: false,
            showReviews: false,
            avgStar: 0
        }
    },
    created() {
        BeerService.getBeer(this.$route.params.id).then(response => {
            this.beer = response.data;
        });
        this.showReviewsByBeer();

     },
     methods: {
         toggleReviewForm() {
             this.showForm = !this.showForm;
         },
         toggleShowReviews() {
             this.showReviews = !this.showReviews;
         },
         makeNewReview() {
             ReviewService.createReview(this.newReview).then(response => {
                 if (response.status == 201) {
                    this.$router.go();    
                 }
            });
        },
        showReviewsByBeer() {
            ReviewService.getReviewByBeer(this.$route.params.id).then(response => {
                this.reviews = response.data;
                this.avgStar = this.calcAvgStars(response.data);
            });
        },
        calcAvgStars(reviews) { 
            let total = 0;
            for (let review of reviews) {
                total += review.reviewStars;
            }
            let roundedAvg = total/reviews.length
            return roundedAvg.toFixed(2);
        },
    
        makeBeerIcons(stars) { 
            const roundedStars = Math.round(stars);
            let totalStars = '';
            for (let i = 0; i < roundedStars; i++) {
                totalStars += '🍺';
            }
            return totalStars;
         },
    }
}
</script>
<style>
    
    .review-beer {
        display: flex;
        flex-direction: column;
        width: 200px;
    }
    .review-beer.hide-form {
        display: none;
    }
    .hide-reviews {
        display: none;
    }

    .list {
        width: 30%;
        display: flex;
        flex-direction: column;
        background-image: url("../assets/brew1.jpg");
        background-size: 100% 100%;
        background-repeat: no-repeat;
        margin: 5px;
        color: black;
        padding: 10px;
        border-width: 2px;
        border-style: inset;
        border-top-color: hotpink; 
        border-left-color: yellow;
        border-bottom-color:  cyan;
        border-right-color:  yellow; 
        border-radius: 15px;
    }

    h6 {
        font-size: 1rem;
        margin: 0;
    }

    .outOf {
    font-family: cursive;
    font-size: 16px;
    }

    .darkness {
        padding: 10px;
        color: white;
        background-color: black;
    }

    .cardHolder {
        display: flex;
        width: 100%;
    }

    .card {
        width: 200px;
        display: flex;
        flex-direction: column;
        align-self: stretch;
        background-image: url("../assets/brew1.jpg");
        background-size: 100% 100%;
        background-repeat: no-repeat;
        margin: 5px;
        color: black;
        padding: 10px;
        border-width: 2px;
        border-style: inset;
        border-top-color: hotpink; 
        border-left-color: yellow;
        border-bottom-color:  cyan;
        border-right-color:  yellow; 
        border-radius: 15px;
    }

    .toggleViewClass {
        width: 400px;
        display: flex;
    }

</style>