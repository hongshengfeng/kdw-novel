var app = new Vue({
    el: '#app',
    data: {
        active: '0',
        hotInfo: [],
        newInfo: [],
        advShow: true,
        loading: true,
        isReco: true,
        catagorys: null,
        novelList: null,
        newList: null,
        hotList: null
    },
    mounted: function () {
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
        this.handle(0);
        $.ajax({
            async: false,
            url: "/novel/new",
            success: function(data){
                _self.newList = data;
                _self.newInfo.push("1");
            },
            error: function () {
                _self.$message({
                    message: '系统错误，请稍后重试。',
                    type: 'warning'
                });
            }
        });
        $.ajax({
            async: false,
            url: "/novel/hot",
            success: function(data){
                _self.hotList = data;
                _self.hotInfo.push("1");
            },
            error: function () {
                _self.$message({
                    message: '系统错误，请稍后重试。',
                    type: 'warning'
                });
            }
        });
        this.loading = false;
        $("body").css("display","block");
    },
    computed: {
        initInfo: function() {
            if (this.active == "") {
                this.isReco = true;
                return;
            }
            this.active == 1 ? this.isReco = true : this.isReco = false;
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
        close: function() {
            this.advShow ? this.advShow = false : this.advShow = true;
        },
        handle:function(key) {
            this.active = key;
            key == 0 ? this.isReco = true : this.isReco = false;
            var _self = this;
            $.ajax({
                type: "post",
                async: true,
                url: "/novel/info/" + key,
                success: function(data){
                    _self.novelList = data;
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
        more: function(){
            window.location.href="/category/" + 0;
        }
    }
})