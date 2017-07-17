/**
 * Created by teo on 22/04/15.
 */


function jsonadvance(data) {

}
function jsonSimple(data)
{
    var json =
        '{ "name" : "'+ $('#namegeneral').val()+'",'+
         '"description" : "'+ $('#descriptiongeneral').val()+'",'+
       ' "memory" : [ {'+
        '       "name" : '+'"'+ $('#simplenamenemory').val()+'",'+
        '       "type" : '+'"'+ $('#simpletypenemory').val()+'"'+
        '      } ], "timers" : [  {'+
        '       "name" : ' +'"'+ $('#simplenametimers').val()+'",'+
        '       "interval" : ' +'"'+ $('#simpleintervaltimers').val()+'",'+
        '        "script" : ' +'"'+ $('#simplescripttimers').val()+'"'+
        '      } ], "outputs" : [{'+
        '       "name" : '+'"'+ $('#simplenameoutputs').val()+'",'+''+
        '       "type" : '+'"'+ $('#simpletypeoutputs').val()+'",'+''+
        '       "compute" : '+'"'+$('#simplecomputeoutputs').val()+'"'+
        '       }, ]}'
    return json;
}
function json(data)
{
    var json =$('#textjson').val()

    return json;
}

function divSimple(data)
{
     var json =
        '{ '+'</br><b>'+'"name"'+'</b>'+':"'+ $('#namegeneral').val()+'",'+'</br>'+
        '<b>'+' "memory":[ '+'</br>'+'      {'+'</br></b>'+
        '       "name": '+'"'+ $('#simplenamenemory').val()+'",'+'</br>'+
        '       "type": '+'"'+ $('#simpletypenemory').val()+'"</br>'+
        '      } ],'+'</br><b>'+' "timers":[ '+'</br></b>'+'      {'+'</br>'+
        '       "name": ' +'"'+ $('#simplenametimers').val()+'",'+'</br>'+
        '       "interval": ' +'"'+ $('#simpleintervaltimers').val()+'",</br>'+
        '        "script": ' +'"'+ $('#simplescripttimers').val()+'"</br>'+
        '      } ],'+'</br><b>'+' "outputs":['+'</br></b>'+'       {'+'</br>'+
        '       "name": '+'"'+ $('#simplenameoutputs').val()+'",'+'</br>'+
        '       "type": '+'"'+ $('#simpletypeoutputs').val()+'",'+'</br>'+
        '       "compute": '+'"'+$('#simplecomputeoutputs').val()+'"</br>'+
        '       }, ]' +'</br>'+'}'
 return json;
}
$(document).ready(function () {
    // Random Person AJAX Request
    $("input:text").change(function() {
        if($(this).attr('name')=='simple')
        {
        $('#codejson').empty();

          $('#codejson').append( divSimple());

        }
        else {
            $('#codejson').empty();
            $('#codejson').empty(divSimple());
        }
    } );

    $("select").change(
        function()
        {
            if($(this).attr('name')=='simple')
            {
                $('#codejson').empty();
                $('#codejson').append( divSimple());
            }
            else {
                $('#codejson').empty();
                $('#codejson').empty(divSimple());
            }
        }
    );

    $.ajax({
        url: "../agent_definitions"
    }).then(function (data) {

        $('#idlisttable').empty();
        $(data.agent_definitions).each(function(index,item) {
            txt="<tr><td id="+item.user+">"+item.user+
                "</td><td id="+item.user+">"+item.name+"</td>"+
                "</td><td id="+item.user+">"+item.description+"</td>"+
                "</td><td id="+item.user+">"+item.enabled+"</td>"+
                //   "<td><input type='button' value='Button 1'  id="+item.id+" /></td>"+
                "<td><a href='user.jsp'><i class='fa fa-pencil'></i></a>"+
                "<a href='#myModal' role='button' data-toggle='modal' id="+item.user+"><i class='fa fa-trash-o'></i></a></td></tr>"
            $('#idlisttable').append(txt);
       });
    });
    $.ajax({
        url: "../users"
    }).then(function (data) {
        $('#advanceusergeneral').empty();
        $('#DropUserGeneral').empty();
        $(data.users).each(function(index,item) {
            {
              $('#advanceusergeneral').append('<option value='+item.id+'>'+item.nick_name+'</option>');
              $('#DropUserGeneral').append('<option value='+item.id+'>'+item.nick_name+'</option>');
                $('#codejson').empty();
                $('#codejson').append( divSimple());
             }
            });
    });
    $("#btnclean").click(function (e) {
        $('#btnsimpleSdve').attr("disabled", false);
    });
    $("#btnsimpleSdve").click(function (e) {
        $.ajax({
            type: "POST",
            url: '../users/'+ $('#DropUserGeneral').val()+'/agent_definitions',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: jsonSimple(),
            success: function (data, status, jqXHR) {
                $('#idMessageAgent').empty();
                $('#idMessageAgent').append(data.message);
                $('#btnsimpleSdve').attr("disabled", true);
                window.location.href = "/listdefinition.do";

            },

            error: function (jqXHR, status) {
                $('#idMessageAgent').empty();
                $('#idMessageAgent').append(jqXHR.responseText);

            }
        });
    });

    $('#btndefinition').click(function () {
        $.ajax({
            type: 'POST',
            url: '../users/tchambil/agent_definitions',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: jsonSimple(),
            success: function (data) {
                $('#txtadefinition').empty();
                $('#txtadefinition').append(JSON.stringify(data, null, "\t"));
            },
            error: function (err) {
                $('#txtadefinition').empty();
                $('#txtadefinition').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax


    });

    $('#btngetdefinition').click(function () {
        $.get('../users/tchambil/agent_definitions', function (data) {
            $('#txtadefinition').empty();
            $('#txtadefinition').append(JSON.stringify(data, null, "\t"));

        }); //-- END of Ajax

    });


    $('#btnscripptSave').click(function () {

        $.ajax({
            type: 'POST',
            url: '../users/'+ $('#DropUserGeneral').val()+'/agent_definitions',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: json(),
            success: function (data) {
                $('#txtadefinition').empty();
                $('#txtadefinition').append(JSON.stringify(data, null, "\t"));
                $('#idMessageUser').append(data.message);
                window.location.href = "/listdefinition.do";
            },
            error: function (err) {
                $('#txtadefinition').empty();
                $('#txtadefinition').append(JSON.stringify(err, null, 2));
            }
        }); //-- END of Ajax


    });

});

