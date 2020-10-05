<template>
    <div>
        <h1>Our Current List of Beers</h1>
        <div class="outer">
        <div class="list" v-for="beer in beers" v-bind:key="beer.id" v-on:click="viewBeer(beer.id)">
            <h2>{{beer.beerName}}</h2>
            <h3>{{beer.beerType}}</h3>
            <h3>{{beer.abv}}</h3>
            <h5>{{beer.description}}</h5>
        </div>
        </div>
    </div>
</template>

<script>
import BeerService from '../services/BeerService'

export default {
    name: "beer-list",
    data() {
        return {
            beers: []
        }
    },
    created() {
        BeerService.getBeers().then(response => {
            this.beers = response.data;
        })
    },
    methods: {
        viewBeer(id) {
            this.$router.push(`/beer/${id}`);
        }
    }
}
</script>

<style scoped>

.list {
    text-align: center;
    width: 20%;
    background-image: url("../assets/brew1.jpg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    margin: 5px;
    color: black;
    padding: 10px;
    margin: 20px;
    border-width: 2px;
    border-style: inset;
    border-top-color: hotpink; 
    border-left-color: yellow;
    border-bottom-color:  cyan;
    border-right-color:  yellow; 
    border-radius: 15px;
    box-shadow:     0px -10px 15px hotpink,
                    0px 10px 15px cyan,
                    10px 0px 15px yellow,
                    -10px 0px 15px yellow;
    }

.outer {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    justify-content: space-evenly;
}

h3, h2, h5 {
    background-color: #FFF5;
    padding: 10px;
    margin: 5px;
}

</style>