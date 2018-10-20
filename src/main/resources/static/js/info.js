var info = new Vue({
    el: '#app',
    data: {
        chapterSize: novel.chapterSize,
        currChapter: 1,
        chapterList: null,
        rigAdv: true,
        tabAdv: true,
    },
    mounted() {
        var _self = this;
        $.ajax({
            type: "post",
            async: true,
            url: "/chapter/list/" + novel.novelId,
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
        $("body").css("display", "block");
    },
    methods: {
        pre() {
            this.section--;
            console.log(this.section);
        },
        next() {
            this.section++;
            console.log(this.section);
        },
        share() {
            this.$message({
                message: '功能未开通，程序员小哥哥正在努力实现，请期待！',
                type: 'warning'
            });
        },
        rigClose(){
            this.rigAdv = false;
        },
        tabClose(){
            this.tabAdv = false;
        },
        changeInfo(chapterId){
            console.log(chapterId);
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
        console.log($(this).text());
    })
});
