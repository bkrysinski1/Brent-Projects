<template>
    <div>
        <h1> {{ this.brewery.breweryName }} </h1>
        <div>Contact Information: {{ this.brewery.contactInfo}}</div>
        <div>Address: {{this.brewery.address}}</div>
        <div v-if="this.brewery.openTime != null && this.brewery.closeTime != null">Open from {{brewery.openTime}} to {{brewery.closeTime}}  </div>
        <div v-else>Times of Operation have not been posted</div>
        <div>History: {{this.brewery.history}}</div>
        <hr>
        
        <div v-if="$store.state.role == 'ADMIN'">
            <br/><button type="button" v-on:click="deleteBrewery">Delete this Brewery</button>
        <hr>
        </div>
        
        <div class="inline">
        <h1 id="beer-title">Beer List</h1>
            <div id="beer-search">
                <label for="name-search">Search by Name: </label>
                <input id="name-search" type="text" v-model="filter.beerName" />
                <label for="type-search">Search by Beer Type: </label>
                <input id="type-search" type="text" v-model="filter.beerType" />
            </div>
        </div>
        <div id="flex-beer">
            <div id="beer-list" v-for="(beer, index) in filteredList" :key="index" v-on:click="viewBeer(beer.id)">
                <h2>{{beer.beerName}}</h2>
                <h3>{{beer.beerType}}</h3>
                <h3>{{beer.abv}}</h3>
                <p>{{beer.description}}</p>
            </div>
        </div>
         <div id="reviews">
            <button id="rev-button" v-bind:key="brewery.id" v-on:click="viewReviewsRatings(brewery.id)"> View Reviews and Ratings for  {{this.brewery.breweryName}} </button>
        </div>
        <div v-if="$store.state.role=='BREWER'||$store.state.role=='ADMIN'">
            <h1 id="form-title">Add a Beer Here!</h1>
            <beer-form  id="beer-form" v-bind:breweryId="parseInt($route.params.id)" />
        </div>
    </div>
</template>

<script>
import BreweryService from "@/services/BreweryService"
import BeerForm from "./BeerForm"

export default {
    name: "brewery-info",
    components: {
        BeerForm
    },
    data() { 
        return {
            brewery: {
                id: "",
                breweryName: "",
                contactInfo: "",
                openTime: "",
                closeTime: "",
                address: "",
                history: "",
                isOpen: ""
            },
            beers: [],
            filter: {
                id: "",
                breweryId: "",
                beerName: "",
                description: "",
                url: "",
                abv: "",
                beerType: ""
             }
        }
    },
    created() {
            BreweryService
            .getBrewery(this.$route.params.id)
            .then ( response => {
                if (response.status === 200) {
                    this.brewery = response.data;
                }
            }).catch (error =>  {
                console.log(error);
            });
            BreweryService.getBreweryBeerList(this.$route.params.id).then(response => {
            this.beers = response.data;
            })
    },
    methods: {
        deleteBrewery() {
            if (confirm("Are you sure you want to delete this Brewery and all associated cards? This action cannot be undone.")) {
                BreweryService.deleteBrewery(this.$route.params.id).then(response => {
                    if (response.status == 204) {
                        this.$router.push("/");
                    }
                })
            }
            
        },
        viewReviewsRatings(id) {
            this.$router.push(`${id}/reviews`);
        },
        viewBeer(id) {
            this.$router.push(`/beer/${id}`);
        }
    },
    computed: {
        filteredList() {
            let filteredBeer = this.beers;
            if (this.filter.beerName != "") {
                filteredBeer = filteredBeer.filter(beer => beer.beerName.toLowerCase().includes(this.filter.beerName.toLowerCase()))
            }
            if (this.filter.beerType != "") {
                filteredBeer = filteredBeer.filter(beer => beer.beerType.toLowerCase().includes(this.filter.beerType.toLowerCase()))
            }
            return filteredBeer;
        }
    }
}
</script>

<style>
#brew-info {
    border: 2px solid #da0;
    border-radius: 15px;
    text-align: center;
    padding: 1rem;
}
#beer-list {
    text-align: center;
    width: 25%;
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
#beer-title {
    text-align: center;
}
#flex-beer {
    display: flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
}
#beer-form {
    display: flex;
    justify-content: center;
    width: 500px;
}
#form-title {
    text-align: center;
}
#reviews {
    display: flex;
    justify-content: center;
}
#rev-button {
    background: #da0;
    color: black;
    margin-top: 3rem;
    padding: 3rem;
    border-radius: 15px;
    font-family: 'Josefin Sans', sans-serif;
    font-size: 25px;
}

h3, h4, p {
    background-color: #FFF5;
    padding: 10px;
    margin: 5px;
}

.inline {
    display: flex;
    align-items: center;
    justify-items: center;
}

label {
    margin-left: 30px;
}

</style>