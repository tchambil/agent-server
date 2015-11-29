/**
 * Created by teo on 11/27/2015.
 */
$(document).ready(function () {

    $.ajax({
        url: "../group"
    }).then(function (data) {
       $('#Dropgroup').empty();
    $(data.groups).each(function(index,item) {
      $('#Dropgroup').append('<option value='+item.group+'>'+item.group+'</option>');
        $('#idlisttable').empty();
      /*  $(item.agents).each(function(indexs,items) {
                txt="<tr><td id="+item.group+">"+item.group+
                    "</td><td id="+items.aid+">"+items.aid+
                    "</td><td id="+items.name+">"+items.name+"</td>"+
                    "</td><td id="+items.Addresses+">"+items.Addresses+"</td>"+
                    "</td><td id="+items.type+">"+items.type+"</td>"+
                    "</td><td id="+items.status+">"+items.status+"</td></tr>"
                $('#idlisttable').append(txt);
            });*/
       });
   });

    $.ajax({
        url: "../groupgeneral"
    }).then(function (data) {
        $('#Droplistgroups').empty();
        $(data.ServerGroup).each(function(index,item) {
            $('#Droplistgroups').append('<option value='+item.name+'>'+item.name+'</option>');
            });
        });

    $.ajax({
        url: "../agents"
    }).then(function (data) {
        $('#Droplistagents').empty();
        $(data.agent_instances).each(function(index,item) {
            $('#Droplistagents').append('<option value='+item.aid+'>'+item.aid+'</option>');
        });
    });

    $('#btnadd').click(function () {
        $.ajax({
            type: 'POST',
            url: "../groups/"+$('#Droplistgroups').val(),
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                state: "active",
                agents: [{aid: $('#Droplistagents').val(),
                    user:"test-user-1" }]
                }),
            dataType: "json",
            success: function (data) {
                $('#idMessagsedAgent').empty();
                $('#idMessagsedAgent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#idMessagsedAgent').empty();
            }
        }); //-- END of Ajax
    });

    $('#btnsavegroup').click(function () {
        $.ajax({
            type: 'POST',
            url: "../group",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                name: $('#name').val(),
                description: $('#Description').val()}),
            dataType: "json",
            success: function (data) {
                $('#idMessagsedAgent').empty();
                $('#idMessagsedAgent').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#idMessagsedAgent').empty();
            }
        }); //-- END of Ajax
    });

    $('#Dropgroup').click(function () {
        $.ajax({
            type: 'GET',
            url: '../group/'+ $('#Dropgroup').val()
        }).then(function (data) {
            $('#idtablelist').empty();
             $(data.agents).each(function(index ,item) {
               txt="<tr><td id="+$('#Dropgroup').val()+">"+$('#Dropgroup').val()+
                        "</td><td id="+item.aid+">"+item.aid+
                        "</td><td id="+item.name+">"+item.name+"</td>"+
                        "</td><td id="+item.addresses+">"+item.addresses+"</td>"+
                        "</td><td id="+item.type+">"+item.type+"</td>"+
                        "</td><td id="+item.status+">"+item.status+"</td></tr>"
                    $('#idtablelist').append(txt);
                      });
            });

    });

});

