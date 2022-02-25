<template>
	<span>
		{{ key }}：{{ value }}
	</span>
</template>

<script>
export default {
  name: "sub-title-text",
  props: [ "key", "value" ]
}
</script>

<style scoped>

</style>