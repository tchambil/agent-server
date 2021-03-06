package dcc.agent.server.controllerclient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by teo on 18/05/15.
 */
@Controller
public class AgentControllerCliente {

    @RequestMapping(value = "/example.do",method = RequestMethod.GET)
    public String getexample(ModelMap modelMap)
    {
        return "example";
    }

    @RequestMapping(value = "/agent.do",method = RequestMethod.GET)
    public String getagent(ModelMap modelMap)
       {
           return "agent";
       }

    @RequestMapping(value = "/agentscript.do",method = RequestMethod.GET)
    public String getagentscript(ModelMap modelMap)
    {
        return "agentscript";
    }

    @RequestMapping(value = "/listagent.do",method = RequestMethod.GET)
    public String getagentlist(ModelMap modelMap)
    {
        return "listagent";

    }

    @RequestMapping(value = "/message.do", method = RequestMethod.GET)
    public String getmessage(ModelMap modelMap)
    {
        return "message";
    }

    @RequestMapping(value = "/testscript.do", method = RequestMethod.GET)
    public String swget(ModelMap modelMap)
    {
        return "testscript";
    }

}
