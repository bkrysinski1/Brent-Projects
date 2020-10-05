<template>
    <div>
        <form class="brewery-update" @submit.prevent="register">
        <h1>Brewery Registration Form</h1>
        <label for="brewery-id">Brewery ID</label>
        <input id="brewery-id" type="text" v-model="brewery.id" />
        <label for="brewery-name">Brewery Name</label>
        <input id="brewery-name" type="text" v-model="brewery.breweryName" />
        <label for="contact-info">Contact Info</label>
        <input id="contact-info" type="text" v-model="brewery.contactInfo" />
        <label for="open-time">Open time</label>
        <input id="open-time" type="time" v-model="brewery.openTime" />
        <label for="close-time">Closing Time</label>
        <input id="close-time" type="time" v-model="brewery.closeTime" />
        <label for="address">Address</label>
        <input id="address" type="text" v-model="brewery.address" />
        <label for="history">History</label>
        <input id="history" type="text" v-model="brewery.history" />
        <button class="submit" type="submit">Update</button>
        </form>
    </div>
</template>

<script>
import BreweryService from "../services/BreweryService";

export default {
    name: "brewery-form",
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
            }
        }
    },
    methods: {
        register() {
            BreweryService.updateBrewery( this.brewery , this.brewery.id ).then(response => {
                if (response.status == 200) {
                    console.log(response);
                    //this.$router.push("/");
                }
            }).catch (error => {
                if (error.response.status == 404) {
                console.log("Brewery Not Found");
                }
            });
        }
    }
}
</script>

<style>

.brewery-update {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 60%;
}

input,
.submit {
    width: 100px;
}

</style>