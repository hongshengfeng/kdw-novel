var app = new Vue({
    el: '#search',
    data: {
        advShow: false,
        loading: false,
        novelList: null,
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
    },
    computed: {
        moreInfo: function () {
            var windowHight = $(window).height();
            console.log(windowHight)
        }
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
                    _self.novelList = data.list;
                    _self.counts = data.totalPage
                    _self.loading = false;
                    _self.isEnd = true;
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
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
