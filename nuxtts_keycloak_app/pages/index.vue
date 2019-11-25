<template>
  <div class="container">
    <div>
      <h1 class="title">nuxtts_keycloak_app</h1>
      <div class="links">
        <nuxt-link to="/users" class="button--green">Users Page</nuxt-link>
        <nuxt-link to="/admin" class="button--green">Admin Page</nuxt-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component({
  components: {
    Logo: () => import("../components/Logo.vue")
  }
})
export default class Index extends Vue {}
</script>

<style lang="sass">
.container
  margin: 0 auto;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;

.title
  font-family: 'Quicksand', 'Source Sans Pro', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  display: block;
  font-weight: 300;
  font-size: 100px;
  color: #35495e;
  letter-spacing: 1px;


.subtitle
  font-weight: 300;
  font-size: 42px;
  color: #526488;
  word-spacing: 5px;
  padding-bottom: 15px;

.links
  padding-top: 15px;

</style>
