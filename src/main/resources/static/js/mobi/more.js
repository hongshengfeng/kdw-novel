var novel = new Vue({
	el: '#novel',
	data:{
		selected: '0',
		recoInfo: null, //推荐
		coatInfo: null, //修真
		cityInfo: null, //都市
		acroInfo: null, //穿越
		fantInfo: null, //玄幻
		scieInfo: null, //科幻
		gameInfo: null, //网游
		show: true,
		loading: false
	},
	mounted(){
		$('#novel').css('display', 'block');
	},
	methods: {
        toBack(){
            window.history.back(-1);
		},
		more(){
			this.loading = true;
        }
	}
});