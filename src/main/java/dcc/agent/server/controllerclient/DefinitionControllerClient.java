package dcc.agent.server.controllerclient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by teo on 18/05/15.
 */
@Controller
public class DefinitionControllerClient {
    @RequestMapping(value = "/definition.do", method = RequestMethod.GET)
   public String getdefinition(ModelMap modelMap)
    {
        return "definition";
    }
@RequestMapping(value = "/listdefinition.do", method = RequestMethod.GET)
    public String getlistdefinitios(ModelMap modelMap)
    {
        return "listdefinition";
    }
}
