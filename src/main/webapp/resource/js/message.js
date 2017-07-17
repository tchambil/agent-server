google.load("visualization", "1.1", {packages: ["table"]});
function drawtableChart(data) {
    var datax = new google.visualization.DataTable();
    datax.addColumn('string', 'id');
    datax.addColumn('string', 'sender');
    datax.addColumn('string', 'receiver');
    datax.addColumn('string', 'content');
    datax.addRows(data.agent_message.length);

    for (i = 0; i < data.agent_message.length; i++) {
        datax.setValue(i, 0, data.agent_message[i].conversationId);
        datax.setValue(i, 1, data.agent_message[i].sender);
        datax.setValue(i, 2, data.agent_message[i].receiver);
        datax.setValue(i, 3, data.agent_message[i].content);
    }

    var options = {
        showRowNumber: false,
        page: 'enable',
        pageSize: 10,
        width: '100%',
        height: '100%',
        pagingSymbols: {
            prev: 'prev',
            next: 'next'
        }
    };
    var table = new google.visualization.Table(document.getElementById('mynetworkFA'));
    table.draw(datax, options)

}

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '../users/messages'
    }).then(function (data) {
        $('#idtablelistTask').empty();
        $(data.agent_message).each(function(index,item) {

            txt="<tr><td id="+item.id+">"+item.conversationId+"</td>"+
                "</td><td id="+item.id+">"+item.sender+"</td>"+
                "</td><td id="+item.id+">"+item.receiver+"</td>"+
                "</td><td id="+item.id+">"+item.content+"</td>"+
                "</td><td id="+item.id+">"+"Processing"+"</td>"+

                "<td><a href='/nautilodresult.do?="+item.conversationId+"' id="+item.id+"><i class='fa fa-pencil'></i>View</a></td></tr>"
            $('#idtablelistTask').append(txt);

        });
    });
    $("#search").click(function (e) {
        loadata()
    });

    $('#btnlist').click(function () {
       $.ajax({
            type: 'GET',
            url: '../users/message/'+'I'+ $('#idconversation').val()
        }).then(function (data) {
            drawtableChart(data);
        });
    });
    $('#btnlistTask').click(function () {
        $.ajax({
            type: 'GET',
            url: '../users/messages'
        }).then(function (data) {
            $('#idtablelistTask').empty();
            $(data.agent_message).each(function(index,item) {

                txt="<tr><td id="+item.id+">"+item.conversationId+"</td>"+
                    "</td><td id="+item.id+">"+item.sender+"</td>"+
                    "</td><td id="+item.id+">"+item.receiver+"</td>"+
                    "</td><td id="+item.id+">"+item.content+"</td>"+
                    "</td><td id="+item.id+">"+"Processing"+"</td>"+
                    "<td><a href='#myModal' role='button' data-toggle='modal' id="+item.id+"><i class='fa fa-pencil'></i>View</a></td></tr>"

                $('#idtablelistTask').append(txt);

            });
        });
    });

});
function loadata() {
    $.ajax({
        url: "../result/messages"
    }).then(function (data) {
        draw(data)
    });
}

function draw(datax) {
    var optionsFA = {
        groups: {
            users: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf007',
                    size: 50,
                    color: '#57169a'
                }
            }
        }
    };

    var edges = [];
    var nodes = [];
    var gEdges = datax.edges;
    var gNodes = datax.nodes;

    for (var i = 0; i < gNodes.length; i++) {
        var node = {};
        var gNode = gNodes[i];
        node['id'] = gNode.id;
        node['label'] = gNode.label;
        node['group'] = gNode.group;
        nodes.push(node);
    }
    for (var i = 0; i < gEdges.length; i++) {
        var edge = {};
        var gEdge = gEdges[i];
        edge['from'] = gEdge.from;
        edge['to'] = gEdge.to;
        edges.push(edge);
    }
    var data = {
        nodes: nodes,
        edges: edges
    };


    var containerFA = document.getElementById('mynetworkFA');

    var networkFA = new vis.Network(containerFA, data,optionsFA);

}

