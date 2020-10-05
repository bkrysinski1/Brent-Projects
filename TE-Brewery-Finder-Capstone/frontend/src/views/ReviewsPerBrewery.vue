<template>
<div>
    <div>
        <h3>Highest Rated Beer: {{this.beegBeerName}} </h3>
        <h4>     Avg Rating:  {{ (isNaN(this.beegBeerStar)) ? 'n/a' : this.makeBeerIcons(this.beegBeerStar) }} 
            <span class="outOf">(out of 5)</span>
        </h4>
        <h3>Lowest Rated Beer: {{this.weeBeerName}} </h3>
        <h4>     Avg Rating: {{ (isNaN(this.weeBeerStar)) ? 'n/a' : this.makeBeerIcons(this.weeBeerStar)}} 
              <span class="outOf">(out of 5)</span>
        </h4>
    </div>
     <button  v-on:click="toggleShowReviews" > Show all beer reviews for this brewery </button>
    <div v-for="review in reviews" v-bind:key="review.id" v-bind:class="{'hide-reviews':!show}"> 
        <h2> "{{review.title}}"</h2>
        <h3> For Beer: {{review.beerName}}</h3>
        <h3> Stars: {{review.reviewStars}}</h3>
        <h3> Body: {{review.reviewBody}}</h3>
       
    </div>
</div>
</template>

<script>
import ReviewService from "../services/ReviewService"
import BeerService from "../services/BeerService"

export default {
    name: "brewery-reviews",
    data () {
        return{
        reviews: {
                reviewId: '',
                beerId: '',
                title: '',
                stars: '',
                body: ''
            },
        show: false,
        beegBeerName: '',
        weeBeerName: '',
        beegBeerStar: 0,
        weeBeerStar: 0
        }
    },
    created() {
        ReviewService.getReviewByBrewery(this.$route.params.breweryId).then(response => {
            this.reviews = response.data;
            this.bigStar = this.calcHighestReview(response.data);
        });
     }, 
     methods: {
         toggleShowReviews() {
             this.show = !this.show;
         },
         calcHighestReview(reviews) {
            let groupedBeerReviews = reviews.reduce(function(rv, x) {
                    (rv[x['beerId']] = rv[x['beerId']] || []).push(x);
                    return rv;
                }, {});
            let avgBeerReviews = [];
            for (let beers in groupedBeerReviews) {
                let total = 0;
                for (let b of groupedBeerReviews[beers]) {
                    total += b.reviewStars;
                }
                let roundedAvg = total/groupedBeerReviews[beers].length
                avgBeerReviews.push({'beerId': beers, 'avgStars':roundedAvg.toFixed(2)});
            }
            let beegStar = avgBeerReviews.reduce((max, br) => br.avgStars > max.avgStars ? br : max, avgBeerReviews[0]);
            let weeStar = avgBeerReviews.reduce((min, br) => br.avgStars < min.avgStars ? br : min, avgBeerReviews[0]);

            BeerService.getBeer(beegStar.beerId).then(response => {this.beegBeerName = response.data.beerName;});  
            BeerService.getBeer(weeStar.beerId).then(response => {this.weeBeerName = response.data.beerName;}); 
            this.beegBeerStar = beegStar.avgStars;
            this.weeBeerStar = weeStar.avgStars;
         },
         makeBeerIcons(stars) { 
            const roundedStars = Math.round(stars);
            let totalStars = '';
            for (let i = 0; i < roundedStars; i++) {
                totalStars += '🍺';
            }
            return totalStars;
         }
     },   
}
</script>

<style>
.hide-reviews {
    display: none;
}
.outOf {
    font-family: cursive;
    font-size: 16px;
}
</style>