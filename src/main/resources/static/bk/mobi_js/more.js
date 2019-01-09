var novel = new Vue({
	el: '#novel',
	data:{
		selected: '0',
		recoInfo: [], //推荐
		coatInfo: [], //修真
		cityInfo: [], //都市
		acroInfo: [], //穿越
		fantInfo: [], //玄幻
		scieInfo: [], //科幻
		gameInfo: [], //网游
		show: true,
		loading: false,
        showTip: true
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
        toBack(){
            window.history.back(-1);
		},
        handle(kind){
            var $self = this;
            this.loading = true;
            $.ajax({
                type: "post",
                async: true,
                dataType: "json",
                url: "/novel/info/" + kind,
                success: function(data){
                    switch(kind){
                        case 1:
                            $self.coatInfo = $self.coatInfo.concat(data);
                            break;
                        case 2:
                            $self.cityInfo = $self.cityInfo.concat(data);
                            break;
                        case 3:
                            $self.acroInfo = $self.acroInfo.concat(data);
                            break;
                        case 4:
                            $self.fantInfo = $self.fantInfo.concat(data);
                            break;
                        case 5:
                            $self.scieInfo = $self.scieInfo.concat(data);
                            break;
                        case 6:
                            $self.gameInfo = $self.gameInfo.concat(data);
                            break;
                        default:
                            $self.recoInfo = $self.recoInfo.concat(data);
                            break;
                    }
                    $self.loading = false;
                }
            });
        },
		more(){
			this.loading = true;
			var kind = parseInt(this.selected);
			console.log(kind);
			this.handle(kind);
        },
        info(id){
            window.location.href="/m/chapter/" + id;
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
        closeTip(){
            this.showTip = !this.showTip;
        }
	}
});