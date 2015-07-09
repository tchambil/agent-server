<!doctype html>

<html>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <script src="/resource/js/testscrip.js"></script>
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
                <li><a href="/listagent.do"><span class="fa fa-caret-right"></span> List</a></li>
            </ul>
        </li>
        <!--<Groups]-->
        <li data-popover="true" rel="popover" data-placement="right"><a href="#" data-target=".group-menu"
                                                                        class="nav-header collapsed"
                                                                        data-toggle="collapse">
            <i class="fa fa-fw fa-arrows-alt"></i> Server Group<i class="fa fa-collapse"></i><span
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
        <!--<Faq]-->
        <li><a href="/faq" class="nav-header"><i class="fa fa-fw fa-comment"></i> Faq</a>
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
        <h1 class="page-title">Script for TEST´s</h1>
    </div>
    <div class="main-content">
        <ul class="nav nav-tabs">


           <li class="active"><a href="#json" data-toggle="tab">Predefined Script</a></li>
            <li><a href="#scrippre" data-toggle="tab">Write Script</a></li>
        </ul>
    <div class="row">
           <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="json">
                        <div class="col-md-8">
                               <div class="btn-group">
                                    <button type="button" class="btn btn-primary">WEB</button>

                                    <button type="button" class="btn btn-primary"
                                            data-toggle="dropdown">
                                        <span class="caret"></span>

                                    </button>

                                    <ul id="menuweb" class="dropdown-menu" role="menu">

                                        <li><a href="#">web w; string s = w.get('http://twitter.com'); return w.statusCode;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">web w; return w.get('http://feeds.reuters.com/reuters/MostRead?format=xml').xml;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">web w; return w.get('http://www.newyorkfed.org/rss/feeds/fedfunds.xml').xml;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">web w; return w.get('http://www.newyorkfed.org/rss/feeds/fedfunds.xml').xml['rdf:RDF']['channel']['description'];</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">web w; return w.get('http://www.newyorkfed.org/rss/feeds/fedfunds.xml').xml['rdf:RDF']['item']['description']['text_1'];</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">web w; return w.get('http://www.newyorkfed.org/rss/feeds/fedfunds.xml').xml['rdf:RDF']['item']['cb:statistics']['cb:interestRate']['cb:value']['text_1'].float;</a></li>

                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success">Operations Basic</button>

                                    <button type="button" class="btn btn-success"
                                            data-toggle="dropdown">
                                        <span class="caret"></span>

                                    </button>

                                    <ul id="menuoper" class="dropdown-menu" role="menu">
                                        <li><a href="#">if (2 + 2 == 4) return 'Yes'; else return 'No';</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">int sum; for (sum = 0, int i = 1; i <= 10; i++) sum += i;  return sum;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">string s; for (int i = 1; i <= 5; i++) s += ' ' + i;  return s;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">float sum; for (sum = 0.0, int j = 0, float f = 0.11; j < 7; j++, f++) sum += f * 0.11;  return sum;</a></li>

                                        <li class="divider"></li>
                                        <li><a href="#">int sum = 0, int i = 0; for (;;){if (i > 10) break; sum += i++;}  return sum;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">int sum = 0, int i = 0; while (true){if (i > 10) break; sum += i++;}  return sum;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">int sum = 0, int i = 0; do {if (i > 10) break; sum += i++;} while (true);  return sum;</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-info">List</button>

                                    <button type="button" class="btn btn-info"
                                            data-toggle="dropdown">
                                        <span class="caret"></span>

                                    </button>

                                    <ul id="menulist" class="dropdown-menu" role="menu">
                                        <li><a href="#">list lst; lst.add('A'); lst.add('B');lst.add('C'); return lst.toString;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">list lst; lst.add('Hello'); lst.add('World');lst.add('The End'); return lst.toString;</a></li>
                                        <li class="divider"></li>
                                        <li ><a href="#">list lst; lst.add('Hello'); lst.add('World');lst.add('The End'); return lst.toString;</a></li>
                                        <li class="divider"></li>
                                        <li ><a href="#">int get_p(){return p;};</a></li>
                                        <li class="divider"></li>
                                        <li list-group-item list-group-item-info><a href="#">map m; m['name'] = 'John Doe'; return m['name'];</a></li>
                                        <li class="divider"></li>
                                        <li list-group-item list-group-item-info><a href="#">list lst = list(123, 456, 'Hello', 2.34, null, true); return lst;</a></li>
                                        <li class="divider"></li>
                                        <li list-group-item list-group-item-info><a href="#">map m; m.put('name', 'John Doe'); m.put('address', 'Here'); m.put('rank', 123); m.put('amount', 4.56); m.put('valid', true); return m.keys.toJson;</a></li>

                                    </ul>
                                </div>

                                <div class="btn-group">
                                    <button type="button" class="btn btn-warning">Methods</button>

                                    <button type="button" class="btn btn-warning"
                                            data-toggle="dropdown">
                                        <span class="caret"></span>

                                    </button>

                                    <ul id="menumethod" class="dropdown-menu" role="menu">
                                        <li><a href="#">return 'abc'.matches('xyz');</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">return 'abc'.matches('abc');</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">return 'abc'.matches('ab');</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">return 'abcdefghi'.split('');</a></li>


                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger">Matematics</button>

                                    <button type="button" class="btn btn-danger dropdown-toggle"
                                            data-toggle="dropdown">
                                        <span class="caret"></span>

                                    </button>

                                    <ul id="menumatec" class="dropdown-menu" role="menu">
                                        <li><a href="#">return [2, 5, 3, 9].sum;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">return [2, 5, 3, 9].avg;</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">true ? true : false"</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">min(123, 456, 789)</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">max(456, 789, 123)</a></li>
                                    </ul>
                                </div>
                          </div>
                        <div class="col-md-5">

                            <div class="widget">
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <p class="label label-primary header-label">Test</p>
                                        <div class="form-group">
                                            <label><p><b>script</b></p></label>

                                            <textarea id="scriptid" class="form-control" ></textarea>
                                        </div>

                                    </li>

                                </ul>
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <div class="form-group">
                                            <label><p><b>result:</b></p></label>
                                            <div id="resultscript">
                                            </div>
                                        </div>
                                    </li>
                               </ul>
                                </div>

                        </div>
                    </div><!-- finish simple-->

                    <div class="tab-pane fade" id="scrippre">
                      <div class="col-md-5">
                            <div class="widget">
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <p class="label label-primary header-label">scrips</p>
                                        <textarea id="textarea1" class="form-control"   placeholder="int get_p(){return p;}"></textarea>
                                        <button id="getstart" class="btn btn-primary"><i class="fa fa-play"></i> Test</button>
                                    </li>

                                </ul>
                                <ul class="cards list-group">
                                    <li class="list-group-item">
                                        <div class="form-group">
                                            <label><p><b>result:</b></p></label>
                                            <div id="resultex">
                                            </div>
                                        </div>
                                    </li>

                                </ul>
                            </div>
                        </div><!-- finish col-md-3-->

                    </div><!-- finish simple-->

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
