var header = new Vue({
    el: '#header',
    data: {
        keywords: ""
    },
    methods: {
        index: function () {
            window.location.href="/";
        },
        login: function () {
            console.log("login")
        },
        record: function() {
            var wd = $.trim(this.keywords);
            if(wd != ""){
                window.location.href = "/search?wd=" + wd;
            }else{
                this.$message({
                    message: "请输入您想要搜索的小说",
                    type: 'warning'
                });
            }
        }
    }
});

var footer = new Vue({
    el: '#footer'
});