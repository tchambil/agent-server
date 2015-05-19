/**
 * Created by teo on 22/04/15.
 */
$(document).ready(function () {
    // Random Person AJAX Request


    $.ajax({
        url: "../users"
    }).then(function (data) {
        $('#idlisttable').empty();
        $(data.users).each(function(index,item) {

            txt="<tr><td id="+item.id+">"+item.id+
                "</td><td id="+item.id+">"+item.nick_name+"</td>"+
                "</td><td id="+item.id+">"+item.full_name+"</td>"+
                "</td><td id="+item.id+">"+item.email+"</td>"+
             //   "<td><input type='button' value='Button 1'  id="+item.id+" /></td>"+

                "<td><a href='user.jsp'><i class='fa fa-pencil'></i></a>"+
                "<a href='#myModal' role='button' data-toggle='modal' id="+item.id+"><i class='fa fa-trash-o'></i></a></td></tr>"
            $('#idlisttable').append(txt);

        });


    });

    $("#btnGetUser").click(function (e) {
        $.get('../users/test-user-1', function (user) {
            $('#outUser').empty();
            $('#outUser').append(JSON.stringify(user, null, "\t"));
            /*
             $('#outUser').append("Id: " + user.id +" <br/>" +
             "display_name:" + user.displayName+" <br/>" +
             "full_name:"+ user.fullName +"<br/>" +
             "email:" + user.email + " <br/>" +
             "nick_name:" + user.nickName +" <br/>" +
             "organization:" + user.organization +" <br/>" +
             "interests:"+ user.interests +" <br/>");*/
        });
    });
    $("#btnGetUserall").click(function (e) {
        $.get('../users', function (user) {
            $('#idlisttable').empty();
            /*
            if(user){
                   var len=user.users.length;
                   var txt="";
                   if(len>0){
                       for(var i=0; i<len;i++)
                       {
                            txt="<tr><td>"+user.users[i].display_name+
                                "</td><td>"+user.users[i].id+"</td>"+
                                "</td><td>"+user.users[i].id+"</td>"+
                                "</td><td>"+user.users[i].id+"</td>"+
                                "<td><a href='user.jsp'><i class='fa fa-pencil'></i></a>"+
                                   "<a href='#myModal' role='button' data-toggle='modal'><i class='fa fa-trash-o'></i></a></td></tr>"
                                $('#idlisttable').append(txt);
                       }
                   }
               }
*/
            $(user.users).each(function(index,item) {



                txt="<tr><td>"+item.id+
                    "</td><td>"+item.nick_name+"</td>"+
                    "</td><td>"+item.full_name+"</td>"+
                    "</td><td>"+item.email+"</td>"+
                    "<td><a href='user.jsp'><i class='fa fa-pencil'></i></a>"+
                    "<a href='#myModal' role='button' data-toggle='modal'><i class='fa fa-trash-o'></i></a></td></tr>"
                $('#idlisttable').append(txt);

            });

        });

    });

    $("#btnputUser").click(function (e) {
        $.ajax({
            type: "PUT",
            url: '../users/' + $('#idInput').val().toString() + '',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                display_name: $('#display_nameInput').val().toString(),
                full_name: $('#full_nameInput').val().toString(),
                email: $('#emailInput').val().toString(),
                nick_name: $('#nicknameInput').val().toString(),
                organization: $('#companyInput').val().toString(),
                interests: $('#interestsInput').val().toString()

            }),
            dataType: "json",
            success: function (data, status, jqXHR) {
                $('#outUser').empty();
                $('#outUser').append(JSON.stringify(data, null, "\t"));
            },

            error: function (jqXHR, status) {
                $('#outUser').empty();
                $('#outUser').append(jqXHR.responseText);

            }
        });
    });


    $("#btnUserSave").click(function (e) {
        $.ajax({
            type: "POST",
            url: '../users',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                id: $('#idInput').val(),
                password: $('#passwordInput').val(),
                display_name: $('#display_nameInput').val(),
                full_name: $('#full_nameInput').val(),
                email: $('#emailInput').val().toString(),
                nick_name: $('#nicknameInput').val(),
                organization: $('#companyInput').val(),
                interests: $('#interestsInput').val()

            }),
            dataType: "json",
            success: function (data, status, jqXHR) {
                $('#idMessageUser').empty();
                $('#idMessageUser').append(data.message);
            },

            error: function (jqXHR, status) {
                $('#idMessageUser').empty();
                $('#idMessageUser').append(jqXHR.responseText);

            }
        });
    });
    $("#btnUserDelete").click(function (e) {
        $.ajax({
            type: "DELETE",
            url: '../users/'+ $('#idInput').val(),
            contentType: "application/json; charset=utf-8",

            success: function (data, status, jqXHR) {
                $('#idMessageUser').empty();
                $('#idMessageUser').append(data.message);
            },

            error: function (jqXHR, status) {
                $('#idMessageUser').empty();
                $('#idMessageUser').append(jqXHR.responseText);

            }
        });
    });


});
