<template>
    <div id="app">
        <!--    <simple-layout :menus="routes"/>-->
        <multi-layout :menus="routes" multi-expand/>
    </div>
</template>

<script>
    export default {
        name: 'App',
        data() {
            return {
                routes: this.$router.options.routes,
            }
        }
    }
</script>

<style lang="less">

</style>
