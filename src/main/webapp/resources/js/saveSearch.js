$(document).ready(function() {
    document.getElementById("contact").style.display="block";
    $("a.save").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("#contact").keypress(function(e){
        if(e.keyCode===13) {
        e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#refresh").click( function() {
        document.getElementById('captchaImg').src = "makeCaptcha.html?id=" + Math.random();
    });
    $("#contact").submit(function() { return false; });
    $("#close").click( function(){
        $.fancybox.close();
    });
    $("#send").click( function(e){
        e.preventDefault();
        document.getElementById('message').innerHTML = "";
        var wordSearch =$(".wordSearch").val();
        var email = $("#emailSave").val();
        var captchaInput = $ (".captchaInput").val();
        var captchaValid = validateCaptcha(captchaInput);
        var mailvalid = validateEmail(email);
        if(mailvalid == false) {
            document.getElementById('message').innerHTML = "Введите корректный e-mail адрес.";
        }
        else if (mailvalid == true) {
            $("#send").replaceWith("отправка...");
            var categorySearch="";
            var checkboxes = document.getElementsByName('categories');
            for (var i = 0, length = checkboxes.length; i < length; i++) {
                if (checkboxes[i].checked)  {
                    var categorySearch=categorySearch+' '+ checkboxes[i].value;
                }
            }
            var dataSearch = 'wordSearch='+wordSearch+'&categorySearch='+categorySearch+'&email='+email;
            $.ajax({
                type: 'GET',
                url: '/n-exchange/advertisement/savingSearch.html',
                data: dataSearch,
                success: function(data) {
                    $("#contact").fadeOut("fast", function(){
                        if (data === "auth") {
                            $(this).before("<strong> Авторизуйтесь!!! </strong>");
                        }
                        if (data === "save") {
                            document.getElementById("saving").style.display="block";
                        }
                        setTimeout("$.fancybox.close()", 2000);
                    });
                }
            });
            $("#contact").fadeIn("fast");
        }
    });
});

function validateEmail(email) {
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
  }

function validateCaptcha (captchaInput) {
    var cap='<% = Session ["captcha"] %>';
    if (cap === captchaInput) {
        return true;
    }
    else {
        return false;
    }
}