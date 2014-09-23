$(document).ready(function() {
    "use strict";
    $(".js-registration").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("#registration-form").keypress(function(e){
        if(e.keyCode===13) {
            e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#registration-form").submit(function() { return false; });
    $("#reg-reject").click( function(){
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        errorString.text("");
        acceptString.text("");
        $.fancybox.close();
    });
    $(".js-entry").click( function(){
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        errorString.text("");
        acceptString.text("");
        $.fancybox.close();
    });
    $('.js-registration-complete').click(function(e) {
        e.preventDefault();
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        var email = $("#reg-email").val();
        var firstName = $("#reg-first-name").val();
        var lastName = $("#reg-last-name").val();
        var password = $("#reg-pass").val();
        var sendingUrl = $(".js-registration-form").data("url");
        var dataJson = {
            email: email,
            firstName: firstName,
            lastName: lastName,
            password: password,
            errors: null
        };
        $.ajax({
            type: 'POST',
            url: sendingUrl,
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == true) {
                    errorString.text("");
                    acceptString.show();
                    errorString.hide();
                    acceptString.text("Вы зарегистрированы! На ваш email выслано подтверждение вашего аккаунта.");
                } else {
                    errorString.show();
                    acceptString.text("");
                    acceptString.hide();
                    var errorVariant = data.errors;
                    if(errorVariant.exist) {
                        errorString.text(data.errors.exist);
                    } else if (errorVariant.wrong) {
                        errorString.text(data.errors.wrong);
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if(jqXHR.status==404) {
                    alert(errorThrown);
                }
            }
        })
    })
});
