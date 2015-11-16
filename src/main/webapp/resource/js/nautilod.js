/**
 * Created by teo on 22/04/15.
 */



function jsonNautilod(data)
{
    var json =

    '{"sender": "agent1@dbpedias.cloudapp.net",'+
    '"receiver": "agent1@dbpedias.cloudapp.net",'+
    '"replyTo": "'+ $('#Dropagent').val()+'",'+
    '"content": "'+ $('#see_uri').val().trim()+" -p "+$('#expressionNau').val().trim()+'",'+
    '"language": "",'+
    '"encoding": "",'+
    '"ontology": "0",'+
    '"performative": "REQUEST",'+
    '"protocol": "REQUEST",'+
    '"replyWith": "",'+
    '"inReplyTo": "",'+
    '"replyBy": "",'+
    '"delegate": false }'

    return json;
}

$(document).ready(function () {
    // Random Person AJAX Request
    $.ajax({
        url: "../agents"
    }).then(function (data) {
        $('#Dropagent').empty();

        $(data.agent_instances).each(function(index,item) {

            $('#Dropagent').append('<option value='+item.aid+'>'+item.aid+'</option>');


        });


    });

    $("#btnclean").click(function (e) {
        $('#btnsimpleSdve').attr("disabled", false);
    });
    $("#btnsimpleSdve").click(function (e) {
        $.ajax({
            type: "POST",
            url: '/acl',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: jsonNautilod(),
            success: function (data, status, jqXHR) {

                var jsonString = JSON.stringify(data, null, "\t");
                var newObject = JSON.parse(jsonString);
                $('#idMessageAgent').empty();
                $('#idMessageAgent').append("Script created succesfully with ID "+newObject.encoding+"Please annotate this ID to check the agent status. If you provided a valid email address you'll receive an email with the task ID");
                $('#btnsimpleSdve').attr("disabled", true);
            },



            error: function (jqXHR, status) {
                $('#idMessageAgent').empty();
                $('#idMessageAgent').append(jqXHR.responseText);

            }
        });
    });



});

