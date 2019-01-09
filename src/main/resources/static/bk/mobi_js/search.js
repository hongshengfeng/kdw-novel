var novel = new Vue({
    el: '#novel',
    data:{
        keywords: '',
        infoList: [],
        isSearch: false,
        isResult: false, //是否有搜索
    },
    watch:{
        keywords:function(val){
            if(val == ''){
                this.isSearch = false;
                this.infoList = [];
                this.isResult = false;
            }else{
                this.isSearch = true;
            }
        }
    },
    methods: {
        search(){
            var $self = this;
            this.showInfo = true;
            this.isResult = true;
            $.ajax({
                type: "post",
                async: false,
                url: "/novel/search/" + this.keywords,
                success: function(data){
                    $self.infoList = data;
                },
                error: function () {
                    $self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
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
        info(id){
            window.location.href="/m/chapter/" + id;
        },
        infoDetail(wd){
            this.keywords = wd;
            this.search();
        }
    }
});