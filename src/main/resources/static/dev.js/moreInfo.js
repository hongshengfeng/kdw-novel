var app = new Vue({
    el: '#more',
    data: {
        active: '0',
        isReco: true,
        advShow: false,
        loading: false,
        catagorys: null,
        novelList: null,
        counts: 1
    },
    computed: {
        initInfo: function() {
            if (this.active == "") {
                this.isReco = true;
                return;
            }
            this.active == 0 ? this.isReco = true : this.isReco = false;
        }
    },
    mounted:function() {
        var _self = this;
        if(!_self.getCookie("topAdv")){
            setTimeout(function () {
                _self.advShow = true;
            }, 1000);
        }
        this.categoryInfo();
        this.handle(0);
        this.pageCounts();
        $("body").css("display", "block");
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
        handle: function(key) {
            this.loading = true;
            this.active = key;
            key == 0 ? this.isReco = true : this.isReco = false;
            var _self = this;
            $.ajax({
                type: "post",
                async: true,
                url: "/novel/info/" + key,
                success: function(data){
                    _self.novelList = data;
                    _self.loading = false;
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
            this.pageCounts();
        },
        info: function(id) {
            window.location.href="/info/" + id;
        },
        close: function() {
            this.advShow ? this.advShow = false : this.advShow = true;
            this.setCookie("topAdv", false, 1);
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
        },
        categoryInfo: function(){
            var _self = this;
            $.ajax({
                async: false,
                url: "/category/list",
                success: function(data){
                    _self.catagorys = data;
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
        },
        pageCounts: function () {
            var _self = this;
            $.ajax({
                async: true,
                url: "/novel/counts/" + this.active,
                success: function(data){
                    _self.counts = data;
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
        },
        pageInfo: function (curr) {
            this.loading = true;
            $(window).scrollTop(0);
            var infoElem = $(".nov_info").height();
            if(infoElem > 0){
                $(".el-loading-spinner").css("top", "5%");
            }
            var _self = this;
            $.ajax({
                async: true,
                url: "/novel/list/" + this.active + "/" + curr,
                success: function(data){
                    _self.novelList = data;
                    _self.loading = false;
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
        }
    }
});
