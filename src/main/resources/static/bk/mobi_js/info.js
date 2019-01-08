var novelInfo = new Vue({
	el: '#novel',
	data:{
        id: info.nId,
        cId: info.cId,
        name: "",
        content: "",
        loading: false,
        showTip: true
	},
    mounted(){
	    this.info(this.cId);
    },
    computed:{
        showPre(){
            return this.cId > info.start;
        },
        showNext(){
            return this.cId < info.end;
        }
    },
	methods:{
		toBack(){
			window.history.back(-1);
		},
		info(cId){
            var $self = this;
            var nId = $self.id;
            this.loading = true;
            $.ajax({
                type: "post",
                async: true,
                jsonType: 'json',
                url: "/chapter/info/" + nId + "/" + cId,
                success: function(data){
                    $self.name = data.name;
                    $self.content = data.content;
                    $self.cId = cId;
                    $self.loading = false;
                }
            });
            $(window).scrollTop(0);
		},
        next(){
            var nextId = this.cId + 1;
            if(nextId > info.start && nextId <= info.end){
                this.info(nextId);
            }
        },
        pre(){
            var preId = this.cId - 1;
            if(preId >= info.start &&  preId < info.end){
                this.info(preId);
            }
        },
        closeTip(){
            this.showTip = !this.showTip;
        }
	}
});