/**
 * Created by teo on 23/05/15.
 */
function scripting (dat)
{
    $.ajax({
        type: "POST",
        url: '../run/',
        contentType: "application/json; charset=utf-8",
        data: dat,
        success: function (data) {
            $('#resultscript').empty();
           $('#resultscript').append('<p><code>'+data.message+'</code></p>');
        },

        error: function (jqXHR, status) {
            $('#resultscript').empty();
            $('#resultscript').append(jqXHR.responseText);

        }
    });
}

$(document).ready(function ()
{

    $('#getstart').click(function (e) {
        $.ajax({
            type: "POST",
            url: '../run/',
            contentType: "application/json; charset=utf-8",
            data: $('#textarea1').val(),
            success: function (data) {
                $('#resultex').empty();

                $('#resultex').append('<p><code>'+data.message+'</code></p>');

            },

            error: function (jqXHR, status) {
                $('#resultex').empty();
                $('#resultex').append(jqXHR.responseText);

            }
        });


    });


    $("#menuweb li").click(function() {
        $("#scriptid").empty();
        $("#scriptid").append($(this).text());
        scripting($(this).text());
    });

    $("#menuoper li").click(function() {
        $("#scriptid").empty();
        $("#scriptid").append($(this).text());
        scripting($(this).text());
    });

    $("#menulist li").click(function() {
        $("#scriptid").empty();
        $("#scriptid").append($(this).text());
        scripting($(this).text());
    });

    $("#menumethod li").click(function() {
        $("#scriptid").empty();
        $("#scriptid").append($(this).text());
        scripting($(this).text());
    });

    $("#menumatec li").click(function() {
        $("#scriptid").empty();
        $("#scriptid").append($(this).text());

        scripting($(this).text());
    });

})

