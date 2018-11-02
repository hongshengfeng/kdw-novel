var header = new Vue({
    el: '#header',
    data: {
        keywords: null
    },
    methods: {
        index: function () {
            window.location.href="/";
        },
        login: function () {
            console.log("login")
        },
        record: function() {
            console.log("record");
        }
    }
});

var footer = new Vue({
    el: '#footer'
});