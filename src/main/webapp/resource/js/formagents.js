/**
 * Created by teo on 08/07/15.
 */

$(document).ready(function(){
    var current = 1;

    widget      = $(".step");
    btnnext     = $(".next");
    btnback     = $(".back");
    btnsubmit   = $(".submit");

    // Init buttons and UI
    widget.not(':eq(0)').hide();
    hideButtons(current);
    setProgress(current);

    // Next button click action
    btnnext.click(function(){
        if(current < widget.length){
            widget.show();
            widget.not(':eq('+(current++)+')').hide();
            setProgress(current);
        }
        hideButtons(current);
    })

    // Back button click action
    btnback.click(function(){
        if(current > 1){
            current = current - 2;
            btnnext.trigger('click');
        }
        hideButtons(current);
    })
});

// Change progress bar action
setProgress = function(currstep){
    var percent = parseFloat(100 / widget.length) * currstep;
    percent = percent.toFixed();
    $(".progress-bar").css("width",percent+"%").html(percent+"%");
}

// Hide buttons according to the current step
hideButtons = function(current){
    var limit = parseInt(widget.length);

    $(".action").hide();

    if(current < limit) btnnext.show();
    if(current > 1) btnback.show();
    if (current == limit) { btnnext.hide(); btnsubmit.show(); }
}
var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-19096935-1']);
_gaq.push(['_trackPageview']);

(function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();