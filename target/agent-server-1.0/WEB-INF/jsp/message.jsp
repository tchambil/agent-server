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
    <script src="/resource/js/system.js"></script>
    <script src="/resource/js/site/jquery.knob.js" type="text/javascript"></script>


    <script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <link href="/resource/css/vis.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript">
        $(function () {
            $(".knob").knob();
        });
    </script>


    <link rel="stylesheet" type="text/css" href="/resource/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/resource/css/premium.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">

    <style>
        #mynetworkFA,
        #mynetworkIO {
            height: 500px;
            width: 1000px;
            border:1px solid lightgrey;
        }

        p {
            max-width:700px;
        }
    </style>
    <script src="/resource/js/message.js"></script>
    <script src="/resource/js/site/googleAnalytics.js"></script>
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
                class="fa fa-paper-plane"></span> Plataform Agent Server v1.0</span></a>

    </div>


</div>
</div>


<div class="sidebar-nav">
    <ul>
        <!--<Plataform]-->
        <li><a href="#"
               data-target=".dashboard-menu" class="nav-header" data-toggle="collapse"><i
                class="fa fa-fw fa-dashboard"></i>Dashboard<i class="fa fa-collapse"></i></a></li>
        <li>
            <ul class="dashboard-menu nav nav-list collapse in">
                <li><a href="/"><span class="fa fa-caret-right"></span> Main</a></li>

            </ul>
        </li>
        <!--<Users]-->
        <li><a href="#" data-target=".users-menu" class="nav-header collapsed" data-toggle="collapse"><i
                class="fa fa-fw fa-users"></i> Users<i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="users-menu nav nav-list collapse">
                <li><a href="/users.do"><span class="fa fa-caret-right"></span> User Profile</a></li>
                <li><a href="/listuser.do"><span class="fa fa-caret-right"></span> User List</a></li>

            </ul>
        </li>

        <!--<Agent Definitions]-->
        <li><a href="#" data-target=".legal-menu" class="nav-header collapsed" data-toggle="collapse"><i
                class="fa fa-fw fa-tasks"></i> Agent Definitions<i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="legal-menu nav nav-list collapse">
                <li><a href="/definitionsimple.do"><span class="fa fa-caret-right"></span> Simple</a></li>
                <li><a href="/definitionadvanced.do"><span class="fa fa-caret-right"></span> Advanced</a></li>
                <li><a href="/definitionsjson.do"><span class="fa fa-caret-right"></span> JSON</a></li>
                <li><a href="/listdefinition.do"><span class="fa fa-caret-right"></span> List</a></li>
            </ul>
        </li>
        <!--<Agents]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".premium-menu"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-eye-slash"></i> Agents<i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="premium-menu nav nav-list collapse">
                <li><a href="/agent.do"><span class="fa fa-caret-right"></span> Agent</a> </li>
                <li><a href="/agentscript.do"><span class="fa fa-caret-right"></span> AgentScript</a></li>
                <li><a href="/listagent.do"><span class="fa fa-caret-right"></span> List</a></li>
            </ul>
        </li>
        <!--<Groups]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".premium-menu1"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-cog"></i> Groups<i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="premium-menu1 nav nav-list collapse">
                <li><a href="/group.do"><span class="fa fa-caret-right"></span> Group</a> </li>
                <li><a href="/listgroup.do"><span class="fa fa-caret-right"></span> List</a></li>
                <li><a href="/suscribegroup.do"><span class="fa fa-caret-right"></span> Suscribe</a></li>
            </ul>
        </li>
        <!--<Groups]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".group-menu"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-arrows-alt"></i> Aplications<i class="fa fa-collapse"></i><span
                class="label label-info">+0</span></a>
        </li>
        <li>
            <ul class="group-menu nav nav-list collapse">
                <li>
                    <a href="/nautilodindex.do"><span class="fa fa-caret-right"></span> NautiLOD</a>
                </li>
            </ul>
        </li>
        <!--<TEST´s]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".group-test"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-globe"></i> Test<i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="group-test nav nav-list collapse">
                <li>
                    <a href="/testscript.do"><span class="fa fa-caret-right"></span> Scripts</a>
                </li>
            </ul>
        </li>


        <!--<Help]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".group-tutorial"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-question-circle"></i> Tutorial<i class="fa fa-collapse"></i></a>

        </li>
        <li>
            <ul class="group-tutorial nav nav-list collapse">
                <li>
                    <a href="/tutorial"><span class="fa fa-caret-right"></span> Simple</a>
                </li>
                <li>
                    <a target="_blank" href="/resource/files/tutorial.txt" ><span class="fa fa-caret-right"></span> Intermediate</a>
                </li>
                <li>
                    <a href="/tutorialintermediate"><span class="fa fa-caret-right"></span> Advanced</a>
                </li>
            </ul>
        </li>


    </ul>
</div>

<div class="content">
<div class="stats">
    <span   class="label label-danger">Status:</span> <span id="headerStatus">null</span>
    <span>&nbsp;&nbsp;</span>
    <span class="label label-success">Address IP:</span> <span id="headerIp">null</span>
    <span>&nbsp;&nbsp;</span>
    <span class="label label-info">Server:</span> <span id="headerServer">null</span>
    <span>&nbsp;&nbsp;</span>
</div>

<div class="header">
    <h1 class="page-title">Message</h1>
</div>

<div class="main-content">
    <button id="getstart" class="btn btn-primary" onclick="myFunction()"><i class="fa fa-play"></i> Start</button>
      <div id="mynetworkFA"></div>


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
