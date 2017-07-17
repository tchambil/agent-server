<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="<c:url value="../../resource/js/group.js"/>"></script>
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
        <span class="label label-danger">Status:</span> <span id="headerStatus">null</span>
        <span>&nbsp;&nbsp;</span>
        <span class="label label-success">Address IP:</span> <span id="headerIp">null</span>
        <span>&nbsp;&nbsp;</span>
        <span class="label label-info">Server:</span> <span id="headerServer">null</span>
        <span>&nbsp;&nbsp;</span>
    </div>

    <div class="header">
        <h1 class="page-title">Plataform</h1>

    </div>
    <div class="modal fade" id="Suscribe" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Subscribe Agents</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <ul class="cards list-group">
                            <li class="list-group-item">
                                <div class="form-group">
                                    <label class="col-lg-2 control-label">Server</label>
                                    <input type="text" id="server" class="form-control" value="" placeholder="http://"/>
                                    <br>
                                    <button id="getGroups" class="btn btn-primary">Get Groups</button>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label">Groups</label>

                                    <select id="Dropgroups" class="form-control">
                                        <option value="Test">Selection Item</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label">Agents</label>
                                    <select id="Droplistagents" class="form-control">
                                        <option value="Test">Selection Item</option>
                                    </select>


                                </div>
                            </li>
                        </ul>

                    </div>
                    <div class="modal-footer">
                        <button id="btnsuscribe" class="btn btn-primary">Save</button>
                        <button  class="btn btn-info" data-dismiss="modal">Cancel</button>

                    </div>
                </div>
            </div>

        </div>
    </div>
    <div class="modal fade" id="AddGroup" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Subscribe Agents</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-4">
                            <ul class="cards list-group">
                                <li class="list-group-item">
                                    <div class="form-group">
                                        <label>Groups</label>
                                        <select id="DroplistAddgroups" class="form-control">
                                            <option value="Test">Selection Item</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Agents</label>
                                        <select id="DroplistAddagents" class="form-control">
                                            <option value="Test">Selection Item</option>
                                        </select>

                                    </div>
                                </li>
                            </ul>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button id="btnadd" class="btn btn-primary"><i class="fa fa-play"></i>Add</button>
                        <button  class="btn btn-info" data-dismiss="modal">Cancel</button>

                    </div>
                </div>
            </div>

        </div>
    </div>
    <div class="modal fade" id="CreateGroup" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Create Groups Agents</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="widget">
                                <ul class="cards list-group">

                                    <li class="list-group-item">

                                        <div class="form-group">
                                            <label>name</label>
                                            <input type="text" id="name" class="form-control" value=""/>

                                        </div>
                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea id="Description" class="form-control" rows="2"></textarea>

                                        </div>
                                    </li>

                                </ul>


                            </div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button id="btnsavegroup" class="btn btn-primary"><i class="fa fa-save"></i>Save</button>
                        <button  class="btn btn-info" data-dismiss="modal">Cancel</button>
                        <div id="idMessagsedAgent" class="alert alert-info">
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>


    <div class="main-content">
        <div class="row">
            <div class="col-md-6">

                <div class="form-group">
                    <label class="control-label col-sm-2" for="Droplistgroups">Groups</label>
                    <div class="col-sm-10">
                    <select id="Droplistgroups" class="form-control">
                        <option value="Test">Selection Item</option>
                    </select>
                    </div>
                </div>
            </div>
            <div class="col-md-2">
                <button class="btn btn-primary pull-right" data-toggle="modal" data-target="#CreateGroup"><i class="fa fa-plus"></i> Create New Group</button>
            </div>
            <div class="col-md-2">
                <button class="btn btn-primary pull-right" data-toggle="modal" data-target="#AddGroup"><i class="fa fa-plus"></i> Add Agent to Group</button>
            </div>
            <div class="col-md-2">
                <button class="btn btn-primary pull-right" data-toggle="modal" data-target="#Suscribe"><i class="fa fa-plus"></i> Subscribe Agents</button><p></p>
            </div>
        </div>





        </br>
        <table id="idtablelist" class="table table-hover">
            <thead>
            <tr>
                <th>group</th>
                <th>aid</th>
                <th>Name</th>
                <th>addresses</th>
                <th>Type</th>
                <th>Status</th>
                <th style="width: 3.5em;"></th>
            </tr>
            </thead>
            <tbody id="listagents">
            </tbody>
        </table>

        <ul class="pagination">
            <li><a href="#">&laquo;</a></li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">&raquo;</a></li>
        </ul>


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
