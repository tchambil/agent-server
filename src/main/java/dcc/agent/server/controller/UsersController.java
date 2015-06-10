package dcc.agent.server.controller;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.appserver.AgentAppServerBadRequestException;
import dcc.agent.server.service.util.NameValue;
import dcc.agent.server.service.util.Utils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UsersController {
    protected static Logger logger = Logger.getLogger(UsersController.class);

    public AgentServer agentServer;
    public Utils util = new Utils();

    @RequestMapping(value = "/users", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
      public String postUser(HttpServletRequest request) throws Exception {
        // User can specify parameters in JSON or as query parameters
        // Query overrides JSON if query parameter is non-null

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        JSONObject userJson = util.getJsonRequest(request);
        String id = request.getParameter("id");
        if (id == null)
            id = userJson.optString("id");
        else
            userJson.put("id", id);
        String password = request.getParameter("password");
        if (password == null) {
            password = userJson.optString("password");
        }
        else {
            userJson.put("password", password);
        }

        String passwordHint = request.getParameter("password_hint");
        if (passwordHint == null)
            passwordHint = userJson.optString("password_hint");
        else
            userJson.put("password_hint", passwordHint);
        String fullName = request.getParameter("full_name");
        if (fullName == null)
            fullName = userJson.optString("full_name");
        else
            userJson.put("full_name", fullName);
        String displayName = request.getParameter("display_name");
        if (displayName == null)
            displayName = userJson.optString("display_name");
        else
            userJson.put("display_name", displayName);
        String nickName = request.getParameter("nick_name");
        if (nickName == null)
            nickName = userJson.optString("nick_name");
        else
            userJson.put("nick_name", nickName);
        String organization = request.getParameter("organization");
        if (organization == null)
            organization = userJson.optString("organization");
        else
            userJson.put("organization", organization);
        String bio = request.getParameter("bio");
        if (bio == null)
            bio = userJson.optString("bio");
        else
            userJson.put("bio", bio);
        String interests = request.getParameter("interests");
        if (interests == null)
            interests = userJson.optString("interests");
        else
            userJson.put("interests", interests);
        String incognitoString = request.getParameter("incognito");
        boolean incognito = incognitoString != null
                && (incognitoString.equalsIgnoreCase("true")
                || incognitoString.equalsIgnoreCase("yes") || incognitoString
                .equalsIgnoreCase("on"));
        if (incognitoString == null)
            incognito = userJson.optBoolean("incognito");
        else
            userJson.put("incognito", incognito ? "true" : false);
        String email = request.getParameter("email");
        if (email == null)
            email = userJson.optString("email");
        else
            userJson.put("email", email);
        String comment = request.getParameter("comment");
        if (comment == null)
            comment = userJson.optString("comment");
        else
            userJson.put("comment", comment);

        if (id == null) {
            throw new AgentAppServerBadRequestException(
                    "Missing id query parameter");
        } else if (id.trim().length() == 0) {
            throw new AgentAppServerBadRequestException(
                    "Empty id query parameter");
        } else if (id.trim().length() < User.MIN_ID_LENGTH) {
            throw new AgentAppServerBadRequestException(
                    "Id must be at least 4 characters");
        } else if (password == null) {
            throw new AgentAppServerBadRequestException(
                    "Missing password query parameter");
        } else if (password.trim().length() == 0) {
            throw new AgentAppServerBadRequestException(
                    "Empty password query parameter");
        } else if (password.trim().length() < User.MIN_ID_LENGTH) {
            throw new AgentAppServerBadRequestException(
                    "Password must be at least 4 characters");
        } else if (agentServer.users.containsKey(id.trim())) {
            throw new AgentAppServerBadRequestException(
                    "User with that id already exists");
        } else {
            id = id.trim();
            password = password.trim();
            logger.info("Adding new user: " + id);
            Boolean approved = !agentServer.config
                    .getBoolean("admin_approve_user_create");
            User newUser = new User(id, password, passwordHint, fullName,
                    displayName, nickName, organization, bio, interests,
                    email, incognito, comment, approved, true, true, null,
                    null);
            newUser.generateSha();
            agentServer.addUser(newUser);
            // TODO: Set Location header with URL
            JSONObject message = new JSONObject();
            message.put("message", "Save successful");
            return message.toString();
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User getUser(@PathVariable String id) throws Exception {
        logger.info("Getting detailed info for a specified user Id:" + id);
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User users = agentServer.users.get(id);
        return users;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Content-Type=application/json")
    @ResponseStatus(HttpStatus.OK)
    public User putUser(@PathVariable String id, HttpServletRequest request) throws Exception {
        // Parse the user info JSON from posted entity
        JSONObject userJson = util.getJsonRequest(request);
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        logger.info("Updating existing user: " + user.id);
        // Parse the updated user info
        User newUser = User.fromJson(userJson, true);
        // Update the user info
        user.update(agentServer, newUser);
        // Update was successful
        return user;
    }

    @RequestMapping(value = "/users/{id}/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User putUserDisable(@PathVariable String id, HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String allActivityString = request.getParameter("all_activity");
        boolean disableAllActivity = allActivityString == null
                || (allActivityString.equalsIgnoreCase("true")
                || allActivityString.equalsIgnoreCase("yes") || allActivityString
                .equalsIgnoreCase("on"));
        String newActivityString = request.getParameter("new_activity");
        boolean disableNewActivity = newActivityString == null
                || (newActivityString.equalsIgnoreCase("true")
                || newActivityString.equalsIgnoreCase("yes") || newActivityString
                .equalsIgnoreCase("on"));
        logger.info("Disabling user: " + user.id + " diable all activity: "
                + disableAllActivity + " disable new activity: "
                + disableNewActivity);

        // Disable user as directed
        user.enabled = !disableAllActivity;
        user.newActivityEnabled = !disableNewActivity;

        // Update was successful
        return user;

    }

    @RequestMapping(value = "/users/{id}/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User putUserenable(@PathVariable String id, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String allActivityString = request.getParameter("all_activity");
        boolean enableAllActivity = allActivityString == null
                || (allActivityString.equalsIgnoreCase("true")
                || allActivityString.equalsIgnoreCase("yes") || allActivityString
                .equalsIgnoreCase("on"));
        String newActivityString = request.getParameter("new_activity");
        boolean enableNewActivity = newActivityString == null
                || (newActivityString.equalsIgnoreCase("true")
                || newActivityString.equalsIgnoreCase("yes") || newActivityString
                .equalsIgnoreCase("on"));
        logger.info("Enabling user: " + user.id + " enable new activity: "
                + enableNewActivity);

        // Enable user as directed
        user.enabled = enableAllActivity;
        user.newActivityEnabled = enableNewActivity;

        // Update was successful
        return user;

    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable String id) throws Exception
    {
        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.users.get(id);

        agentServer.users.remove(user.id);
        logger.info("Deleted user: " + user.id);
        JSONObject message = new JSONObject();
        message.put("message", "Deleted Successful user: "+id);
        return message.toString();

    }
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getUsers() throws Exception {
        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        JSONArray usersArrayJson = new JSONArray();
        for (NameValue<User> userIdValue : agentServer.users) {
            User user = userIdValue.value;
            JSONObject userJson = new JSONObject();
            userJson.put("id", user.id);
            userJson.put("display_name", user.incognito ? "(Incognito)"
                    : (user.displayName == null ? "" : user.displayName));
            userJson.put("full_name", user.fullName);
            userJson.put("nick_name", user.nickName);
            userJson.put("email", user.email);


            usersArrayJson.put(userJson);
        }
        JSONObject usersJson = new JSONObject();
        usersJson.put("users", usersArrayJson);

        return  usersJson.toString();

    }
}
