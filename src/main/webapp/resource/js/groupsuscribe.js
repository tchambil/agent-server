/**
 * Created by teo on 11/27/2015.
 */

$(document).ready(function () {
    $.ajax({
        url: "../agents"
    }).then(function (data) {
        $('#Droplistagents').empty();
        $(data.agent_instances).each(function(index,item) {
          if (item.Type=="local"){
                $('#Droplistagents').append('<option value='+item.aid+'>'+item.aid+'</option>');
            }
        });
    });
    $('#Dropgroups').click(function () {
        $.ajax({
            type: 'PUT',
            url: "../groupsname",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
            server: $('#server').val(),
            group: $('#Dropgroups').val()}),
            dataType: "json",
            success: function (data) {
                $('#listagents').empty();
                $(data.agents).each(function(index ,item) {
                    txt="<tr><td id="+$('#Dropgroups').val()+">"+$('#Dropgroups').val()+
                        "</td><td id="+item.aid+">"+item.aid+
                        "</td><td id="+item.name+">"+item.name+"</td>"+
                        "</td><td id="+item.addresses+">"+item.addresses+"</td>"+
                        "</td><td id="+item.type+">"+item.type+"</td>"+
                        "</td><td id="+item.status+">"+item.status+"</td></tr>"
                    $('#listagents').append(txt);
                });
            },
            error: function (err) {
                $('#listagents').empty();
            }
   });
  });

    $('#btnsuscribe').click(function () {
        $.ajax({
            type: 'PUT',
            url: "../suscribe/",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                server: $('#server').val(),
                group: $('#Dropgroups').val(),
                agent: $('#Droplistagents').val()}),
            dataType: "json",
            success: function (data) {
                $('#Dropgroups').empty();
                $(data.groups).each(function(index ,item) {
                    $('#Dropgroups').append('<option value='+item.group+'>'+item.group+'</option>');
                });
            },
            error: function (err) {
                $('#Dropgroups').empty();
            }
        }); //-- END of Ajax
    });

    $('#getGroups').click(function () {

        $.ajax({
            type: 'PUT',
            url: "../groups/",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
            server: $('#server').val()}),
            dataType: "json",
            success: function (data) {

                $('#Dropgroups').empty();
                $(data.groups).each(function(index ,item) {

                    $('#Dropgroups').append('<option value='+item.group+'>'+item.group+'</option>');
                });
            },
            error: function (err) {
                $('#Dropgroups').empty();
            }
        }); //-- END of Ajax
    });

});

