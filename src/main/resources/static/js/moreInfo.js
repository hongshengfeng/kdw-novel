var app = new Vue({
    el: '#more',
    data: {
        active: '1',
        isReco: true,
        counts: 20,
        advShow: false,
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
    mounted() {
        var _self = this;
        if(!_self.getCookie("topAdv")){
            setTimeout(function () {
                _self.advShow = true;
            }, 1000);
        }
        $("body").css("display", "block");
    },
    methods: {
        handle: function(key) {
            this.active = key;
            key == 1 ? this.isReco = true : this.isReco = false;
        },
        info: function(id) {

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
        }
    }
});
