package dcc.agent.server.controllerclient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by teo on 18/05/15.
 */
@Controller
public class PlataformControllerClient {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getdata(ModelMap model) {
        return "index";

    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
    public String getrestpass(ModelMap modelMap)
    {
        return "resetpassword";
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.GET)
    public String getsignin(ModelMap modelMap)
    {
     return "sign-in";
    }

    @RequestMapping (value = "/sign-up", method = RequestMethod.GET)
    public String getsignup(ModelMap modelMap)
    {
        return "sign-up";
    }

    @RequestMapping (value = "/view", method = RequestMethod.GET)
    public String getview(ModelMap modelMap)
    {
        return "view";
    }

    @RequestMapping (value = "/tutorial", method = RequestMethod.GET)
    public String gettutorial(ModelMap modelMap)
    {
        return "tutorial";
    }

    @RequestMapping (value = "/tutorialintermediate", method = RequestMethod.GET)
    public String gettutorialintermediate(ModelMap modelMap)
    {
        return "tutorialintermediate";
    }
}
