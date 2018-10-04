var info = new Vue({
    el: '#app',
    data: {
        id: 0,
        section: 1,
        rigAdv: true,
        tabAdv: true
    },
    mounted() {
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
        }
    }
});
