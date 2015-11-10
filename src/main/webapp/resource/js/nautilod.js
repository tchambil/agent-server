/**
 * Created by teo on 22/04/15.
 */


function jsonadvance(data) {

}
function jsonNautilod(data)
{
    var json =

    '{"sender": "'+ $('#agentini').val()+'",'+
    '"receiver": "'+$('#agentini').val()+'",'+
    '"replyTo": "'+ $('#PutTo').val()+'",'+
    '"content": "nautilod n; return n.get('+"'"+ $('#expressionNau').val()+"'"+').xml;",'+
    '"language": "",'+
    '"encoding": "",'+
    '"ontology": "0",'+
    '"performative": "REQUEST",'+
    '"protocol": "REQUEST",'+
    '"replyWith": "",'+
    '"inReplyTo": "",'+
    '"replyBy": "",'+
    '"delegate": false }'
    window.alert(json);
    return json;
}

$(document).ready(function () {
    // Random Person AJAX Request
    $("#btnclean").click(function (e) {
        $('#btnsimpleSdve').attr("disabled", false);
    });
    $("#btnsimpleSdve").click(function (e) {
        $.ajax({
            type: "POST",
            url: 'http://dbpedias.cloudapp.net/acl',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: jsonNautilod(),
            success: function (data, status, jqXHR) {
                $('#idMessageAgent').empty();
                $('#idMessageAgent').append(data.message);
                $('#btnsimpleSdve').attr("disabled", true);
            },

            error: function (jqXHR, status) {
                $('#idMessageAgent').empty();
                $('#idMessageAgent').append(jqXHR.responseText);

            }
        });
    });



});

