<template>
  <div>

    <h2>Avatar</h2>
    <hr/>
    <p>Using stand-alone:
    <p/>
    <div class="mb-4">
      <b-avatar></b-avatar>
      <b-avatar variant="primary" text="BV"></b-avatar>
      <b-avatar variant="info" src="https://placekitten.com/300/300"></b-avatar>
      <b-avatar variant="success" icon="people-fill"></b-avatar>
    </div>
    <div class="mb-2">
      <b-avatar src="https://placekitten.com/300/300"></b-avatar>
      <b-avatar src="https://placekitten.com/300/300" size="6rem"></b-avatar>
    </div>

    <div class="mb-2">
      <b-avatar icon="people-fill"></b-avatar>
      <b-avatar icon="star-fill"></b-avatar>
      <b-avatar icon="music-note"></b-avatar>
      <b-avatar icon="star-fill" size="4em"></b-avatar>
    </div>

    <h2>Alert</h2>
    <hr/>
    <b-alert show variant="primary"><a href="#" class="alert-link">Primary Alert</a></b-alert>
    <b-alert show variant="secondary"><a href="#" class="alert-link">Secondary Alert</a></b-alert>
    <b-alert show variant="success"><a href="#" class="alert-link">Success Alert</a></b-alert>
    <b-alert show variant="danger"><a href="#" class="alert-link">Danger Alert</a></b-alert>
    <b-alert show variant="warning"><a href="#" class="alert-link">Warning Alert</a></b-alert>
    <b-alert show variant="info"><a href="#" class="alert-link">Info Alert</a></b-alert>
    <b-alert show variant="light"><a href="#" class="alert-link">Light Alert</a></b-alert>
    <b-alert show variant="dark"><a href="#" class="alert-link">Dark Alert</a></b-alert>
    <b-alert show dismissible>
      Dismissible Alert! Click the close button over there <b>&rArr;</b>
    </b-alert>
  </div>

</template>

<script>
export default {
  name: 'example'
}
</script>

<style scoped>

</style>