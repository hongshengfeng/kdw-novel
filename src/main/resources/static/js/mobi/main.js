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

	},
	mounted(){
		$('#novel').css('display', 'block');
	},
	watch:{
		selected(val) {
			console.log(val);
		}
	},
	methods: {
        index(){
            window.location.href="./index.html";
        },
        search(){
            window.location.href="./search.html";
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
            var _self = this;
            $.ajax({
                type: "post",
                async: true,
                url: "/novel/info/" + key,
                success: function(data){
                	switch(key){
                	case 1:
                		_self.coatInfo = data;
                		break;
            		case 2:
	            		_self.cityInfo = data;
	            		break;
            		case 3:
	            		_self.acroInfo = data;
	            		break;
            		case 4:
	            		_self.fantInfo = data;
	            		break;
            		case 5:
	            		_self.scieInfo = data;
	            		break;
            		case 6:
	            		_self.gameInfo = data;
	            		break;
	            	default:
	            		_self.recoInfo = data;
	            		break;
                	}
                }
            });
        },
        more(){
            window.location.href="./more.html";
        },
        info(){
            window.location.href="./chapter.html"; 
        }
	}
});