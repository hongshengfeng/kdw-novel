var header = new Vue({
    el: '#header',
    data: {
        wd: ""
    },
    methods: {
        index: function () {
            window.location.href="/";
        },
        login: function () {
            console.log("login")
        },
        search: function() {
            var wd = $.trim(this.wd);
            if(wd != ""){
                window.location.href = "/search?wd=" + wd;
            }else{
                this.$message({
                    message: "请输入您想要搜索的小说",
                    type: 'warning'
                });
            }
        },
        record: function () {
            this.$message({
                message: "登录后系统会自动记录阅读记录",
                type: 'warning'
            });
        }
    }
});

var footer = new Vue({
    el: '#footer'
});