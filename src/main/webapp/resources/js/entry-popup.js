$(document).ready(function() {
    "use strict";
    document.getElementById("#entry-form");
    $(".js-entry").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no',
        'display' : 'block'
    });
    $("#entry-form").keypress(function(e){
        if(e.keyCode===13) {
            e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#entry-form").submit(function() { return false; });
    $("#reject").click( function(){
        $.fancybox.close();
    });
});