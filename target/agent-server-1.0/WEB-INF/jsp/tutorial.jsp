<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <title>Agent Server v1.0</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">


    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="../../resource/css/bootstrap.css">
    <link rel="stylesheet" href="../../resource/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="../../resource/css/shCoreEclipse.css">
    <script src="<c:url value="../../resource/js/site/jquery-1.11.1.min.js" />"></script>
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
                <li><a href="/agent.do"><span class="fa fa-caret-right"></span> Agent</a></li>
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
            <i class="fa fa-fw fa-arrows-alt"></i>Aplications<i class="fa fa-collapse"></i><span
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

    <div class="header">
        <h1 class="page-title">Tutorial for the Agent Server (v1.0)</h1>
    </div>

    <div class="main-content">
        <p class="definition">


            The agent server supports a REST API that uses JSON as its primary information format.

            The curl command is probably the simplest and best way to experiment with the agent server.

            The tutorial command assumes you are using the bash shell (e.g., cygwin on Windows), or something
            comparable.
        </p>

        <h2>Create a new new user</h2>

        <p class="label label-primary header-label">url_base: curl -X POST http://localhost:8080/users</p>

        <div id="code1" class="syntaxhighlighter  jscript">
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td class="gutter">
                        <div class="line number1 index0 alt0">1</div>
                        <div class="line number2 index1 alt1">2</div>
                        <div class="line number3 index2 alt2">3</div>
                        <div class="line number4 index3 alt3">4</div>
                        <div class="line number5 index4 alt4">5</div>
                        <div class="line number6 index5 alt5">6</div>
                        <div class="line number7 index6 alt6">7</div>

                    </td>
                    <td class="code">
                        <div class="container">
                            <div class="line number1 index0 alt0"><code class="jscript plain">{</code></div>
                            <div class="line number2 index1 alt1"><code class="jscript string">"display_name": "User
                                Name",</code></div>
                            <div class="line number3 index2 alt2"><code class="jscript string">"full_name": "User Full
                                Name",</code></div>
                            <div class="line number4 index3 alt3"><code class="jscript string">"email":
                                "user@example.com", </code></div>
                            <div class="line number5 index4 alt4"><code class="jscript string">"nick_name":
                                "useraname",</code></div>
                            <div class="line number6 index5 alt4"><code class="jscript string">"company": "Your
                                organization" </code></div>
                            <div class="line number7 index6 alt6"><code class="jscript plain">}</code></div>
                        </div>

                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <h2>Create a new agent definition</h2>

        <p>We went through all of that trouble creating a new user because agents are owned by or associated
            with a specific user.</p>

        <p>So, here's the command to create a simple "AgentDefinition_1" agent associated with the new user</p>

        <p class="label label-primary header-label">url_base: curl -X POST
            'http://localhost:8080/users/test-user-1/agent_definitions' -H Content-type:application/json -d</p>

        <div id=cod2" class="syntaxhighlighter  jscript">
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td class="gutter">
                        <div class="line number1 index0 alt0">1</div>
                        <div class="line number2 index1 alt1">2</div>
                        <div class="line number3 index2 alt2">3</div>
                        <div class="line number4 index3 alt3">4</div>
                        <div class="line number5 index4 alt4">5</div>
                        <div class="line number6 index5 alt5">6</div>
                        <div class="line number7 index6 alt6">7</div>
                        <div class="line number8 index7 alt7">8</div>
                        <div class="line number9 index8 alt8">9</div>
                        <div class="line number10 index9 alt9">10</div>
                        <div class="line number11 index10 alt10">11</div>
                        <div class="line number12 index11 alt11">12</div>
                        <div class="line number13 index12 alt12">13</div>
                        <div class="line number14 index13 alt13">14</div>
                        <div class="line number15 index14 alt14">15</div>
                        <div class="line number16 index15 alt15">16</div>
                        <div class="line number17 index16 alt16">17</div>
                        <div class="line number18 index17 alt17">18</div>
                        <div class="line number19 index18 alt18">19</div>
                        <div class="line number20 index19 alt19">20</div>
                        <div class="line number21 index20 alt20">21</div>
                        <div class="line number22 index21 alt21">22</div>
                        <div class="line number23 index22 alt22">23</div>
                    </td>
                    <td class="code">
                        <div class="container">
                            <div class="line number1 index0 alt0"><code class="jscript plain">{</code></div>
                            <div class="line number2 index1 alt1"><code class="jscript plain"> "name":
                                "AgentDefinition_1",</code></div>
                            <div class="line number3 index2 alt2"><code class="jscript plain"> "memory": [</code></div>
                            <div class="line number4 index3 alt3"><code class="jscript plain"> {</code></div>
                            <div class="line number5 index4 alt4"><code class="jscript plain"> "name":
                                "counter",</code></div>
                            <div class="line number6 index5 alt5"><code class="jscript plain"> "type": "int"</code></div>
                            <div class="line number7 index6 alt6"><code class="jscript plain"> }</code></div>
                            <div class="line number8 index7 alt7"><code class="jscript plain"> ],</code></div>
                            <div class="line number9 index8 alt8"><code class="jscript plain"> "timers": [</code></div>
                            <div class="line number10 index9 alt9"><code class="jscript plain"> {</code></div>
                            <div class="line number11 index10 alt10"><code class="jscript plain"> "name": "count",</code></div>
                            <div class="line number12 index11 alt11"><code class="jscript plain"> "interval": "seconds(3)",</code></div>
                            <div class="line number13 index12 alt12"><code class="jscript plain"> "script":
                                "counter++;"</code></div>
                            <div class="line number14 index13 alt13"><code class="jscript plain"> }</code></div>
                            <div class="line number15 index14 alt14"><code class="jscript plain"> ],</code></div>
                            <div class="line number16 index15 alt15"><code class="jscript plain"> "outputs": [</code>
                            </div>
                            <div class="line number17 index16 alt16"><code class="jscript plain"> {</code></div>
                            <div class="line number18 index17 alt17"><code class="jscript plain"> "name":
                                "output1",</code></div>
                            <div class="line number19 index18 alt18"><code class="jscript plain">"type": "string",</code>
                            </div>
                            <div class="line number20 index19 alt18"><code class="jscript plain"> "compute": "\"Count is
                                \" + counter"</code></div>
                            <div class="line number21 index20 alt19"><code class="jscript plain"> },</code></div>
                            <div class="line number22 index21 alt21"><code class="jscript plain"> ]</code></div>
                            <div class="line number23 index22 alt22"><code class="jscript plain">}</code></div>


                        </div>

                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <h2>Now instantiate the counter agent:</h2>

        <p class="label label-primary header-label">url_base: curl -X POST 'http://localhost:8080/users/test-user-1/agents' -H Content-type:application/json -d' </p>

        <div id="code3" class="syntaxhighlighter  jscript">
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td class="gutter">
                        <div class="line number1 index0 alt0">1</div>
                        <div class="line number2 index1 alt1">2</div>
                        <div class="line number3 index2 alt2">3</div>
                        <div class="line number4 index3 alt3">4</div>


                    </td>
                    <td class="code">
                        <div class="container">
                            <div class="line number1 index0 alt0"><code class="jscript plain">{</code></div>
                            <div class="line number2 index1 alt1"><code class="jscript plain"> "name": "Agent1",</code></div>
                            <div class="line number3 index2 alt2"><code class="jscript plain"> "definition": "AgentDefinition_1"</code></div>
                            <div class="line number4 index3 alt3"><code class="jscript plain">}</code></div>
                        </div>

                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <h2>Now instantiate the counter agent:</h2>

        <p class="label label-primary header-label">url_base: curl 'http://localhost:8080/users/test-user-1/agents/agent1/output' </p>

        <div id="code4" class="syntaxhighlighter  jscript">
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td class="gutter">
                        <div class="line number1 index0 alt0">1</div>

                    </td>
                    <td class="code">
                        <div class="container">
                            <div class="line number1 index0 alt0"><code class="jscript plain">{"output1": "Count is 23"}</code></div>

                        </div>

                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        Wait a few seconds and try that again..., Basically, the count increments every three seconds.

    </div>

    <footer>
        <hr>
        <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free
            Bootstrap
            Theme</a> by <a href="http://www.portnine.com" target="_blank">Portnine</a></p>

        <p>� 2015 <a href="http://www.portnine.com" target="_blank">Portnine</a></p>
    </footer>


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
