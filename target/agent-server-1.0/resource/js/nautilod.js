google.load("visualization", "1.1", {packages: ["table"]});

function loadata() {
    $.ajax({
        url: "../result/" + $('#idresult').val()
    }).then(function (data) {
        draw(data)
    });
}

function jsonNautilod(data) {
    var json =

        '{"sender": "' + $('#Dropagentstart').val() + '",' +
        '"receiver": "' + $('#Dropagentstart').val() + '",' +
        '"replyTo": "' + $('#Dropagent').val() + '",' +
        '"content": "' + "::putTo(" + $('#Dropagent').val() + ",::exec(" + $('#see_uri').val().trim() + " -p " + $('#expressionNau').val().trim() + "))" + '",' +
        '"language": "",' +
        '"encoding": "",' +
        '"ontology": "0",' +
        '"performative": "REQUEST",' +
        '"protocol": "REQUEST",' +
        '"replyWith": "",' +
        '"inReplyTo": "",' +
        '"replyBy": "",' +
        '"delegate": false }'

    return json;
}
function valida(group, datax) {

    for (x = 0; x < group.length; x++) {
        if ((group[x].group == datax)) {
            return true
        }
    }
    return false
}
function getGroup(group, datax) {
    for (x = 0; x < group.length; x++) {
        if ((group[x].group == datax)) {
            return group[x].id
        }
    }
    return 0
}
function getItem(group, datax) {

    for (x = 0; x < group.length; x++) {
        if ((group[x].group == datax)) {
            return group[x].item
        }
    }
    return 0
}
function draw(datax) {
    var options = {
        nodes: {
            shape: 'dot',
            size: 16
        },
        physics: {
            forceAtlas2Based: {
                gravitationalConstant: -26,
                centralGravity: 0.005,
                springLength: 230,
                springConstant: 0.18
            },
            maxVelocity: 146,
            solver: 'forceAtlas2Based',
            timestep: 0.35,
            stabilization: {iterations: 150}
        }
    };
    var nodes = [];
    var edges = [];
    var group = [];

    for (i = 0; i < datax.result.length; i++) {
        var grp = {};
        if ((valida(group, datax.result[i].receiver))) {
        }
        else {
            grp['id'] = x;
            grp['item'] = i;
            grp['group'] = datax.result[i].receiver;
            group.push(grp);
        }
    }
/*
    for (x = 0; x < group.length; x++) {
        var node = {};
        node['id'] =x;
        node['label'] = group[x].group;
        node['group'] = group[x].id;
        nodes.push(node);
    }

*/
    for (i = 0; i < datax.result.length; i++) {
        var node = {};
        var edge = {};
        node['id'] =  i;
        node['label'] = datax.result[i].uri;
        node['group'] = getGroup(group, datax.result[i].receiver);
        nodes.push(node);
        edge['from'] =  getItem(group, datax.result[i].receiver);
        edge['to'] = i;
        edges.push(edge);
    }







    var data = {
        nodes: nodes,
        edges: edges
    };
    var containerFA = document.getElementById('mynetworkFA');
    var networkFA = new vis.Network(containerFA, data, options);
}
$(document).ready(function () {
    // Random Person AJAX Request
    $.ajax({
        url: "../agents"
    }).then(function (data) {
        $('#Dropagent').empty();
        $('#Dropagentstart').empty();
        $(data.agent_instances).each(function (index, item) {
            $('#Dropagentstart').append('<option value=' + item.aid + '>' + item.aid + '</option>');
            $('#Dropagent').append('<option value=' + item.aid + '>' + item.aid + '</option>');
        });
    });

    $("#getstart").click(function (e) {
        loadata();
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
                $('#idMessageAgent').append("Script created succesfully with ID " + newObject.encoding + " Please annotate this ID to check the agent status. If you provided a valid email address you'll receive an email with the task ID");
                $('#btnsimpleSdve').attr("disabled", true);
            },
            error: function (jqXHR, status) {
                $('#idMessageAgent').empty();
                $('#idMessageAgent').append(jqXHR.responseText);

            }
        });
    });
});

