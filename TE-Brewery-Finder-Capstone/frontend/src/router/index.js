import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import BreweryForm from '../views/BreweryForm.vue'
import AdminHome from '../views/AdminHome.vue'
import UpdateBrewery from '../views/UpdateBrewery.vue'
import BreweryList from '../views/BreweryList'
import BreweryDisp from "../views/BreweryDisp.vue"
import BeerPage from '../views/BeerPage'

import BeerForm from '../views/BeerForm'
import BeerList from '../views/BeerList'
import UserHome from '../views/UserHome'
import UpdateBeer from '../views/UpdateBeer'
import ReviewsPerBrewery from "../views/ReviewsPerBrewery"

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/brewery/registration",
      name: "brewery-reg",
      component: BreweryForm,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/brewery/update",
      name: "brewery-update",
      component: UpdateBrewery,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/admin",
      name: "admin-home",
      component: AdminHome,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/brewery",
      name: "brewery-list",
      component: BreweryList,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/brewery/:id", 
      name: "brewery-display",
      component: BreweryDisp,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/beer/:id",
      name: "beer-page",
      component: BeerPage,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/beer/register",
      name: "beer-register",
      component: BeerForm,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/beer",
      name: "beer-list",
      component: BeerList,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/user",
      name: "user-home",
      component: UserHome,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/brewery/:id/beer",
      name: "brewery-beer-list",
      component: BreweryDisp,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/beer/update",
      name: "beer-update",
      component: UpdateBeer,
      meta: {
        requiresAuth: true
        }
      },
      {
        path: "/brewery/:breweryId/reviews",
        name: "brewery-reviews",
        component: ReviewsPerBrewery,
        meta: {
          requiresAuth: true
        }
    },
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
//I added this line because Jacob messed up