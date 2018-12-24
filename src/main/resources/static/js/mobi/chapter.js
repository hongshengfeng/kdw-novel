var novel = new Vue({
	el: '#novel',
	data:{
		title: '标题过长会隐藏后面的内容啊哈哈哈哈'
	},
	mounted(){

	},
	methods: {
		allDesc(){
			$(".icon-more").css("transform","rotate(90deg)")
		},
		toBack(){
			window.history.back(-1);
		}
	}
});