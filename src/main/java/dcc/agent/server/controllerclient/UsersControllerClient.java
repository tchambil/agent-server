package dcc.agent.server.controllerclient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by teo on 18/05/15.
 */
@Controller
public class UsersControllerClient {

    @RequestMapping(value = "users.do", method = RequestMethod.GET)
    public String getdata(ModelMap model) {
        return "/user";

    }
    @RequestMapping(value = "listuser.do", method = RequestMethod.GET)
    public String getListUser(ModelMap modelMap)
    {
        return "/listuser";
    }

}
