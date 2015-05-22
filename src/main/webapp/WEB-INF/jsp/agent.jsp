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
    <script src="/resource/js/agent.js"></script>
    <script src="/resource/js/system.js"></script>
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
                class="fa fa-paper-plane"></span> Agent Server v1.0</span></a>

    </div>

    <div class="navbar-collapse collapse" style="height: 1px;">
        <ul id="main-menu" class="nav navbar-nav navbar-right">
            <li class="dropdown hidden-xs">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-user padding-right-small"
                          style="position:relative;top: 3px;"></span> Teo Chambil
                    <i class="fa fa-caret-down"></i>
                </a>

                <ul class="dropdown-menu">
                    <li><a href="">My Account</a></li>
                    <li class="divider"></li>
                    <li><a href="">Users</a></li>
                    <li class="divider"></li>
                    <li><a tabindex="-1" href="sign-in.jsp">Logout</a></li>
                </ul>
            </li>
        </ul>

    </div>
</div>
</div>


<div class="sidebar-nav">
    <ul>
        <!--<Plataform]-->
        <li><a href="#"
               data-target=".dashboard-menu" class="nav-header" data-toggle="collapse"><i
                class="fa fa-fw fa-dashboard"></i>Plataform<i class="fa fa-collapse"></i></a></li>
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

                <li><a href="/definition.do"><span class="fa fa-caret-right"></span> Definitions</a></li>
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
                <li><a href="/listagent.do"><span class="fa fa-caret-right"></span> List</a></li>
            </ul>
        </li>
        <!--<Groups]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".group-menu"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-arrows-alt"></i> Groups<i class="fa fa-collapse"></i><span
                class="label label-info">+0</span></a>
        </li>
        <li>
            <ul class="group-menu nav nav-list collapse">
                <li>
                    <a href="premium-profile.html"><span class="fa fa-caret-right"></span> Enhasasasanced Profile</a>
                </li>
            </ul>
        </li>
        <!--<Account]-->
        <li><a href="#" data-target=".accounts-menu" class="nav-header collapsed" data-toggle="collapse"><i
                class="fa fa-fw fa-briefcase"></i> Account <i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="accounts-menu nav nav-list collapse">
                <li><a href="/sign-in"><span class="fa fa-caret-right"></span> Sign In</a></li>
                <li><a href="/sign-up"><span class="fa fa-caret-right"></span> Sign Up</a></li>
                <li><a href="/resetpassword"><span class="fa fa-caret-right"></span> Reset Password</a></li>
            </ul>
        </li>

        <!--<Help]-->
        <li><a href="help.html" class="nav-header"><i class="fa fa-fw fa-question-circle"></i> Help</a>

        </li>
        <!--<Faq]-->
        <li><a href="faq.html" class="nav-header"><i class="fa fa-fw fa-comment"></i> Faq</a>
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
    <h1 class="page-title">Agents</h1>
</div>
<div class="main-content">
<ul class="nav nav-tabs">
    <li class="active"><a href="#home" data-toggle="tab">Register</a></li>
    <li><a href="#advanced" data-toggle="tab">Result</a></li>

</ul>

<div class="row">
<div id="myTabContent" class="tab-content">

<div class="tab-pane active in" id="home">

    <div class="col-md-3">
        <div class="widget">
            <ul class="cards list-group">

                <li class="list-group-item">
                    <p class="label label-warning header-label">General</p>
                    <div class="form-group">
                        <label>name</label>
                        <input type="text" id="nameagentgeneral" class="form-control" value="cuonter"/>

                    </div>
                </li>
                <li class="list-group-item">

                    <div class="form-group">

                        <label>User</label>
                        <select   id="DropUserGeneral" class="form-control">
                            <option value="Test">Selection Item</option>
                        </select>

                    </div>
                    <div class="form-group">
                        <label>Agent Definitions</label>
                        <select  id="DropAgentGeneral" class="form-control">
                            <option value="Test">Selection Item</option>
                        </select>

                    </div>

                </li>
            </ul>
            <button id="btnagentSdve" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
            <a href="#myModal" data-toggle="modal" class="btn btn-danger">Delete</a>
            <br/>      <br/>
            <div id="idMessagsedAgent" class="alert alert-info">
            </div>

        </div>
    </div><!-- finish col-md-3-->


</div><!-- finish simple-->

<div class="tab-pane fade" id="advanced">


    <div class="col-md-3">
        <div class="widget">
            <ul class="cards list-group">
                <li class="list-group-item">
                    <p class="label label-warning header-label">General</p>
                    <div class="form-group">
                        <label>name</label>
                        <input type="text" name="id" id="advancenamegeneral" class="form-control" value="cuonter"/>

                    </div>
                    <div class="form-group">
                        <label>User</label>

                        <select name="DropUsergeneral" id="advanceusergeneral" class="form-control">
                            <option value="Test">User Test</option>
                        </select>
                    </div>

                </li>
                <li>

                </li>

            </ul>

            <button id="btnadvanceSdve" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
            <a href="#myModal" data-toggle="modal" class="btn btn-danger">Delete</a>
            <br/>      <br/>
            <div id="idMessagsesdUser" class="alert alert-success">
            </div>

        </div>
    </div><!-- finish col-md-3-->

</div>

</div>
</div>

<div class="modal small fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">Delete Confirmation</h3>
            </div>
            <div class="modal-body">

                <p class="error-text"><i class="fa fa-warning modal-icon"></i>Are you sure you want to delete
                    the Agent Definitions?</p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancel</button>
                <button id="btndefinitionDelete" class="btn btn-danger" data-dismiss="modal">Delete</button>
            </div>
        </div>
    </div>
</div>


<footer>
    <hr>
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap
        Theme</a> by <a href="http://www.portnine.com" target="_blank">Portnine</a></p>

    <p>© 2015 <a href="http://www.portnine.com" target="_blank">Portnine</a></p>
</footer>
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
