<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Agent Server v1.0</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="../../resource/css/bootstrap.css">
    <link rel="stylesheet" href="../../resource/css/font-awesome.css">

    <script src="<c:url value="../../resource/js/site/jquery-1.11.1.min.js" />"></script>
    <script src="<c:url value="../../resource/js/plataform.js"/>"></script>
    <script src="<c:url value="../../resource/js/system.js"/>"></script>
    <script src="<c:url value="../../resource/js/site/jquery.knob.js"/>"></script>

    <script type="text/javascript">
        $(function () {
            $(".knob").knob();
        });
    </script>


    <link rel="stylesheet" type="text/css" href="../../resource/css/theme.css">
    <link rel="stylesheet" type="text/css" href="../../resource/css/premium.css">

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
                <li><a href="/" data-target=".legal-menu" class="nav-header collapsed" data-toggle="collapse"><i
                        class="fa fa-fw fa-dashboard"></i> Home</a></li>
        <!--<Users]-->
        <li><a href="/listuser.do" data-target=".legal-menu" class="nav-header collapsed" data-toggle="collapse"><i
                class="fa fa-fw fa-users"></i> Users</a>
        </li>
        <li>
            <ul class="users-menu nav nav-list collapse">
                <li><a href="/users.do"><span class="fa fa-caret-right"></span> Add</a></li>
                <li><a href="/listuser.do"><span class="fa fa-caret-right"></span> List</a></li>
            </ul>
        </li>
        <!--<Agent Definitions]-->
        <li><a href="#" data-target=".legal-menu" class="nav-header collapsed" data-toggle="collapse"><i
                class="fa fa-fw fa-tasks"></i> Capabilities (Actions)<i class="fa fa-collapse"></i></a>
        </li>
        <li>
            <ul class="legal-menu nav nav-list collapse">
                <li><a href="/listdefinition.do"><span class="fa fa-caret-right"></span> List Actions</a></li>
                <li><a href="/definitionsimple.do"><span class="fa fa-caret-right"></span> Actions Simple</a></li>
                <li><a href="/definitionsjson.do"><span class="fa fa-caret-right"></span> Actions in JSON</a></li>

            </ul>
        </li>
        <!--<Agents]-->
        <li><a href="/listagent.do" data-target=".legal-menu" class="nav-header collapsed">
            <i class="fa fa-fw fa-eye-slash"></i> Agents</a>
        </li>
        <!--<Groups]-->
        <li><a href="/listgroup.do" data-target=".legal-menu" class="nav-header collapsed">
            <i class="fa fa-fw fa-cog"></i> Multi-Agents(Groups)</a>
        </li>

        <!--<TEST´s]-->
        <li><a href="/testscript.do" data-target=".legal-menu" class="nav-header collapsed">
            <i class="fa fa-fw fa-globe"></i> Test</a>
        </li>
        <!--<Aplications]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".group-menu"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-arrows-alt"></i> Aplications<i class="fa fa-collapse"></i><span
                class="label label-info">1</span></a>
        </li>
        <li>
            <ul class="group-menu nav nav-list collapse">
                <li>
                    <a href="/nautilodindex.do"><span class="fa fa-caret-right"></span> NautiLOD Distributed</a>
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
        <h1 class="page-title">Plataform</h1>
    </div>

    <div class="main-content">

        <ul class="nav nav-tabs">
            <li class="active">
                <a href="#manage" data-toggle="tab">Manage</a>
            </li>
            <li>
                <a href="#status" data-toggle="tab" id="btnstatus">Status</a>
            </li>
            <li>
                <a href="#config" data-toggle="tab" id="btnconfig">Config</a>
            </li>
            <li>
                <a href="#about" data-toggle="tab" id="putabout">About</a>
            </li>
        </ul>

        <div class="row">

            <div class="col-md-4">
                <br>

                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="manage">

                        <div>
                            <button id="getstart" class="btn btn-primary"><i class="fa fa-play"></i> Start</button>
                            <button id="putpause" class="btn btn-primary"><i class="fa fa-pause"></i> Pause</button>
                            <button id="putrestart" class="btn btn-primary"><i class="fa fa-refresh"></i> Resume
                            </button>
                            <a href="#myModal" data-toggle="modal" class="btn btn-danger"><i class="fa fa-stop"></i>
                                Stop</a>
                        </div>
                        <br/>      <br/>

                        <div id="idmessage"  class="alert alert-info">

                        </div>

                    </div>
                    <div class="tab-pane fade" id="status">

                        <ul class="cards compact list-group">
                            <li class="list-group-item">
                                <p class="title" > status</p>
                                <span id="statusInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > since</p>
                                <span id="sinceInput" class="text-warning" style="font-size: 1em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > num_registered_users</p>
                                <span id="num_registered_usersInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > num_active_users</p>
                                <span id="num_active_usersInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > num_registered_agents</p>
                                <span id="num_registered_agentsInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > num_active_agents</p>
                                <span id="num_active_agentsInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                        </ul>


                    </div>

                    <div class="tab-pane fade" id="config">

                         <div class="cards compact list-group">
                             <ul class="list-group">
                                <li class="list-group-item">
                                      <i class="fa fa-share">Plataform </i>
                                     <span id="Plataformout" class="text-warning"> </span>
                                </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">description</i>
                                     <span id="descriptionout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">software </i>
                                     <span id="softwareout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">version </i>
                                     <span id="versionout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">website </i>
                                     <span id="websiteout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">admin_approve_user_create </i>
                                     <span id="admin_approve_user_createout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">mail_confirm_user_create </i>
                                     <span id="mail_confirm_user_createout" class="text-warning"> </span>
                                 </li>

                                 <li class="list-group-item">
                                     <i class="fa fa-share">contact </i>
                                     <span id="contactout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">user_agent_name </i>
                                     <span id="user_agent_nameout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">default_web_page_refresh_interval </i>
                                     <span id="default_web_page_refresh_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">minimum_web_page_refresh_interval </i>
                                     <span id="minimum_web_page_refresh_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">minimum_web_site_access_interval </i>
                                     <span id="minimum_web_site_access_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">execution_limit_level_1 </i>
                                     <span id="minimum_web_access_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">execution_limit_level_2 </i>
                                     <span id="execution_limit_level_2out" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">execution_limit_level_3 </i>
                                     <span id="execution_limit_level_3out" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">execution_limit_level_4 </i>
                                     <span id="execution_limit_level_4out" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">execution_limit_default_level </i>
                                     <span id="execution_limit_default_levelout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">max_users </i>
                                     <span id="max_usersout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">max_instances </i>
                                     <span id="max_instancesout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">implicitly_deny_web_access </i>
                                     <span id="implicitly_deny_web_accessout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">implicitly_deny_web_write_access </i>
                                     <span id="implicitly_deny_web_write_accessout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">default_trigger_interval </i>
                                     <span id="default_trigger_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">default_reporting_interval </i>
                                     <span id="default_reporting_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">minimum_trigger_interval </i>
                                     <span id="minimum_trigger_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">minimum_reporting_interval </i>
                                     <span id="minimum_reporting_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">default_limit_instance_states_stored </i>
                                     <span id="default_limit_instance_states_storedout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">maximum_limit_instance_states_stored </i>
                                     <span id="maximum_limit_instance_states_storedout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">default_limit_instance_states_returned </i>
                                     <span id="default_limit_instance_states_returnedout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">maximum_limit_instance_states_returned </i>
                                     <span id="maximum_limit_instance_states_returnedout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">mail_access_enabled </i>
                                     <span id="mail_access_enabledout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">minimum_mail_access_interval </i>
                                     <span id="minimum_mail_access_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">minimum_host_mail_access_interval </i>
                                     <span id="minimum_host_mail_access_intervalout" class="text-warning"> </span>
                                 </li>
                                 <li class="list-group-item">
                                     <i class="fa fa-share">minimum_address_mail_access_interval </i>
                                     <span id="minimum_address_mail_access_intervalout" class="text-warning"> </span>
                                 </li>
                             </ul>
                         </div>

                    </div>
                    <div class="tab-pane fade" id="about" class="json">

                        <ul class="cards compact list-group">
                            <li class="list-group-item">
                                <p class="title" > Plataform</p>
                                <span id="PlataformInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > software</p>
                                <span id="softwareInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > version</p>
                                <span id="versionInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > description</p>
                                <span id="descriptionInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > website</p>
                                <span id="websiteInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                            <li class="list-group-item">
                                <p class="title" > contact</p>
                                <span id="contactInput" class="text-warning" style="font-size: 1.5em; line-height: 1em;margin: 0px;">
                                </span>
                            </li>
                         </ul>


                    </div>
                </div>
            </div>

            <div class="modal small fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
                            <h3 id="myModalLabel">Stop Confirmation</h3>
                        </div>
                        <div class="modal-body">

                            <p class="error-text"><i class="fa fa-warning modal-icon"></i>Are you sure you want to Stop
                                the Plataform?</p>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancel</button>
                            <button class="btn btn-danger" id="putstop" data-dismiss="modal">Stop</button>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            <footer>
                <hr>
                <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free
                    Bootstrap
                    Theme</a> by <a href="http://www.portnine.com" target="_blank">Portnine</a></p>

                <p>� 2015 <a href="http://www.portnine.com" target="_blank">Portnine</a></p>
            </footer>
        </div>

</div>
<script src="<c:url value="../../resource/js/site/bootstrap.js"/>"></script>

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
