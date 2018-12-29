var novelInfo = new Vue({
	el: '#novel',
	data:{
        id: info.nId,
        cId: info.cId,
        start: info.start,
        end: info.end,
        name: "",
        content: ""
	},
    mounted(){
	    this.info(this.cId);
    },
	methods: {
		toBack(){
			window.history.back(-1);
		},
		info(cId){
            var $self = this;
            var nId = $self.id;
            $.ajax({
                type: "post",
                async: true,
                jsonType: 'json',
                url: "/chapter/info/" + nId + "/" + cId,
                success: function(data){
                    $self.name = data.name;
                    $self.content = data.content;
                    $self.cId = cId;
                }
            });
		},
        next(){
            var nextId = this.cId + 1;
            if(nextId >= this.last && nextId < this.end){
                this.info(nextId);
            }
        },
        pre(){
            var preId = this.cId - 1;
            if(preId > this.last &&  preId <= this.end){
                this.info(preId);
            }
        }
	}
});