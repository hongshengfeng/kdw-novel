var info = new Vue({
    el: '#app',
    data: {
        chapterSize: novel.chapterSize,
        novelId: novel.novelId,
        currChapter: 1,
        chapterList: null,
        chapterContent: null,
        rigAdv: true,
        tabAdv: true,
    },
    mounted: function() {
        var _self = this;
        var chapterId = this.currChapter;
        var novelId = this.novelId;
        $.ajax({
            type: "post",
            async: true,
            url: "/chapter/list/" + novelId,
            success: function(data){
                _self.chapterList = data;
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
        pre:function() {
            var page = this.currChapter;
            this.currChapter = page > 1 ? page - 1 : 1;
            this.changeInfo(this.currChapter);
            $(window).scrollTop(0);
        },
        next:function() {
            var page = this.currChapter;
            this.currChapter = page <= this.chapterSize ? page + 1 : page;
            this.changeInfo(this.currChapter);
            $(window).scrollTop(0);
        },
        share:function() {
            this.$message({
                message: '功能未开通，程序员小哥哥正在努力实现，请期待！',
                type: 'warning'
            });
        },
        rigClose:function(){
            this.rigAdv = false;
        },
        tabClose:function(){
            this.tabAdv = false;
        },
        changeInfo:function(chapterId){
            var _self = this;
            var novelId = this.novelId;
            $.ajax({
                type: "post",
                async: true,
                url: "/chapter/content/" + novelId + "/" + chapterId,
                success: function(data){
                    _self.chapterContent = data;
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
