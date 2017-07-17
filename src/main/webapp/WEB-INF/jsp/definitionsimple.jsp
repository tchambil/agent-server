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
    <script src="/resource/js/agentdefinition.js"></script>
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
                class="fa fa-paper-plane"></span> Plataform Agent Server v1.0</span></a>

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
        <span class="label label-danger">Status:</span> <span id="headerStatus">null</span>
        <span>&nbsp;&nbsp;</span>
        <span class="label label-success">Address IP:</span> <span id="headerIp">null</span>
        <span>&nbsp;&nbsp;</span>
        <span class="label label-info">Server:</span> <span id="headerServer">null</span>
        <span>&nbsp;&nbsp;</span>
    </div>
    <div class="header">
        <h1 class="page-title">Agent Definitions</h1>
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
                                    <label>name</label>
                                    <input type="text"  name="simple" id="namegeneral" class="form-control" value="Action_cuonter"/>
                                </div>
                                <div class="form-group">
                                    <label>Description</label>
                                    <input type="text"  name="simple" id="descriptiongeneral" class="form-control" value="First Action"/>
                                </div>

                                <div class="form-group">
                                    <label>User</label>

                                    <select name="simple" id="DropUserGeneral" class="form-control">
                                        <option value="Test">User Test</option>
                                    </select>

                                </div>

                            </div>
                        </fieldset>
                    </div>
                    <div class="step well">
                        <fieldset>
                            <div class="form-top">
                                <div class="form-top-left">
                                    <h1>Memory</h1>

                                </div>

                            </div>
                            <div class="form-bottom">
                                <div class="form-group">
                                    <label>name</label>
                                    <input type="text" name="simple" id="simplenamenemory" class="form-control" value="counter"/>


                                </div>
                                <div class="form-group">
                                    <label>type</label>


                                    <select  name="simple" id="simpletypenemory" class="form-control">
                                        <option value="int">Int</option>
                                        <option value="string">String</option>
                                        <option value="list">list</option>
                                        <option value="map">map</option>
                                        <option value="web">web</option>
                                        <option value="boolean">boolean</option>
                                        <option value="float">float</option>

                                    </select>
                                </div>

                            </div>
                        </fieldset>
                    </div>
                    <div class="step well">
                        <fieldset>
                            <div class="form-top">
                                <div class="form-top-left">
                                    <h1>Script</h1>
                                </div>

                            </div>
                            <div class="form-bottom">
                                <div class="form-group">
                                    <label>name</label>
                                    <input type="text"  name="simple" id="simplenametimers" class="form-control" value="count"/>

                                </div>
                                <div class="form-group">
                                    <label>interval</label>
                                    <input type="text"  name="simple" id="simpleintervaltimers" class="form-control" value="seconds(3)"/>

                                </div>
                                <div class="form-group">
                                    <label>script</label>
                                    <input type="text" name="simple" id="simplescripttimers" class="form-control" value="counter++;"/>

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
                                    <input type="text"  name="simple" id="simplenameoutputs" class="form-control" value="output1"/>

                                </div>
                                <div class="form-group">
                                    <label>type</label>

                                    <select name="simple"  id="simpletypeoutputs" class="form-control">
                                        <option value="int">Int</option>
                                        <option value="string">String</option>
                                        <option value="list">list</option>
                                        <option value="map">map</option>
                                        <option value="web">web</option>
                                        <option value="boolean">boolean</option>
                                        <option value="float">float</option>

                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>compute</label>
                                    <input type="text" name="simple"  id="simplecomputeoutputs" class="form-control" value="counter"/>

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
        <div id="myModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- dialog body -->
                    <div class="modal-body">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        Add was successful !
                    </div>
                    <!-- dialog buttons -->
                    <div class="modal-footer"><button type="button" class="btn btn-primary">OK</button></div>
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

