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
    @RequestMapping(value = "/nautilod.do", method = RequestMethod.GET)
    public String getdata(ModelMap model) {
        return "nautilod";

    }
}
