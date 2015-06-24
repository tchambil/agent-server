<!doctype html>
<html>
<head>
  <title>Network | DOT language playground</title>


</head>
<body onload="drawExample('example2')">
<table id="frame">
  <col width="50%">
  <col width="50%">
  <tr>
    <td colspan="2" style="height: 50px;">
      <div>
        <br>
        <button id="draw">Draw</button>
        <span id="error"></span>
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <textarea id="data"></textarea>
    </td>
    <td>
      <div id="mynetwork"></div>
    </td>
  </tr>
</table>


<script type="text/javascript" src="/resource/js/message.js"></script>

<textarea id="example2" class="example">
  digraph topology
  {
  node[shape=circle fontSize=12]
  edge[length=170 fontSize=12]
  "10.0.255.1" -> "10.0.255.3"[label=""];
  "10.0.255.1" -> "10.0.255.2"[label=""];
  "10.0.255.2" -> "10.0.255.3"[label=""];
   "10.0.255.3" -> "10.0.255.4"[label=""];
    "10.0.255.4" -> "10.0.255.1"[label=""];
   }
</textarea>


</body>
</html>
