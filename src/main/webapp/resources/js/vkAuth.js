function readyFn( jQuery ) {
    var vkAuthButton = $(".vkAuth");
    vkAuthButton.click(vkAuthButtonFunc);
    myAuth();
}

$( document ).ready( readyFn );

function vkAuthButtonFunc(eventObject) {
    eventObject.preventDefault();
    var url = "https://oauth.vk.com/authorize";
    var client_id = "client_id=3862800&";
    var scope = "scope=notify&";
    var redirect_uri = "redirect_uri=http://naturalexchange.ru/advertisement/list.html&";
    var display = "display=popup&";
    var response_type = "response_type=token";
    var data = client_id + scope + redirect_uri + display + response_type;
    //window.open(url + "?" + data,"window",'width=200,height=400');
    window.location.replace(url + "?" + data);
}

function myAuth() {
    var anchor = window.location.hash;
    if(anchor.indexOf("access_token=") > 0) {
        var t = anchor.replace("#access_token=","");
        t = t.replace(" ","");
        var access_token = "";
        var i = 0;
        while(t[i] != "&") {
            access_token += t[i];
            i++;
        }
        t = t.replace(access_token + "&expires_in=","");
        var expires_in = "";
        i = 0;
        while(t[i] != "&") {
            expires_in += t[i];
            i++;
        }
        var user_id = "";
        t = t.replace(expires_in + "&user_id=","");
        user_id = t;
        var thing = { "access_token" : access_token, "user_id": user_id };
        jsonData = $.toJSON(thing);
        $.ajax({
            type: "POST",
            url: "http://naturalexchange.ru/VK/auth.html",
            dataType: 'json',
            data: user_id,
            success: function(result1) {
                alert(result1);
              //  if(result == true) {
                    window.location.replace("http://naturalexchange.ru/advertisement/list.html");
                //} else if (result == false) {
                  //  var script = document.createElement('SCRIPT');
                   // script.src = "https://api.vk.com/method/getProfiles?uid=" + user_id + "&v=5.0&access_token=" + access_token + "&callback=callbackFunc'";
                    //document.getElementsByTagName("head")[0].appendChild(script);
                //}


            }
        })
    }
}

function callbackFunc(result) {
    alert(result);
}