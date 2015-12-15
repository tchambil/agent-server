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

    <script src="/resource/js/formagents.js"></script>
    <script src="/resource/js/utils.js"></script>
    <script type="text/javascript" src="/resource/js/site/vis.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
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
    <style type="text/css">
        #mynetwork {
            width: 900px;
            height: 900px;
            border: 1px solid lightgray;
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

        <!--<Message]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".premium-menu1"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-cog"></i> Messages<i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="premium-menu1 nav nav-list collapse">
                <li><a href="/nautilodmessage.doo"><span class="fa fa-caret-right"></span> Sequence</a> </li>
                <li><a href="/listmessage.do"><span class="fa fa-caret-right"></span> List</a></li>
            </ul>
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
        <h1 class="page-title">Sequence Message</h1>
        <div class="col-md-4">
            <div class="widget">
                <ul class="cards list-group">

                    <li class="list-group-item">
                        <div class="form-group">
                            <label>Retrieve Task(ID)</label>
                            <input id="idresult" type="text" id="nameagentgeneral" class="form-control" value=""/>
                        </div>
                        <button id="search" class="btn btn-primary"> search</button>
                    </li>
                </ul>
            </div>
        </div>
    </div>

   <div class="main-content">


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

