<template>
    <div>
        <form class="beer-update" @submit.prevent="register">
            <h1>Update Beer Form</h1>
            <label for="beer-id"> Beer ID</label> 
            <input id="beer-id" type="text" v-model="beer.id"/> &nbsp;
            <label for="beer-name">Beer Name</label>
            <input id="beer-name" type="text" v-model="beer.beerName" /> &nbsp;
            <label for="description">Beer Description</label> 
            <input id="description" type="text" v-model="beer.description" /> &nbsp;
            <label for="url">Beer Image</label> 
            <input id="url" type="text" v-model="beer.url" /> &nbsp;
            <label for="abv">Beer ABV</label> 
            <input id="abv" type="text" v-model="beer.abv" /> &nbsp;
            <label for="beer-type">Beer Type</label> 
            <input id="beer-type" type="text" v-model="beer.beerType" /> &nbsp;
            <button class="submit" type="submit">Update Beer</button>
        </form>
    </div>
</template>

<script>
import BeerService from '../services/BeerService'

export default {
    name: "beer-update",
    data() {
        return {
            beer: {
                id: "",
                beerName: "",
                description: "",
                url: "",
                abv: "",
                beerType: "",
            }
        }
    },
    methods: {
        register() {
            BeerService.updateBeer(this.beer, this.beer.id).then(response => {
                if (response.status == 200) {
                    console.log(response);
                    this.$router.push(`/beer/${this.beer.id}`);
                }
            }).catch (error => {
                if (error.response.status == 404) {
                    console.log ("Beer Not Found");
                }
            });
        }
    }
}
</script>

<style>
    .beer-update {
        display: flex;
        flex-direction: column;
        width: 60%;
    }

    input,
    .submit {
        width: 150px;
    }

</style>


