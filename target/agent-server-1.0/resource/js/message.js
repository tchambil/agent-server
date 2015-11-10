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
        label: 'agent_1',
        group: 'users'
    }, {
        id: 2,
        label: 'agent_2',
        group: 'users'
    }, {
        id: 3,
        label: 'agent_3',
        group: 'users'
    }, {
        id: 4,
        label: 'agent_4',
        group: 'users'
    }, {
        id: 5,
        label: 'agent_5',
        group: 'users'

    }];

    // create an array with edges
    var edges = [{
        from: 1,
        to: 3
    }, {
        from: 2,
        to: 4
    }, {
        from: 2,
        to: 1
    }, {
        from: 3,
        to: 5
    }, {
        from: 4,
        to: 5
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