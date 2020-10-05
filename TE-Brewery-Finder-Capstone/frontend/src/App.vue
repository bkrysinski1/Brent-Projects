<template>
  <div id="app">
    <div id="nav" v-if="$store.state.token!=''">
      <ul>
      <li>
        <router-link :to="{ name: 'user-home' }" class="thisUser1" v-if="this.$store.state.token!=''">
          {{$store.state.user.username}}
        </router-link>
      </li>
      <li><router-link :to="{ name: 'home' }">HOME</router-link></li>
      <li class="dropdown"><div class="userMenu">
        <a href="javascript:void(0)" class="dropbtn">USER MENU</a>
        <div class="dropdown-content">
        <router-link :to="{ name: 'brewery-list'}">View Breweries</router-link>
        <router-link :to="{ name: 'beer-list' }" >Available Beers</router-link>
        </div>
      </div></li>
      <li class="dropdown"><div v-if="$store.state.role=='ADMIN'" class="adminMenu">
        <a href="javascript:void(0)" class="dropbtn">ADMIN MENU</a>
        <div class="dropdown-content">
        <router-link :to="{ name: 'admin-home' }" >Admin</router-link>
        <router-link :to="{ name: 'brewery-reg'}">Add Brewery</router-link>
        </div>
      </div></li>
      <li class="dropdown"><div v-if="$store.state.role=='BREWER'||$store.state.role=='ADMIN'" class="brewerMenu">
        <a href="javascript:void(0)" class="dropbtn">BREWER MENU</a>
        <div class="dropdown-content">
        <router-link :to="{ name: 'brewery-update' }">Update Brewery</router-link>
        <router-link :to="{ name: 'beer-update' }">Update Beer Form</router-link>
        </div>
      </div></li>
      <li class="logout"><router-link :to="{ name: 'logout' }" v-if="$store.state.token != ''">LOGOUT</router-link></li>
      </ul>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
    state: {
      loggedIn: false,
    },
    created() {
      if (this.$store.state.token!='') {
      let userRole = this.$store.state.user.authorities[0].name.slice(5);
      this.$store.commit('SET_ROLE', userRole);
      this.seeBrewer = (userRole == "BREWER" || userRole == "ADMIN")? true: false;
      this.seeAdmin = (userRole == "ADMIN")? true: false;
      }
    },
}
</script>

<style>

@import url('https://fonts.googleapis.com/css2?family=Josefin+Sans:wght@370&display=swap');

#app  {
  background: #000000d0;
  color: #fe0;
  border: 2px solid black;
  border-radius: 5px;
  padding: 20px;
  justify-items: center;
  font-family: 'Josefin Sans', sans-serif;
  font-size: 1.5em;
}

#nav {
  background: black;
  color: #fe0;
  border-width: 2px;
  border-style: inset;
  border-top-color: hotpink; 
  border-left-color: yellow;
  border-bottom-color:  cyan;
  border-right-color:  yellow;
  border-radius: 5px;
  padding: 20px;
  margin: 10px;
  font-size: .75em;
}

.userMenu,
.adminMenu,
.brewerMenu {
  display: inline-block;
}

body {
  background-image: url("./assets/background2.jpeg");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-attachment: fixed;
}

a {
  color: #fe0;
}



ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

li {
  float: left;
}

li a, .dropbtn, .thisUser1, .thisUser2 {
  display: inline-block;
  color: #fe0;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

.logout {
  background-color: hotpink;
  color: black;
}

li a:hover, .dropdown:hover .dropbtn {
  background-color: #fe0;
  color: black;
}

li.dropdown {
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: hotpink;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
} 

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {background-color: cyan;}

.dropdown:hover .dropdown-content {
  display: block;
}

.thisUser1 {
  height: 15px;
  background-color: cyan;
  color: black;
  border-width: 2px;
  border-style: inset;
  border-top-color: hotpink; 
  border-left-color: yellow;
  border-bottom-color:  cyan;
  border-right-color:  yellow;
}

.thisUser2 {
  height: 15px;
  background-color: hotpink;
  color: black;
  border-width: 2px;
  border-style: inset;
  border-top-color: hotpink; 
  border-left-color: yellow;
  border-bottom-color:  cyan;
  border-right-color:  yellow;
  font-size: .75em;
}

button {
  background-color: rgb(43 4 40);
  border: none;
  color: #da0;
  padding: 9px;
  text-align: center;
  font-family: 'Josefin Sans', sans-serif;
  text-decoration: none;
  display: inline-block;
  font-size: 1em;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 25px;
}

input {
  background-color: rgb(33, 223, 223);
  color:hotpink;
  padding: 4px 4px;
  margin: 8px 0;
  box-sizing: border-box;
  font-size: .75em;
  
}
input:focus {
    background: black;
    border: 4px solid hotpink
}

button:hover,
.sumbit:hover,
.button:hover {
    color: black;
    background-color: linear-gradient(hotpink, yellow, cyan);
    animation-name: vaporwave;
    animation-duration: 1s;
    animation-timing-function: linear;
    animation-iteration-count: infinite;
    transition: vaporwave 4s;
}

@keyframes vaporwave{
    0%   {background-image: linear-gradient(to right, hotpink, hotpink, yellow, yellow, cyan, cyan);}
    16%  {background-image: linear-gradient(to right, hotpink, yellow, yellow, cyan, cyan, hotpink);}
    33%  {background-image: linear-gradient(to right, yellow, yellow, cyan, cyan, hotpink, hotpink);}
    50%  {background-image: linear-gradient(to right, yellow, cyan, cyan, hotpink, hotpink, yellow);}
    66%  {background-image: linear-gradient(to right, cyan, cyan, hotpink, hotpink, yellow, yellow);}
    83%  {background-image: linear-gradient(to right, cyan, hotpink, hotpink, yellow, yellow, cyan);}
    100% {background-image: linear-gradient(to right, hotpink, hotpink, yellow, yellow, cyan, cyan);}
}

textarea {
  width: 100%;
  height: 150px;
  padding: 12px 20px;
  box-sizing: border-box;
  border: 1px solid yellowgreen;
   color: #da0;
  border-radius: 4px;
  background-color: #022629;
  resize: none;
}
</style>