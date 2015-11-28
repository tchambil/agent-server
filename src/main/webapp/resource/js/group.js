/**
 * Created by teo on 11/27/2015.
 */
$(document).ready(function () {

    $.ajax({
        url: "../group"
    }).then(function (data) {
        $('#idlisttable').empty();
        $('#Dropgroup').empty();
    $(data.groups).each(function(index,item) {
      $('#Dropgroup').append('<option value='+item.group+'>'+item.group+'</option>');
        $(item.agents).each(function(indexs,items) {
                txt="<tr><td id="+item.group+">"+item.group+
                    "</td><td id="+items.aid+">"+items.aid+
                    "</td><td id="+items.name+">"+items.name+"</td>"+
                    "</td><td id="+items.Addresses+">"+items.Addresses+"</td>"+
                    "</td><td id="+items.type+">"+items.type+"</td>"+
                    "</td><td id="+items.status+">"+items.status+"</td></tr>"
                $('#idlisttable').append(txt);
            });
       });
   });

    $('#Dropgroup').click(function () {
        $.ajax({
            type: 'GET',
            url: '../group/'+ $('#Dropgroup').val()
        }).then(function (data) {
            $('#idlisttable').empty();
             $(data.agents).each(function(index ,item) {
                    txt="<tr><td id="+$('#Dropgroup').val()+">"+$('#Dropgroup').val()+
                        "</td><td id="+item.aid+">"+item.aid+
                        "</td><td id="+item.name+">"+item.name+"</td>"+
                        "</td><td id="+item.addresses+">"+item.addresses+"</td>"+
                        "</td><td id="+item.type+">"+item.type+"</td>"+
                        "</td><td id="+item.status+">"+item.status+"</td></tr>"
                    $('#idlisttable').append(txt);
                });
            });

    });

});

