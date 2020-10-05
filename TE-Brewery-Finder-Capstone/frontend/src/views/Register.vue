<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        @input="checkPassword"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />      
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <div id ="pass-strength">
        <h4>Password Requirements</h4>
        <ul id="strength-list">
          <li v-bind:class="{isValid: containsSixChars}">6 Chararcters</li>
          <li v-bind:class="{isValid: containsNumber}">Contains Number</li>
          <li v-bind:class="{isValid: containsUpper}">Contains Uppercase</li>
          <li v-bind:class="{isValid: containsSpecial}">Contains Special Character</li>
        </ul>
      </div>

      <label for="user-type">User, Brewer, or Admin?</label>
      <select id="user-type" v-model="user.role">
        <option value="user">User</option>
        <option value="brewer">Brewer</option>
        <option value="admin">Admin</option>
      </select>
      <router-link :to="{ name: 'login' }">Have an account?</router-link>
      <button v-show="validPass" class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: '',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      passwordLength: 0,
      containsSixChars: false,
      containsNumber: false,
      containsUpper: false,
      containsSpecial: false,
      validPass: false
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            console.log(error);
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
    checkPassword() {
      const format = /[ !@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/;

      if (this.user.password.length >= 6) {
        this.containsSixChars = true;
      } else {
        this.containsSixChars = false;
      }
      this.containsNumber = /\d/.test(this.user.password);
      this.containsUpper = /[A-Z]/.test(this.user.password);
      this.containsSpecial = format.test(this.user.password);
      
      if (this.containsSixChars === true &&
					this.containsSpecial === true &&
					this.containsUpper === true &&
					this.containsNumber === true) {
						this.validPass = true;			
      } else {
        this.validPass = false;
      }
    }
  }
}
</script>

<style>
#user-type {
  width: 8rem;
}

.isValid {
  color: green;
}

.form-register {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 60%;
}

#pass-strength {
  padding-bottom: 2rem;
}

#strength-list {
  display: flex;
  flex-direction: column;
  
}
</style>
