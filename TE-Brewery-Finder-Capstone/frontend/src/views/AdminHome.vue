<template>
    <div>
        <h1>Welcome to the Admin Page:</h1>
        <div class="outer">
        <form class="beer-del" @submit.prevent="deleteBeer" autocomplete="off">
            <h3>Delete a Beer</h3>
            <h6>Click to display list or start typing to filter choices
            </h6>
            <input type="text" list="beers" id="beerSelect">
            <datalist id="beers">
                <option v-for="b in beers" :value="b.id + ': ' + b.beerName" :key="b.beerId"/>
            </datalist>
            <br/><button type="submit">Delete</button>
        </form>

        <form class="user-del" @submit.prevent="deleteUser" autocomplete="off">
            <h3>Delete a User</h3>
            <h6>Click to display list or start typing to filter choices</h6>
            <input type="text" list="users" id="userSelect">
            <datalist id="users">
                <option v-for="u in users" :value="u.id + ': ' + u.username" :key="u.id"/>
            </datalist>
            <br/><button type="submit">Delete</button>
        </form>

            <form class="user-role" @submit.prevent="assignUserRole" autocomplete="off">
            <h3>Assign Role to User</h3>
            <h6>Click to display list or start typing to filter choices</h6>
            <input type="text" list="users" id="userAssign">
            <datalist id="users">
                <option v-for="u in users" :value="u.id + ': ' + u.username" :key="u.id"/>
            </datalist><br><br>
                <label for="user-type" class="titleBox">Switch to Role</label>
                <br><br>
                <select id="user-type" v-model="roleSwitch.name">
                    <option value="user">User</option>
                    <option value="brewer">Brewer</option>
                    <option value="admin">Admin</option>
                </select>
            <br/><button type="submit">Assign</button>
        </form>

        <form class="brewery-del" @submit.prevent="deleteBrewery" autocomplete="off">
            <h3>Delete a Brewery</h3>
                <h6>Click to display list or start typing to filter choices</h6>
            <input type="text" list="breweries" id="brewerySelect">
            <datalist id="breweries">
                <option v-for="br in breweries" :value="br.id + ': ' + br.breweryName" :key="br.breweryId"/>
            </datalist>
            <br/><button type="submit">Delete</button>
        </form>

        <form class="brewer-assign" @submit.prevent="assignBrewer" autocomplete="off">
            <h3>Assign Brewer to Brewery</h3>
            <h6>Choose Brewery</h6>
            <input type="text" list="breweries" id="brewerBrewery">
            <datalist id="breweries">
                <option v-for="br in breweries" :value="br.id + ': ' + br.breweryName" :key="br.breweryId"/>
            </datalist>
            <h6>Choose Brewer</h6>
            <input type="text" list="brewers" id="brewerSelect">
            <datalist id="brewers">
                <option v-for="b in brewers" :value="b.id + ': ' + b.username" :key="b.id"/>
            </datalist>
            <br/><button type="submit">Assign</button>
        </form>
        </div>
    </div>
</template>

<script>
import BreweryService from "../services/BreweryService";
import BeerService from "../services/BeerService";
import UserService from "../services/UserService";

export default {
    name: "admin-home",
    data() {
        return {
            roleSwitch: {
                name: ""
            },
            beers: [],
            beer: {
                id: 0,
                beerName: "",
                description: "",
                abv: "",
                beerType: ""
            },
            breweries: [],
            users: [],
            brewers: [],
            brewery: {
                id: 0,
                userId: 0,
                breweryName: "",
                contactInfo: "",
                openTime: "",
                closeTime: "",
                address: "",
                history: "",
            }
        }
    },
    created() {
            BreweryService.getBreweries().then(response => {
                this.breweries = response.data;
                })
            BeerService.getBeers().then(response => {
                this.beers = response.data;
                })
            UserService.getUsers().then(response => {
                    this.users = response.data.filter( user => {
                        return (user.authorities[0].name.includes("USER") ||
                                user.authorities[0].name.includes("BREWER"));
                    })
                })
            UserService.getUsers().then(response => {
                    this.brewers = response.data.filter( user => {
                        return user.authorities[0].name.includes("BREWER");
                    })
                })
    },
    methods: {
        deleteBrewery() {
            if (confirm("Are you sure you want to delete this Brewery and all associated cards? This action cannot be undone.")) {
                let del = document.getElementById("brewerySelect").value.split(":").splice(0,1);
                BreweryService.deleteBrewery(del)
                    .then(response => {
                    if (response.status == 204) {
                        this.$router.push("/");
                    }
                })
            }
        },
        deleteBeer() {
            if (confirm("Are you sure you want to delete this Beer? This action cannot be undone.")) {
                let del = document.getElementById("beerSelect").value.split(":").splice(0,1);
                BeerService.deleteBeer(del)
                    .then(response => {
                    if (response.status == 204) {
                        this.$router.push("/");
                    }
                })
            }
        },
        assignBrewer() {
            if (confirm("Assigning this Brewer to this Brewery will override any other Brewers already assigned.")) {
                let id = document.getElementById("brewerBrewery").value.split(":").splice(0,1);
                let brewerNo = document.getElementById("brewerSelect").value.split(":")[0];
                BreweryService.assignBrewer(id,brewerNo)
                    .then(response => {
                    if (response.status == 200) {
                        this.$router.push("/");
                    }
                });
            }
        },
        deleteUser() {
            if (confirm("Are you sure you want to delete this User? This action cannot be undone.")) {
                let del = document.getElementById("userSelect").value.split(":").splice(0,1);
                UserService.deleteUser(del)
                    .then(response => {
                    if (response.status == 204) {
                        this.$router.push("/");
                    }
                })
            }
        },
        assignUserRole() {
            let id = document.getElementById("userAssign").value.split(":").splice(0,1);
            UserService.updateUserRole(id,this.roleSwitch)
                .then(response => {
                    if (response.status == 200) {
                        this.$router.push("/");
                    }
                })
        }
    }
}
</script>

<style scoped>

form {
    background-image: url("../assets/brew1.jpg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    width: 20%;
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

.outer {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    justify-content: space-evenly;
    align-items: stretch;
}

.titleBox,
h6 {
    background-color: #FFFA;
    padding: 10px;
    margin: 5px;
}

label {
    padding: 30px;
}

select,
button,
input {
    margin: 5px;
}

h3 {
    text-align: center;
}

</style>