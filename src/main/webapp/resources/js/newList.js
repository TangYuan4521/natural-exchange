$(document).ready(function () {
    "use strict";
    if ($(".container").find(".js-deleted-advertisement").length) {
        $.gritter.add({
            title: "Ваше предложение удалено!",
            image: "/resources/images/newdesign/logo.png"
        });
    }
});
