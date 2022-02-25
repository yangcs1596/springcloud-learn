<template>
	<div class="card p-2 m-2 t-card-container">
		<div class="card-header m-0 p-1 bg-transparent">
			<div class="d-sm-inline-flex">
				<div class="h5" v-if="card.icon">
					<b-icon :icon="card.icon" variant="info" font-scale="3" class=" " />
				</div>
				<div class="h5" v-if="card.iconHtml && !card.icon" v-html="card.iconHtml" />
				<div class="pl-3 ">
					<h6 class="card-title">{{card.title}}</h6>
					<div class="card-subtitle">
						<div v-for="item of card.titleItems" :key="item">{{ item }}</div>
					</div>
				</div>
			</div>
		</div>

		<div class="card-body p-1 m-1">
			<div :key="item.name+index" v-for="(item,index) of card.items" v-show="index<maxLine">
				<div class="d-flex mb-1 " v-if="index<3">
					<div class="text-truncate">{{index+1}}. {{ item.name }}</div>
					<div class="pl-1">
						<b-badge variant="info">{{ item.count }}</b-badge>
					</div>
				</div>
			</div>
		</div>
		<div class="card-footer border-0 bg-transparent d-flex pb-0  justify-content-end" v-if="card.items.length<=maxLine">
			<b-link href="#more">更多...</b-link>
		</div>

	</div>
</template>

<script>
	// var card = {
	//   icon: String,
	//   iconHtml: String,
	//   title: String,
	//   titleItems: Int32Array,
	//   items: Array,
	// }

	export default {
		name: "app-card",
		props: ["card", "maxLine"],
		components: {}
	}
</script>

<style scoped>
	.app-card-footer {}
</style>
