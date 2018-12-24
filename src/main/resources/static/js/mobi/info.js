var novel = new Vue({
	el: '#novel',
	data:{
		title: '标题过长会隐藏后面的内容啊哈哈哈哈',
		loading: false,
		isEnd: false,
		infoList: [],
		page: 0,
	},
	mounted(){
		this.info();
		console.log(this.infoList);
	},
	methods: {
		toBack(){
			window.history.back(-1);
		},
		loadMore() {
			this.loading = true;
			setTimeout(() => {
				this.info();
				this.loading = false;
			}, 2500);
		},
		info(){
			var chapter = {};
			chapter.title = "哈哈哈哈哈哈哈哈";
			chapter.content = "哈哈哈哈哈啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈哈哈哈";
			this.infoList.push(chapter);
		}
	}
});