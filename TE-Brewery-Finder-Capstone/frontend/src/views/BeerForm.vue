<template>
    <div>
        <form id="beer-reg" @submit="register">
            <label for="beer-name">Beer Name</label> &nbsp;
            <input id="beer-name" type="text" v-model="beer.beerName" /> &nbsp; 
            <label for="description">Beer Description</label> 
            <textarea id="description" v-model="beer.description" /> &nbsp; 
            <label for="url">Beer Image</label> 
            <input id="url" type="text" v-model="beer.url" /> &nbsp; 
            <label for="abv">Beer ABV</label> 
            <input id="abv" type="text" v-model="beer.abv" /> &nbsp; 
            <label for="beer-type">Beer Type</label> 
            <input id="beer-type" type="text" v-model="beer.beerType" /> &nbsp; 
            <button class="submit" type="submit">Add Beer</button>
        </form>
    </div>
</template>

<script>

import BeerService from "../services/BeerService";

export default {
    name: "beer-form",
    props: {
        breweryId: {
            type: Number
        }
    },
    data() {
        return {
            beer: {
                beerName: "",
                breweryId: this.breweryId,
                description: "",
                url: "",
                abv: "",
                beerType: "",
            }
        }
    },
    methods: {
        register() {
            BeerService.register(this.beer).then(response => {
                if (response.status == 201) {
                    this.$router.push(`/brewery/${this.breweryId}`);
                } if (response.status == 403) {
                    alert("You do not have permission to add a Beer");
                }
            })
        },
        viewBeer() {
            
        }
    }
}
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Josefin+Sans:wght@370&display=swap');


#beer-reg {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

input,
#submit {
    width: 200px;
}

textarea {
    width: 20rem;
    background: rgb(33, 223, 223);
    color: black;
    font-family: 'Josefin Sans', sans-serif;
    font-size: 1.2rem;
}
</style>