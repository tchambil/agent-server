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
                    <li><a href="./">My Account</a></li>
                    <li class="divider"></li>
                    <li><a href="./">Users</a></li>
                    <li class="divider"></li>
                    <li><a tabindex="-1" href="sign-in.jsp">Logout</a></li>
                </ul>
            </li>
        </ul>

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
                <li><a href="/definitionscript.do"><span class="fa fa-caret-right"></span> Script(run)</a></li>
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
                    <a href="/message.do"><span class="fa fa-caret-right"></span> message</a>
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
        <li><a href="/tutorial" class="nav-header"><i class="fa fa-fw fa-question-circle"></i> Tutorial</a>

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
        <h1 class="page-title">Agent Definitions</h1>
    </div>
    <div class="main-content">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#home" data-toggle="tab">Simple</a></li>
            <li><a href="#advanced" data-toggle="tab">Advanced</a></li>
            <li><a href="#scrippre" data-toggle="tab">Predefined Script</a></li>
            <li><a href="#scrip" data-toggle="tab">Write Your Script</a></li>
            <li><a href="#json" data-toggle="tab">JSON</a></li>
        </ul>
    <div class="row">
           <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade" id="json">
                        <div class="col-md-8">
                        <pre>
                            <div id="codejson">
                            </div>
                         </pre>
                        </div>
                    </div><!-- finish simple-->
                    <div class="tab-pane fade" id="scrippre">
                        <div class="col-md-3">
                            <div class="widget">
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <p class="label label-warning header-label">General</p>
                                        <div class="form-group">
                                            <label>name</label>
                                            <input type="text"  name="advance" id="scriptpgeneral" class="form-control" value="def1"/>

                                        </div>
                                        <div class="form-group">
                                            <label>User</label>

                                            <select name="advance" id="scriptpusergeneral" class="form-control">
                                                <option value="Test">User Test</option>
                                            </select>
                                        </div>
                                    </li>

                                </ul>
                                <button id="btnscripptSave" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
                                <a href="#myModal" data-toggle="modal" class="btn btn-danger">Delete</a>
                                <br/>      <br/>
                                <div id="idMessagescripptdUser"   class="alert alert-success">
                                </div>

                            </div>
                        </div><!-- finish col-md-3-->
                        <div class="col-md-4">
                            <div class="widget">
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <p class="label label-primary header-label">menory</p>
                                        <div class="form-group">
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" value="">
                                                    {"name": "p", "type": "int", "default_value": 123 }
                                                </label>
                                            </div>

                                        </div>

                                    </li>

                                </ul>
                            </div>
                        </div><!-- finish col-md-3-->
                        <div class="col-md-4">
                            <div class="widget">
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <p class="label label-primary header-label">scrips</p>
                                        <div class="form-group">
                                            <div class="form-group">
                                                <label for="sel1">Select list:</label>
                                                <select class="form-control" id="sel1">
                                                    <option>{"name": "script1","script": "if (2 + 2 == 4) return 'Yes'; else return 'No';",  "public": true}</option>
                                                    <option>{"name": "script2","script": "int sum; for (sum = 0, int i = 1; i <= 10; i++) sum += i;  return sum;",  "public": true}</option>
                                                    <option>{"name": "script3","script": "string s; for (int i = 1; i <= 5; i++) s += ' ' + i;  return s;",  "public": true}</option>
                                                    <option>{"name": "script4","script": "float sum; for (sum = 0.0, int j = 0, float f = 0.11; j < 7; j++, f++) sum += f * 0.11;  return sum;",  "public": true}</option>
                                                    <option>{"name": "script5","script": "int sum = 0, int i = 0; for (;;){if (i > 10) break; sum += i++;}  return sum;",  "public": true}</option>
                                                    <option>{"name": "script6","script": "int sum = 0, int i = 0; while (true){if (i > 10) break; sum += i++;}  return sum;",  "public": true}</option>
                                                    <option>{"name": "script7","script": "int sum = 0, int i = 0; do {if (i > 10) break; sum += i++;} while (true);  return sum;",  "public": true}</option>
                                                    <option>{"name": "script8","script": "return sqrt(2);",  "public": true}</option>
                                                    <option>{"name": "script9","script":  "return 'abc'.length;",  "public": true}</option>
                                                    <option>{"name": "script10","script": "list lst; lst.add('Hello'); lst.add('World');lst.add('The End'); return lst.toString;",  "public": true}</option>
                                                    <option>{"name": "script11","script": "list lst; lst.add('Hello'); lst.add('World');lst.add('The End'); return lst.toString;",  "public": true}</option>
                                                    <option>{"name": "script12","script": "int get_p(){return p;}",  "public": true}</option>
                                                    <option>{"name": "script13","script": "web w; string s = w.get('http://twitter.com'); return w.statusCode;",  "public": true}</option>
                                                     <option>{"name": "script14","script": "web w; string s = w.get('http://www.weather.com/weather/today/10022'); string city = s.between('<h1 id=\"twc_loc_head\">', ' <nobr>'); string weather = s.between('class=\"twc-col-1\">', '\\n'); return 'City: ' + city;",  "public": true}</option>
                                               </select>
                                            </div>


                                        </div>

                                    </li>

                                </ul>
                            </div>
                        </div><!-- finish col-md-3-->

                    </div><!-- finish simple-->

                    <div class="tab-pane fade" id="scrip">

                        <div class="col-md-3">
                            <div class="widget">
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <p class="label label-warning header-label">General</p>
                                        <div class="form-group">
                                            <label>name</label>
                                            <input type="text"  name="advance" id="scriptgeneral" class="form-control" value="def1"/>

                                        </div>
                                        <div class="form-group">
                                            <label>User</label>

                                            <select name="advance" id="scriptusergeneral" class="form-control">
                                                <option value="Test">User Test</option>
                                            </select>
                                        </div>

                                    </li>
                                </ul>
                                <button id="btnscriptSave" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
                                <a href="#myModal" data-toggle="modal" class="btn btn-danger">Delete</a>
                                <br/>      <br/>
                                <div id="idMessagescriptdUser"   class="alert alert-success">
                                </div>

                            </div>
                        </div><!-- finish col-md-3-->
                        <div class="col-md-3">
                            <div class="widget">
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <p class="label label-primary header-label">memory</p>
                                        <div class="form-group">
                                            <label>name</label>
                                            <input type="text"name="advance"  id="scriptmenemory" class="form-control" value="p"/>

                                        </div>
                                        <div class="form-group">
                                            <label>type</label>

                                            <select   name="advance" id="scripttypenemory" class="form-control">
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
                                            <label>default_value</label>
                                            <input type="text"name="advance"  id="scriptvalue" class="form-control" value="123"/>

                                        </div>

                                    </li>

                                </ul>

                            </div>
                        </div><!-- finish col-md-3-->
                        <div class="col-md-3">
                            <div class="widget">
                                <ul class="cards list-group">

                                    <li class="list-group-item">
                                        <p class="label label-primary header-label">scripts</p>
                                        <div class="form-group">
                                            <label>name</label>
                                            <input type="text" name="advance" id="scriptsname" class="form-control" value="get_p"/>

                                        </div>
                                        <div class="form-group">
                                            <label>public</label>
                                            <select  name="advance"  id="scripttypeaccess" class="form-control">
                                                <option value="string">true</option>
                                                <option value="int">false</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>script</label>
                                            <textarea class="form-control" id="scripttextarea"  placeholder="int get_p(){return p;}"></textarea>


                                        </div>

                                    </li>
                                </ul>

                            </div>
                        </div><!-- finish col-md-3-->
                    </div><!-- finish simple-->
                    <div class="tab-pane active in" id="home">

                       <div class="col-md-3">
                                <div class="widget">
                                    <ul class="cards list-group">
                                        <li class="list-group-item">
                                            <p class="label label-warning header-label">General</p>
                                            <div class="form-group">
                                                <label>name</label>
                                                <input type="text"  name="simple" id="namegeneral" class="form-control" value="cuonter"/>

                                            </div>
                                            <div class="form-group">
                                                <label>User</label>
                                                <select name="simple" id="DropUserGeneral" class="form-control">
                                                    <option value="Test">User Test</option>
                                                </select>

                                            </div>

                                        </li>
                                    </ul>
                                    <button id="btnsimpleSdve" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
                                    <a href="#myModal" data-toggle="modal" class="btn btn-danger">Delete</a>
                                    <br/>      <br/>
                                    <div id="idMessageAgent"   class="alert alert-success">
                                    </div>

                                </div>
                            </div><!-- finish col-md-3-->

                       <div class="col-md-3">

                                <div class="widget">

                                    <ul class="cards list-group">
                                        <li class="list-group-item">
                                            <p class="label label-primary header-label">memory</p>
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

                                        </li>
                                    </ul>


                                </div>
                            </div><!-- finish col-md-3-->

                       <div class="col-md-3">
                                <div class="widget">
                                    <ul class="cards list-group">
                                        <li class="list-group-item">
                                            <p class="label label-primary header-label">timers</p>
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

                                        </li>
                                    </ul>
                                </div>
                            </div><!-- finish col-md-3-->

                       <div class="col-md-3">
                                <div class="widget">
                                    <ul class="cards list-group">
                                        <li class="list-group-item">
                                            <p class="label label-primary header-label">outputs</p>
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
                                                \"Count is \" + counter
                                            </div>

                                        </li>

                                    </ul>

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
                                            <input type="text"  name="advance" id="advancenamegeneral" class="form-control" value="cuonter"/>

                                        </div>
                                        <div class="form-group">
                                            <label>User</label>

                                            <select name="advance" id="advanceusergeneral" class="form-control">
                                                <option value="Test">User Test</option>
                                            </select>
                                        </div>

                                    </li>
                                </ul>
                                <button id="btnadvanceSdve" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
                                <a href="#myModal" data-toggle="modal" class="btn btn-danger">Delete</a>
                                <br/>      <br/>
                                <div id="idMessagsesdUser"   class="alert alert-success">
                                </div>

                            </div>
                        </div><!-- finish col-md-3-->

                        <div class="col-md-3">
                        <div class="widget">
                            <ul class="cards list-group">
                                <li class="list-group-item">
                                    <p class="label label-primary header-label">parameters input</p>
                                   <div class="form-group">
                                        <label>name</label>
                                        <input type="text"  name="advance" id="parametersname" class="form-control" value="timer_interval_expression"/>

                                    </div>
                                    <div class="form-group">
                                        <label>type</label>

                                        <select  name="advance"  id="typeparameterinput" class="form-control">
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
                                        <label>default_value</label>
                                        <input type="text"  name="advance" id="parametersdefault_value" class="form-control" value="seconds(3)"/>

                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <p class="label label-primary header-label">parameters out</p>
                                    <div class="form-group">
                                        <label>name</label>
                                        <input type="text"  name="advance"  id="parametersnameout" class="form-control" value="output_text"/>

                                    </div>
                                    <div class="form-group">
                                        <label>type</label>


                                        <select name="advance"   id="typeparameterout" class="form-control">

                                            <option value="string">String</option>
                                            <option value="int">Int</option>
                                           <option value="list">list</option>
                                            <option value="map">map</option>
                                            <option value="web">web</option>
                                            <option value="boolean">boolean</option>
                                            <option value="float">float</option>

                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>default_value</label>
                                        <input type="text" name="advance" id="parametersdefault_valueout" class="form-control" value="The count is"/>

                                    </div>
                                </li>
                               </ul>
                        </div>
                    </div><!-- finish col-md-3-->

                        <div class="col-md-3">
                        <div class="widget">
                            <ul class="cards list-group">
                                <li class="list-group-item">
                                    <p class="label label-primary header-label">memory</p>
                                    <div class="form-group">
                                        <label>name</label>
                                        <input type="text"name="advance"  id="advancenamenemory" class="form-control" value="counter"/>

                                    </div>
                                    <div class="form-group">
                                        <label>type</label>

                                        <select   name="advance" id="typenemory" class="form-control">
                                            <option value="int">Int</option>
                                            <option value="string">String</option>
                                            <option value="list">list</option>
                                            <option value="map">map</option>
                                            <option value="web">web</option>
                                            <option value="boolean">boolean</option>
                                            <option value="float">float</option>

                                        </select>
                                    </div>

                                </li>
                                <li class="list-group-item">
                                    <p class="label label-primary header-label">outputs</p>
                                    <div class="form-group">
                                        <label>name</label>
                                        <input type="text" name="advance" id="advancenameoutputs" class="form-control" value="output1"/>

                                    </div>
                                    <div class="form-group">
                                        <label>type</label>


                                        <select  name="advance"  id="typeoutputs" class="form-control">
                                            <option value="string">String</option>
                                            <option value="int">Int</option>
                                            <option value="list">list</option>
                                            <option value="map">map</option>
                                            <option value="web">web</option>
                                            <option value="boolean">boolean</option>
                                            <option value="float">float</option>

                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>compute</label>
                                        <input type="text" name="advance" id="advancecomputeoutputs" class="form-control" value="output_text + counter"/>

                                    </div>

                                </li>
                            </ul>

                        </div>
                    </div><!-- finish col-md-3-->

                         <div class="col-md-3">
                        <div class="widget">
                            <ul class="cards list-group">
                                <li class="list-group-item">
                                    <p class="label label-primary header-label">timers</p>
                                    <div class="form-group">
                                        <label>name</label>
                                        <input type="text" name="advance" id="advancenametimers" class="form-control" value="count"/>

                                    </div>
                                    <div class="form-group">
                                        <label>interval</label>
                                        <input type="text" name="advance" id="advanceintervaltimers" class="form-control" value="eval(timer_interval_expression)"/>

                                    </div>
                                    <div class="form-group">
                                        <label>script</label>
                                        <input type="text" name="advance"id="advancescripttimers" class="form-control" value="counter++;"/>

                                    </div>

                                </li>

                            </ul>

                        </div>
                    </div><!-- finish col-md-3-->

                    </div><!-- finish advanced-->

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
