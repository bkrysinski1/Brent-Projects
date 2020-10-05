<template>
    <div>
        <h1>Check out these Breweries</h1>
        <div class="outer">
        <div class="list" v-for="brewery in breweries" v-bind:key="brewery.id" v-on:click="viewBrewery(brewery.id)" >
            <h3> {{brewery.breweryName}}</h3>
            <h4>{{brewery.contactInfo}}</h4>
            <h4>{{brewery.address}}</h4>
            <p v-if="!brewery.open">Sorry, we're currently closed</p>
            <p v-else-if="brewery.openTime != null && brewery.closeTime != null">Open from {{brewery.openTime}} to {{brewery.closeTime}} </p>
            <p v-else>Times of Operation have not been posted</p>
            <p>{{brewery.history}}</p>
        </div>
        </div>
    </div>
</template>

<script>
import BreweryService from "../services/BreweryService"

export default {
    name: "brewery-list",
    data () {
        return {
            breweries: []
        }
    },
    created() {
            BreweryService.getBreweries().then(response => {
                this.breweries = response.data;
            }) 
    },
    methods: {
        viewBrewery(id) {
            this.$router.push(`/brewery/${id}`);
        }
    }
}
</script>

<style>

.list {
    border-width: 2px;
    border-style: inset;
    border-top-color: hotpink; 
    border-left-color: yellow;
    border-bottom-color:  cyan;
    border-right-color:  yellow;
    border-radius: 15px;
    text-align: center;
    width: 30%;
}

.outer {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    justify-content: center;
}

h3, h4, p {
    background-color: #FFF5;
    padding: 10px;
    margin: 5px;
}

</style>