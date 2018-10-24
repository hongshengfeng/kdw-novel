var app = new Vue({
    el: '#app',
    data: {
        active: '0',
        hotInfo: ['1'],
        newInfo: ['1'],
        advShow: true,
        loading: false,
        isReco: true,
        catagorys: null,
        novelList: null,
        newList: null,
        hotList: null
    },
    mounted: function () {
        this.handle(0);
        this.categoryInfo();
        this.getNewsInfo();
        this.getHotInfo();
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
        getNewsInfo: function() {
            var _self = this;
            $.ajax({
                async: false,
                url: "/novel/new",
                success: function(data){
                    _self.newList = data;
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
        },
        getHotInfo: function(){
            var _self = this;
            $.ajax({
                async: false,
                url: "/novel/hot",
                success: function(data){
                    _self.hotList = data;
                },
                error: function () {
                    _self.$message({
                        message: '系统错误，请稍后重试。',
                        type: 'warning'
                    });
                }
            });
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
        handle:function(key) {
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
        },
        info: function(id) {
            window.location.href="/info/" + id;
        },
        more: function(){
            window.location.href="/category/0";
        }
    }
})