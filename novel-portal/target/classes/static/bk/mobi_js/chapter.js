var novelInfo = new Vue({
	el: '#novel',
	data:{
		id: id,
        cid: 0,
        name: '',
        brief: '',
        author: '',
        status: '',
        uptime: '',
        list: null,
        loading: false,
        showAll: false
	},
	mounted(){
		this.chapter();
	},
	methods: {
		chapter(){
			var $self = this;
			this.loading = true;
            $.ajax({
                type: "post",
                async: true,
				jsonType: 'json',
                url: "/chapter/list/" + $self.id,
                success: function(data){
                	$self.list = data;
                	$self.loading = false;
                	$self.showAll = true;
                    $('.list').css('height', '1200px');
                }
            });
		},
        allChapter(){
			$('.list').css('height', 'auto');
            $('.footer').css('margin-top', '0px');
            this.showAll = false;
        },
		chInfo(cId){
			window.location.href= "/m/info/" + id + "/" + cId;
		},
		allDesc(){
			$('.icon-more').css("transform","rotate(90deg)")
		},
		toBack(){
			window.history.back(-1);
		},

	}
});