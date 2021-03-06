$(document).ready(function () {

        var isMobile = false;
        if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent)
                || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0, 4))) isMobile = true;
        
        if (isMobile) {
            var bg = jQuery("#wrapper");
            jQuery(window).resize("resizeBackground");
            function resizeBackground() {
                bg.height(jQuery(window).height());
            }
            resizeBackground();
            
            var h = $(window).height();
            var w = $(window).width();
            var margin = h * 0.94 + 'px auto';
            margin = margin.toString();
            $("#lower-div").css('margin', margin);
            $("#lower-div").css('height', h * 0.06 + 'px');
            $("#lower-div span").css('line-height', h * 0.06 + 'px');
            
            $(".upper-div").css('margin', h * 0.20 + 'px auto');
            $(".upper-div").css('font-size', w * 0.08 + 'px');
            
            $("header").css('font-size', h * 0.025 + 'px');
            $(".headerNavBar li a").css('padding', '8px 10px');
            $(".center-div").css('width', w * 0.6 + 'px');
            $(".center-div").css('max-height', w * 0.20 + 'px');
            $(".center-div").css('margin', h * 0.40 + 'px auto');
            
            $("input").css('padding', 0.02 * h + 'px');
            $("input").css('margin', '5px 0');
            $("input").css('font-size', 0.02 * h + 'px');
            
            $(".deleteUser input").css('padding', 0.007 * h + 'px');
            
            $(".guideSlides").css('margin', '0');
            
            $(".guideSlides img").css('max-height', '20%')
                                 .css('max-width', '90%')
                                 .css('margin-right', '10px')
                                 .css('margin-left', '10px')
                                 .css('margin-bottom', '20px')
                                 .css('margin-top', '20px');

        }
        
        $('input').attr('autocomplete','off');
        
        var url = window.location.href.toString();
        if (url.indexOf("?error") != -1) {
            document.getElementById('errorMsg').innerHTML = "Käyttäjänimi tai salasana väärin!";
        }
        
        
        
        $("a").on('click', function(event) {
            
            var pathname = this.href; 
            console.log(pathname.toString().indexOf("#") > -1);
            if (pathname.toString().indexOf("#") > -1) {
                // Prevent default anchor click behavior
                event.preventDefault();

                // Store hash
                var hash = this.hash;

                // Using jQuery's animate() method to add smooth page scroll
                // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
                $('html, body').animate({
                    scrollTop: $(hash).offset().top - 51
                }, 800, function(){
   
                // Add hash (#) to URL when done scrolling (default click behavior)
                window.location.hash = hash;
                });
            }
        });
        
        $('.removeCommentForm input').mouseover(function(){$(this).parent().first().css('text-decoration', 'line-through')});
        $('.removeCommentForm input').mouseout(function(){$(this).parent().first().css('text-decoration', 'none')});
        
});

window.onload = function()
  {
    var url = window.location.href.toString();
        if (url.indexOf("?error") != -1) {
            document.getElementById('errorMsg').innerHTML = "Käyttäjänimi tai salasana väärin!";
        }
  }
