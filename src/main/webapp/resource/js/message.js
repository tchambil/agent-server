
var network = null;
var data = null;

var btnDraw = document.getElementById('draw');
var txtData = document.getElementById('data');
var txtError = document.getElementById('error');
btnDraw.onclick = draw;

// resize the network when window resizes
window.onresize = function () {
    network.redraw()
};

function destroy() {
    if (network !== null) {
        network.destroy();
        network = null;
    }
}

// parse and draw the data
function draw () {
    destroy();
    try {
        txtError.innerHTML = '';

        // Provide a string with data in DOT language
        data = {
            dot: txtData.value
        };

        // create a network
        var container = document.getElementById('mynetwork');
        var options = {};
        network = new vis.Network(container, data, options);
    }
    catch (err) {
        // set the cursor at the position where the error occurred
        var match = /\(char (.*)\)/.exec(err);
        if (match) {
            var pos = Number(match[1]);
            if(txtData.setSelectionRange) {
                txtData.focus();
                txtData.setSelectionRange(pos, pos);
            }
        }

        // show an error message
        txtError.innerHTML =  err.toString();
    }
}

/**
 * Draw an example
 * @param {String} id HTML id of the textarea containing the example code
 */
function drawExample(id) {
    txtData.value = document.getElementById(id).value;
    draw();
}