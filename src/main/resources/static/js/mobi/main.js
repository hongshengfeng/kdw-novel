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
		this.handle(0);
	},
	watch:{
		selected(val) {
			var index = parseInt(val);
			this.handle(index);
		}
	},
	methods: {
        index(){
            window.location.href="/";
        },
        search(){
            window.location.href="/m/search";
        },
		infoClass(cateId){
            var kind = "";
            switch (cateId){
                case 1:
                    kind = "coatard";
                    break;
                case 2:
                    kind = "city";
                    break;
                case 3:
                    kind = "across";
                    break;
                case 4:
                    kind = "fantasy";
                    break;
                case 5:
                    kind = "science";
                    break;
                default:
                    kind = "game";
                    break;
            }
            return kind;
        },
        handle(key){
            var $self = this;
            this.loading = true
            $.ajax({
                type: "post",
                async: true,
				dataType: "json",
                url: "/novel/info/" + key,
                success: function(data){
                	switch(key){
                	case 1:
                        $self.coatInfo = data;
                		break;
            		case 2:
                        $self.cityInfo = data;
	            		break;
            		case 3:
                        $self.acroInfo = data;
	            		break;
            		case 4:
                        $self.fantInfo = data;
	            		break;
            		case 5:
                        $self.scieInfo = data;
	            		break;
            		case 6:
                        $self.gameInfo = data;
	            		break;
	            	default:
                        $self.recoInfo = data;
	            		break;
                	}
                    $self.loading = false;
                }
            });
        },
        more(){
            window.location.href="/m/more";
        },
        info(id){
            window.location.href="/m/chapter/" + id;
        }
	}
});