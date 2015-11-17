<%--
  Created by IntelliJ IDEA.
  User: teo
  Date: 08/07/15
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Agent Server v1.0</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resource/css/bootstrap.css">
    <link rel="stylesheet" href="/resource/css/font-awesome.css">

    <script src="/resource/js/site/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="/resource/js/site/jquery.knob.js" type="text/javascript"></script>
    <script src="/resource/js/user.js"></script>
    <script src="/resource/js/system.js"></script>
    <script src="/resource/js/nautilod.js"></script>
    <script src="/resource/js/formagents.js"></script>

    <script type="text/javascript">
        $(function () {
            $(".knob").knob();
        });
    </script>


    <link rel="stylesheet" type="text/css" href="/resource/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/resource/css/premium.css">

</head>
<body class=" theme-blue">
<!-- Demo page code -->
<script type="text/javascript">
    $(function () {
        var match = document.cookie.match(new RegExp('color=([^;]+)'));
        if (match) var color = match[1];
        if (color) {
            $('body').removeClass(function (index, css) {
                return (css.match(/\btheme-\S+/g) || []).join(' ')
            })
            $('body').addClass('theme-' + color);
        }

        $('[data-popover="true"]').popover({html: true});

    });
</script>
<style type="text/css">
    #line-chart {
        height: 300px;
        width: 800px;
        margin: 0px auto;
        margin-top: 1em;
    }

    .navbar-default .navbar-brand, .navbar-default .navbar-brand:hover {
        color: #fff;
    }
</style>
<script type="text/javascript">
    $(function () {
        var uls = $('.sidebar-nav > ul > *').clone();
        uls.addClass('visible-xs');
        $('#main-menu').append(uls.clone());
    });
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<!--[if lt IE 7 ]>
<body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]>
<body class="ie ie7 "> <![endif]-->
<!--[if IE 8 ]>
<body class="ie ie8 "> <![endif]-->
<!--[if IE 9 ]>
<body class="ie ie9 "> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->

<!--<![endif]-->

<div class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="" href="/"><span class="navbar-brand"><span
                class="glyphicon glyphicon-tasks"></span> NautiLOD Distributed Through Agents</span></a>
    </div>
</div>

<div class="sidebar-nav">
    <ul>
        <!--<Plataform]-->
        <li><a href="/nautilodindex.do"
               data-target=".dashboard-menu" class="nav-header" data-toggle="collapse"><i
                class="glyphicon glyphicon-open"></i>Home</a></li>
        <!--<Users]-->
        <li><a href="/nautilodrun.do" data-target=".users-menu" class="nav-header collapsed" data-toggle="collapse"><i
                class="glyphicon glyphicon-cog"></i> Run Script</a>
        </li>
        <!--<Agent Definitions]-->
        <li><a href="/nautilodmessage.do" data-target=".legal-menu" class="nav-header collapsed" data-toggle="collapse"><i
                class="glyphicon glyphicon-map-marker"></i> Messages</a>
        </li>
        <!--<Agents]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="/nautilodresult.do" data-target=".premium-menu"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="glyphicon glyphicon-book"></i> Result</a>
        </li>
    </ul>
</div>
<div class="content">
    <div class="header">
        <h1 class="page-title"></h1>
    </div>

   <div class="main-content">
       <div id="myTabContent" class="tab-content">
           <div class="row">
               <div class="col-md-8 col-md-offset-2">
                   <div class="progress">
                       <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuemin="0"
                            aria-valuemax="100"></div>
                   </div>

                   <div class="step well">
                       <fieldset>
                           <div class="form-top">
                               <div class="form-top-left">
                                   <h1>General</h1>
                               </div>
                           </div>
                           <div class="form-bottom">
                               <div class="form-group">
                                   <label>Submitter</label>
                                   <input type="text"  name="simple" id="Submitter" class="form-control" value="Teofilo"/>
                               </div>
                               <div class="form-group">
                                   <label>Email</label>
                                   <input type="text"  name="simple" id="Email" class="form-control" value="antony_epis@hotmail.com"/>
                               </div>

                               <div class="form-group">
                                   <label>Script Name</label>
                                   <input type="text"  name="simple" id="Script_Name" class="form-control" value="NautiLod"/>

                               </div>

                           </div>
                       </fieldset>
                   </div>
                   <div class="step well">
                       <fieldset>
                           <div class="form-top">
                               <div class="form-top-left">
                                   <h1>Optional</h1>
                               </div>
                           </div>
                           <div class="form-bottom">
                               <div class="form-group">
                                   <label>Max size data(MBs)</label>
                                   <input type="text" name="simple" id="mbs" class="form-control" value="0"/>
                               </div>
                               <div class="form-group">
                                   <label>Max Triples</label>
                                   <input type="text" name="simple" id="maxtriples" class="form-control" value="0"/>
                               </div>
                               <div class="form-group">
                                   <label>Max timeout(ms)</label>
                                   <input type="text" name="simple" id="maxtimeut" class="form-control" value="0"/>
                               </div>
                               <div class="form-group">
                                   <label>Delay(ms)</label>
                                   <input type="text" name="simple" id="delay" class="form-control" value="0"/>
                               </div>
                           </div>
                       </fieldset>
                   </div>
                   <div class="step well">
                       <fieldset>
                           <div class="form-top">
                               <div class="form-top-left">
                                   <h1>Expression</h1>
                               </div>

                           </div>
                           <div class="form-bottom">
                               <div class="form-group">
                                   <label>Seed URI</label>
                                   <input id="see_uri" type="text"  name="simple" id="seeuri" class="form-control" value=" http://dbpedia.org/resource/Peru"/>

                               </div>
                               <div class="form-group">
                                   <label>NautiLOD expression</label>
                                   <textarea id="expressionNau" class="form-control" rows="10"><http://dbpedia.org/ontology/hometown>[ASK {?ctx <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/Person>. ?ctx <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/MusicalArtist>.}]/<http://dbpedia.org/ontology/birthPlace>/<http://www.w3.org/2002/07/owl#sameAs>/<http://www.w3.org/2000/01/rdf-schema#isDefinedBy>[ASK {?ctx <http://www.geonames.org/ontology#population> ?pop. FILTER (?pop >100).}] -f files.rdf</textarea>

                               </div>
                           </div>
                       </fieldset>
                   </div>
                   <div class="step well">
                       <fieldset>
                           <div class="form-top">
                               <div class="form-top-left">
                                   <h1>Output</h1>
                               </div>

                           </div>
                           <div class="form-bottom">
                               <div class="form-group">
                                   <label>name</label>
                                   <input type="text"  name="simple" id="nameout" class="form-control" value="AgentGeo"/>

                               </div>
                               <div class="form-group">
                                   <label>PutTo</label>
                                   <select id="Dropagent" class="form-control">
                                       <option value="Test">Selection Item</option>
                                   </select>
                                 </div>

                           </div>
                       </fieldset>
                   </div>

                   <button class="action back btn btn-info"><i class="fa fa-backward"></i>Back</button>
                   <button class="action next btn btn-info"><i class="fa fa-forward"></i>Next</button>
                   <button id="btnsimpleSdve"  class="action submit btn btn-success"> <i class="fa fa-save"></i>Save</button>
                   <button id="btnclean"  class="action submit btn btn-success"> <i class="fa fa-stop"></i>New</button>
                   <div id="idMessageAgent" class="alert alert-info">
                   </div>
               </div>
           </div>
       </div>

    </div>
</div>
<script src="/resource/js/site/bootstrap.js"></script>
<script type="text/javascript">
    $("[rel=tooltip]").tooltip();
    $(function () {
        $('.demo-cancel-click').click(function () {
            return false;
        });
    });
</script>
</body>
</html>

