var app = new Vue({
    el: '#app',
    data: {
        keywords: '',
        active: '0',
        hotInfo: '1',
        newInfo: '1',
        advShow: true,
        loading: true,
        isReco: true,
        catagorys: null,
        novelList: null,
    },
    mounted: function () {
        var _self = this;
        $.ajax({
            async: false,
            url: "/category/list",
            success: function(data){
                _self.catagorys = data;
            }
        });
        _self.handle(0);
        _self.loading = false;
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
        login: function() {
            console.log("login")
        },
        index: function() {
            console.log("111")
        },
        record: function() {
            console.log("record");
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
            this.$router.push({
                path: '/info',
                query: {
                    novId: id
                }
            })
        },
        more: function(){
            this.$router.push({
                path: '/more',
                query: {
                    active: this.active
                }
            })
        }
    }
})