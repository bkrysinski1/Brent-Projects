<template>
    <div>
        <div class="thisUser2" v-if="this.$store.state.token!=''">ROLE: {{$store.state.role}}</div>
        <form class="user-del" @submit.prevent="deleteUser" autocomplete="off">
            <button type="submit">Delete Account</button>
            Click to Delete Your Account: once you do this, your account cannot be recovered.   
        </form>
    </div>
</template>

<script>
import UserService from "../services/UserService";

export default {
    name: "user-home",
    data() {
        return {
            id: this.$store.state.user.id,
        }
    },
    methods: {
        deleteUser() {
            if (confirm("Are you sure you want to delete your account? All information will be lost and this action cannot be undone.")) {
                UserService.deleteUserById(this.id)
                    .then(response => {
                    if (response.status == 204) {
                        this.$store.commit("LOGOUT");
                        this.$router.push("/login");
                    }
                })
            }
        }
    }
}
</script>