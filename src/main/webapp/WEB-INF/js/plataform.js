$(document).ready(function () {
    // Random Person AJAX Request
    $('#getstart').click(function (e) {
        $.ajax({
            type: "GET",
            url: '../status/start',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {
                $("#idmessage").empty();
                $("#idmessage").append(data.message);
                $("#headerStatus").empty();
                $("#headerStatus").append('[ '+data.status+' ]');
            },

            error: function (jqXHR, status) {
                // alert(jqXHR.responseText + status);
                $('#personResponse').empty();
                $('#personResponse').append(jqXHR.responseText);

            }
        });
    });
    $('#putpause').click(function (e) {
        $.ajax({
            type: "PUT",
            url: '../status/pause',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {

                $("#headerStatus").empty();
                $("#headerStatus").append('[ '+data.status+' ]');
                $("#idmessage").empty();
                $("#idmessage").append(data.message);
            },

            error: function (jqXHR, status) {
                // alert(jqXHR.responseText + status);
                $('#personResponse').empty();
                $('#personResponse').append(jqXHR.responseText);

            }
        });
    });

    $('#putrestart').click(function (e) {
        $.ajax({
            type: "PUT",
            url: '../status/resume',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {
                $("#idmessage").empty();
                $("#idmessage").append(data.message);
                $("#headerStatus").empty();
                $("#headerStatus").append('[ '+data.status+' ]');
            },

            error: function (jqXHR, status) {
                $('#personResponse').empty();
                $('#personResponse').append(jqXHR.responseText);

            }
        });
    });

    $('#putresume').click(function (e) {
        $.ajax({
            type: "PUT",
            url: '../status/resume',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {
                $("#idmessage").empty();
                $("#idmessage").append(data.message);
                $("#headerStatus").empty();
                $("#headerStatus").append('[ '+data.status+' ]');
            },

            error: function (jqXHR, status) {
                $('#personResponse').empty();
                $('#personResponse').append(jqXHR.responseText);

            }
        });
    });

    $('#putstop').click(function (e) {
        $.ajax({
            type: "PUT",
            url: '../status/stop',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {

                $("#idmessage").empty();
                $("#idmessage").append(data.message);

                $("#headerStatus").empty();
                $("#headerStatus").append('[ '+data.status+' ]');

            },

            error: function (jqXHR, status) {
                $('#personResponse').empty();
                $('#personResponse').append(jqXHR.responseText);

            }
        });
    });
    $('#putabout').click(function (e) {
        $.ajax({
            type: "GET",
            url: '../about',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {
              //  $('#responseabout').empty();
              //  $('#responseabout').append(JSON.stringify(data, null, "\t"));
                $("#PlataformInput").empty();
                $("#softwareInput").empty();
                $("#versionInput").empty();
                $("#descriptionInput").empty();
                $("#websiteInput").empty();
                $("#contactInput").empty();
                $("#PlataformInput").append(data.Plataform);
                $("#softwareInput").append(data.software);
                $("#versionInput").append(data.version);
                $("#descriptionInput").append(data.description);
                $("#websiteInput").append(data.website);
                $("#contactInput").append(data.contact);

            }
        });
    });

    $("#btnstatus").click(function (e) {
        $.get('../status', function (server) {
           // $('#responsestatus').empty();
          //  $('#responsestatus').append(JSON.stringify(server, null, "\t"));
            $("#statusInput").empty();
            $("#headerstatus").empty();
            $("#sinceInput").empty();
            $("#num_registered_usersInput").empty();
            $("#num_active_usersInput").empty();
            $("#num_registered_agentsInput").empty();
            $("#num_active_agentsInput").empty();

            $("#statusInput").append(server.status);
            $("#headerstatus").append(server.status);
            $("#sinceInput").append(server.since);
            $("#num_registered_usersInput").append(server.num_registered_users);
            $("#num_active_usersInput").append(server.num_active_users);
            $("#num_registered_agentsInput").append(server.num_registered_agents);
            $("#num_active_agentsInput").append(server.num_active_agents);

        });
    });

    $("#btnstatus").click(function (e) {
        $.get('../status', function (server) {
            // $('#responsestatus').empty();
            //  $('#responsestatus').append(JSON.stringify(server, null, "\t"));
            $("#statusInput").empty();

            $("#sinceInput").empty();
            $("#num_registered_usersInput").empty();
            $("#num_active_usersInput").empty();
            $("#num_registered_agentsInput").empty();
            $("#num_active_agentsInput").empty();

            $("#statusInput").append(server.status);
            $("#headerStatus").empty();
            $("#headerStatus").append('[ '+server.status+' ]');
            $("#sinceInput").append(server.since);
            $("#num_registered_usersInput").append(server.num_registered_users);
            $("#num_active_usersInput").append(server.num_active_users);
            $("#num_registered_agentsInput").append(server.num_registered_agents);
            $("#num_active_agentsInput").append(server.num_active_agents);

            $("#headerServer").empty();
            $("#headerServer").append('['+server.HostName+']');
            $("#headerIp").empty();
            $("#headerIp").append('['+server.IP+']');

        });
    });

    $("#btnconfig").click(function (e) {
        $.get('../config', function (config) {
           //$('#responseconfig').empty();
            //$('#responseconfig').append(JSON.stringify(config, null, "\t"));
            $("#Plataformout").empty();
            $("#descriptionout").empty();
            $("#softwareout").empty();
            $("#versionout").empty();
            $("#websiteout").empty();
            $("#admin_approve_user_createout").empty();
            $("#mail_confirm_user_createout").empty();
                $("#contactout").empty();
            $("#user_agent_nameout").empty();
            $("#default_web_page_refresh_intervalout").empty();
            $("#minimum_web_page_refresh_intervalout").empty();
            $("#minimum_web_site_access_intervalout").empty();
            $("#minimum_web_access_intervalout").empty();
            $("#execution_limit_level_1out").empty();
            $("#execution_limit_level_2out").empty();
            $("#execution_limit_level_3out").empty();
            $("#execution_limit_level_4out").empty();
            $("#execution_limit_default_levelout").empty();
            $("#max_usersout").empty();
            $("#max_instancesout").empty();
            $("#implicitly_deny_web_accessout").empty();
            $("#implicitly_deny_web_write_accessout").empty();
            $("#default_trigger_intervalout").empty();
            $("#default_reporting_intervalout").empty();
            $("#minimum_trigger_intervalout").empty();
            $("#minimum_reporting_intervalout").empty();
            $("#default_limit_instance_states_storedout").empty();
            $("#maximum_limit_instance_states_storedout").empty();
            $("#default_limit_instance_states_returnedout").empty();
            $("#maximum_limit_instance_states_returnedout").empty();
            $("#mail_access_enabledout").empty();
            $("#minimum_mail_access_intervalout").empty();
            $("#minimum_host_mail_access_intervalout").empty();
            $("#minimum_address_mail_access_intervalout").empty();

            $("#Plataformout").append(config.Plataform);
            $("#descriptionout").append(config.description);
             $("#softwareout").append(config.software);
             $("#versionout").append(config.version);
             $("#websiteout").append(config.website);
             $("#admin_approve_user_createout").append(config.admin_approve_user_create);
             $("#mail_confirm_user_createout").append(config.mail_confirm_user_create);+
             $("#contactout").append(config.contact);
             $("#user_agent_nameout").append(config.user_agent_name);
             $("#default_web_page_refresh_intervalout").append(config.default_web_page_refresh_interval);
             $("#minimum_web_page_refresh_intervalout").append(config.minimum_web_page_refresh_interval);
             $("#minimum_web_site_access_intervalout").append(config.minimum_web_site_access_interval);
             $("#minimum_web_access_intervalout").append(config.minimum_web_access_interval);
             $("#execution_limit_level_1out").append(config.execution_limit_level_1);
             $("#execution_limit_level_2out").append(config.execution_limit_level_2);
             $("#execution_limit_level_3out").append(config.execution_limit_level_3);
             $("#execution_limit_level_4out").append(config.execution_limit_level_4);
             $("#execution_limit_default_levelout").append(config.execution_limit_default_level);
             $("#max_usersout").append(config.max_users);
             $("#max_instancesout").append(config.max_instances);
             $("#implicitly_deny_web_accessout").append(config.implicitly_deny_web_access);
             $("#implicitly_deny_web_write_accessout").append(config.implicitly_deny_web_write_access);
             $("#default_trigger_intervalout").append(config.default_trigger_interval);
             $("#default_reporting_intervalout").append(config.default_reporting_interval);
             $("#minimum_trigger_intervalout").append(config.minimum_trigger_interval);
             $("#minimum_reporting_intervalout").append(config.minimum_reporting_interval);
             $("#default_limit_instance_states_storedout").append(config.default_limit_instance_states_stored);
             $("#maximum_limit_instance_states_storedout").append(config.maximum_limit_instance_states_stored);
             $("#default_limit_instance_states_returnedout").append(config.default_limit_instance_states_returned);
             $("#maximum_limit_instance_states_returnedout").append(config.maximum_limit_instance_states_returned);
             $("#mail_access_enabledout").append(config.mail_access_enabled);
             $("#minimum_mail_access_intervalout").append(config.minimum_mail_access_interval);
             $("#minimum_host_mail_access_intervalout").append(config.minimum_host_mail_access_interval);
             $("#minimum_address_mail_access_intervalout").append(config.minimum_address_mail_access_interval);

        });
    });


});

