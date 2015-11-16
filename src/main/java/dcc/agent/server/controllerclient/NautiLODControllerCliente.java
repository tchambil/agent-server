package dcc.agent.server.controllerclient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by teo on 11/10/2015.
 */
@Controller
public class NautiLODControllerCliente {
    @RequestMapping(value = "/nautilodindex.do", method = RequestMethod.GET)
    public String getnautilod(ModelMap model) {
        return "nautilodindex";

    }
    @RequestMapping(value = "/nautilodrun.do", method = RequestMethod.GET)
    public String getnautilodrun(ModelMap model) {
        return "nautilodrun";

    }
    @RequestMapping(value = "/nautilodmessage.do", method = RequestMethod.GET)
    public String getnautilodmessage(ModelMap model) {
        return "nautilodmessage";

    }
    @RequestMapping(value = "/nautilodresult.do", method = RequestMethod.GET)
    public String getnautilodresult(ModelMap model) {
        return "nautilodresult";

    }

}
