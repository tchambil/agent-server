function draw() {
    /*
     * Example for FontAwesome
     */
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

    // create an array with nodes
    var nodesFA = [{
        id: 1,
        label: 'agent1@dbpedias.cloudapp.net',
        group: 'users'
    }, {
        id: 2,
        label: 'agent2@geonames.cloudapp.net',
        group: 'users'
    }, {
        id: 3,
        label: 'agent3@freebases.cloudapp.net',
        group: 'users'
    }, {
        id: 4,
        label: 'agent4@yagos.cloudapp.net',
        group: 'users'
    }];

    // create an array with edges
    var edges = [{
        from: 1,
        to: 3
    },
        {
        from: 2,
        to: 1
    },
         {
            from: 2,
            to: 4
        },
        {
            from: 2,
            to: 4
        }
    ];

    // create a network
    var containerFA = document.getElementById('mynetworkFA');
    var dataFA = {
        nodes: nodesFA,
        edges: edges
    };

    var networkFA = new vis.Network(containerFA, dataFA, optionsFA);

}