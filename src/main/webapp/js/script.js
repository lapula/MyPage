$(document).ready(function () {
    console.log("ready!");
    
    var bg = jQuery("#wrapper");
    jQuery(window).resize("resizeBackground");
    function resizeBackground() {
        bg.height(jQuery(window).height() + 60);
    }
    resizeBackground();

});