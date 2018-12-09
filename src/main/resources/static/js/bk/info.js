var info = new Vue({
    el: '#app',
    data: {
        size: novel.size,
        id: novel.id,
        currPage: 1,
        list: null,
        content: '',
        rigAdv: true
    },
    mounted() {
        var _self = this;
        var chapterId = this.currPage;
        var novelId = this.id;
        $.ajax({
            type: "post",
            async: false,
            url: "/chapter/list/" + novelId,
            success: function(data){
                _self.list = data;
            },
            error: function () {
                _self.$message({
                    message: "系统错误，请稍后重试。",
                    type: "warning"
                });
            }
        });
        this.changeInfo(chapterId);
        $("body").css("display", "block");
    },
    methods: {
        pre() {
            var page = this.currPage;
            this.currPage = page > 1 ? page - 1 : 1;
            this.changeInfo(this.currPage);
            $(window).scrollTop(0);
        },
        next() {
            var page = this.currPage;
            this.currPage = page <= this.size ? page + 1 : page;
            this.changeInfo(this.currPage);
            $(window).scrollTop(0);
        },
        share() {
            this.$message({
                message: '功能未开通，程序员小哥哥正在努力实现，请期待！',
                type: 'warning'
            });
        },
        rigClose(){
            this.rigAdv = false;
            $(".noveInfo").css("min-height", "750px");
        },
        changeInfo(chapterId){
            var _self = this;
            var novelId = this.id;
            $.ajax({
                type: "post",
                async: false,
                url: "/chapter/content/" + novelId + "/" + chapterId,
                success: function(data){
                    _self.content = data;
                },
                error: function () {
                    _self.$message({
                        message: "系统错误，请稍后重试。",
                        type: "warning"
                    });
                }
            });
        }
    }
});

$(document).ready(function(){
    $("body").on("click", "#list a", function (){
        var allElem = $("#list a");
        for(var i = 0; i < allElem.length; i++){
            allElem[i].className="";
        }
        $(this).addClass("active");
    })
});
