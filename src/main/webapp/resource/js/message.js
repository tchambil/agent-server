$(document).ready(function () {
    $("#search").click(function (e) {
        loadata()
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

