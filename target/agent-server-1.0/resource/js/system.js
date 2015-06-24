/**
 * Created by teo on 02/05/15.
 */
$(document).ready(function () {

    $.ajax({
        url: "../status"
    }).then(function (data) {
        $("#idmessage").empty();
        $("#idmessage").append(data.message);
        $("#headerServer").empty();
        $("#headerServer").append('[' + data.HostName + ']');
        $("#headerIp").empty();
        $("#headerIp").append('[' + data.IP + ']');
        $("#headerStatus").empty();
        $("#headerStatus").append('[ ' + data.status + ' ]');

    });
});