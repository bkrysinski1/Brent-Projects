<template>
    <div>
        <div class="outer">
        <form class="brewery-reg" @submit.prevent="register">
        <h1>Brewery Registration Form</h1>
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
        <button type="submit">Register</button>
        </form>
        </div>
    </div>
</template>

<script>
import BreweryService from "../services/BreweryService";

export default {
    name: "brewery-form",
    data() {
        return {
            brewery: {
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
            BreweryService.register(this.brewery).then(response => {
                if (response.status == 201) {
                    this.$router.push("/");
                }
            })
        }
    }
}
</script>

<style>

.brewery-reg {
    align-self: center;
}

input,
.submit {
    width: 100px;
}

#address {
    height: 100px;
}

#history {
    width: 95%;
    height: 100px;
}

form {    
    display: flex;
    flex-direction: column;
    align-self: center;
    background-image: url("../assets/brew1.jpg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    width: 500px;
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
    box-shadow:     0px -10px 15px hotpink,
                    0px 10px 15px cyan,
                    10px 0px 15px yellow,
                    -10px 0px 15px yellow;
}



</style>