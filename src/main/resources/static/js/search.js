var app = new Vue({
    el: '#search',
    data: {
        advShow: false,
        loading: false,
        novelList: [],
        currPage: 1,
        counts: 1,
        isEnd: false,
        isMore: false,
        wd : wd
    },
    mounted() {
        var _self = this;
        if(!_self.getCookie("sTopAdv")){
            setTimeout(function () {
                _self.advShow = true;
            }, 1000);
        }
        header.keywords = this.wd
        this.handle();
        this.moreInfo();
    },
    methods: {
        infoClass: function (cateId) {
            var category = "";
            switch (cateId){
                case 1:
                    category = "coatard";
                    break;
                case 2:
                    category = "city";
                    break;
                case 3:
                    category = "across";
                    break;
                case 4:
                    category = "fantasy";
                    break;
                case 5:
                    category = "science";
                    break;
                default:
                    category = "game";
                    break;
            }
            return category;
        },
        nextHandle: function () {
            var nextPage = this.currPage ++;
            if(nextPage > counts){
                this.isEnd = true;
            }else{
                this.handle();
            }
        },
        handle: function () {
            this.loading = true;
            var _self = this;
            $.ajax({
                type: "post",
                async: true,
                url: "/novel/search/" + this.wd + "/" + this.currPage,
                success: function(data){
                    if(data.list.length > 0){
                        for(var i = 0; i < data.list.length; i++){
                            _self.novelList.push(data.list[i]);
                        }
                        _self.counts = data.totalPage;
                        _self.loading = false;
                        if(_self.counts == 1){
                            _self.isEnd = true;
                        }
                    }else{
                        _self.isEnd = true;
                    }
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
        },
        moreInfo: function () {
            var _self = this;
            $(window).scroll(function(){
                if ($(window).scrollTop() + $(window).height() == $(document).height() && !_self.isEnd) {
                    _self.currPage ++;
                    _self.isMore = true;
                    $.ajax({
                        type: "post",
                        async: true,
                        url: "/novel/search/" + _self.wd + "/" + _self.currPage,
                        success: function(data){
                            if(data.list.length > 0){
                                _self.counts = data.totalPage;
                                setTimeout(function(){
                                    _self.isMore = false;
                                },3000);
                                for(var i = 0; i < data.list.length; i++){
                                    _self.novelList.push(data.list[i]);
                                }
                            }else{
                                setTimeout(function(){
                                    _self.isMore = false;
                                },3000);
                                _self.isEnd = true;
                            }
                        },
                        error: function () {
                            _self.$message({
                                message: '系统错误，请稍后重试。',
                                type: 'warning'
                            });
                        }
                    });
                }
            });

        },
        info: function(id) {
            window.location.href="/info/" + id;
        },
        close: function() {
            this.advShow ? this.advShow = false : this.advShow = true;
            this.setCookie("sTopAdv", false, 1);
        },
        setCookie: function (key, value, life) {
            var date = new Date();
            date.setTime(date.getTime() + (life * 24 * 60 * 60 * 1000));
            var expires = "expires=" + date.toUTCString();
            window.document.cookie = key + "=" + value + "; " + expires;
        },
        getCookie: function (key) {
            var regx = '(^|;) ?' + key + '=([^;]*)(;|$)';
            var value = window.document.cookie.match(regx);
            return value ? value[2] : null;
        }
    }
});
