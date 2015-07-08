$(document).ready(function () {
    $.ajax({
        url: "../agents"
    }).then(function (data) {
        $('#idlisttable').empty();
        $(data.agent_instances).each(function(index,item) {

            txt="<tr><td id="+item.user+">"+item.user+
                "</td><td id="+item.user+">"+item.name+"</td>"+
                "</td><td id="+item.user+">"+item.definition+"</td>"+
                "</td><td id="+item.user+">"+item.Outputs+"</td>"+
                //   "<td><input type='button' value='Button 1'  id="+item.id+" /></td>"+

                "<td><a href='user.jsp'><i class='fa fa-pencil'></i></a>"+
                "<a href='#myModal' role='button' data-toggle='modal' id="+item.user+"><i class='fa fa-trash-o'></i></a></td></tr>"
            $('#idlisttable').append(txt);


        });


    });

    $.ajax({
        url: "../agent_definitions"
    }).then(function (data) {
        $('#DropAgentGeneral').empty();

        $(data.agent_definitions).each(function(index,item) {

            $('#DropAgentGeneral').append('<option value='+item.name+'>'+item.name+'</option>');


        });


    });

    $.ajax({
        url: "../users"
    }).then(function (data) {
        $('#DropUserGeneral').empty();


        $(data.users).each(function(index,item) {
            {

                $('#DropUserGeneral').append('<option value='+item.id+'>'+item.id+'</option>');
                   }
        });


    });


    $('#btnagentSdve').click(function () {
        $.ajax({

            type: 'POST',
            url: '../users/'+ $('#DropUserGeneral').val()+'/agents',
            contentType: "application/json; charset=utf-8",
            dataType: "json",


            data:     '{ "name" : "'+ $('#nameagentgeneral').val()+'" , "definition" : "'+ $('#DropAgentGeneral').val()+'", "addresses" : "'+$('#addressesagent').val()+'", "type": "'+ $('#typeagent').val()+'"}',
            success: function (data) {

                $('#idMessagsedAgent').empty();
                $('#idMessagsedAgent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {

                $('#idMessagsedAgent').empty();
                $('#idMessagsedAgent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });

    $('#btngetagentall').click(function () {
        $.ajax({
            type: 'GET',
            url: '../users/test-user-1/agents/',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });
    $('#btngetagentid').click(function () {
        $.ajax({
            type: 'GET',
            url: '../users/test-user-1/agents/Agent-1',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });
    $('#btnagentpause').click(function () {
        $.ajax({
            type: 'PUT',
            url: '../users/test-user-1/agents/Agent-1/pause',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });

    $('#btnagentdisable').click(function () {
        $.ajax({
            type: 'PUT',
            url: '../users/test-user-1/agents/Agent-1/disable',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });

    $('#btnagentresume').click(function () {
        $.ajax({
            type: 'PUT',
            url: '../users/test-user-1/agents/Agent-1/resume',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });

    $('#btnagentenable').click(function () {
        $.ajax({
            type: 'PUT',
            url: '../users/test-user-1/agents/Agent-1/enable',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });
    $('#btnagentstatus').click(function () {
        $.ajax({
            type: 'GET',
            url: '../users/test-user-1/agents/Agent-1/status?state=yes&count=2',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });
    $('#btnagentoutput').click(function () {
        $.ajax({
            type: 'GET',
            url: '../users/test-user-1/agents/Agent-1/output',
            // url: '../users/test-user-1/agents/Agent-1/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtagent').empty();
                $('#txtagent').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax
    });
});
