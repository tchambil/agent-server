/**
 * Created by teo on 11/27/2015.
 */
$(document).ready(function () {
    $('#Dropgroups').click(function () {
        $.ajax({
            type: 'GET',
            url: $('#server').val()+'/group/'+ $('#Dropgroups').val()
        }).then(function (data) {
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
        });

    });

    $('#getGroups').click(function () {
        $.ajax({
            type: 'GET',
            url: $('#server').val()+'/group'
        }).then(function (data) {
            $('#Dropgroups').empty();
            $(data.groups).each(function(index ,item) {
                $('#Dropgroups').append('<option value='+item.group+'>'+item.group+'</option>');
            });
        });

    });
});

