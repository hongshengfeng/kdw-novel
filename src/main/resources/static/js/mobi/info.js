var novelInfo = new Vue({
	el: '#novel',
	data:{
        id: novel.id,
        size: novel.size,
        page: novel.firstCId,
        title: '可读小说',
        loading: false,
        isEnd: false,
		index: 1,
        infoList: []
	},
	methods: {
		toBack(){
			window.history.back(-1);
		},
		loadMore() {
			if(this.index >= this.size || this.page == 0){
				this.isEnd = true;
				this.loading = false;
                return;
			}
            this.info();
		},
		info(){
            var $self = this;
            var novelId = this.id;
            var chapterId = this.page;
            this.loading = true;
            $.ajax({
                type: "post",
                async: true,
                url: "/chapter/info/" + novelId + "/" + chapterId,
                success: function(data){
                    var chapter = {};
                    chapter.title = data.name;
                    chapter.content = data.content;
                    setTimeout(() => {
                        $self.infoList.push(chapter);
                        $self.title = data.name;
                        $self.loading = false;
                        $self.page ++;
                        $self.index ++;
                    }, 1000);
                }
            });
		}
	}
});