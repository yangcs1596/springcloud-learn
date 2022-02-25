// import axios from 'axios'

var svg =
	`<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
					 width="1em" height="1em" focusable="false"  
					 xmlns="http://www.w3.org/2000/svg" fill="currentColor" 
					 class=" b-icon bi text-success" style="font-size: 300%;">
					<path fill-rule="evenodd" d="M4.75 3a1.75 1.75 0 100 3.5 1.75 1.75 0 000-3.5zM1.5 4.75a3.25 3.25 0 116.5 0 3.25 3.25 0 01-6.5 0zM4.75 17.5a1.75 1.75 0 100 3.5 1.75 1.75 0 000-3.5zM1.5 19.25a3.25 3.25 0 116.5 0 3.25 3.25 0 01-6.5 0zm17.75-1.75a1.75 1.75 0 100 3.5 1.75 1.75 0 000-3.5zM16 19.25a3.25 3.25 0 116.5 0 3.25 3.25 0 01-6.5 0z"></path><path fill-rule="evenodd" d="M4.75 7.25A.75.75 0 015.5 8v8A.75.75 0 014 16V8a.75.75 0 01.75-.75zm8.655-5.53a.75.75 0 010 1.06L12.185 4h4.065A3.75 3.75 0 0120 7.75v8.75a.75.75 0 01-1.5 0V7.75a2.25 2.25 0 00-2.25-2.25h-4.064l1.22 1.22a.75.75 0 01-1.061 1.06l-2.5-2.5a.75.75 0 010-1.06l2.5-2.5a.75.75 0 011.06 0z"></path>
					</svg>`;
var cards = [{
		// icon: "bullseye",
		iconHtml: svg,
		title: "XXX应用",
		titleItems: ["扩展点数：" + 5, "能力实例数：" + 23],
		items: [{
				name: "扩展点x1",
				count: 3
			},
			{
				name: "扩展点x2",
				count: 1
			},
			{
				name: "扩展点x3",
				count: 3
			},
			{
				name: "扩展点x4",
				count: 3
			},
		],
	},
	{
		icon: "house",
		iconHtml: "",
		title: "XXX应用2",
		titleItems: ["扩展点数：" + 5, "能力实例数：" + 46],
		items: [{
				name: "扩展点x1扩展点x1扩展点x1扩展点x1扩展点x1扩展点x1扩展点x1",
				count: 3
			},
			{
				name: "扩展点x2",
				count: 5
			},
			{
				name: "扩展点x3",
				count: 6
			},
			{
				name: "扩展点x4",
				count: 1
			},

		],
	}
];

function getApps() {
	return cards;
}

var findApps = (appName = "", extName = "") => {
	let cs = cards
	for (let [i, s] of cs.entries()) {
		if (s.title != appName && extName != s.title) {
			cards.splice(i)
		}
	}

	return cs
}

export default {
	getApps,
	findApps
}
