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
    @RequestMapping(value = "/definitionsimple.do", method = RequestMethod.GET)
   public String getdefinitionsimple(ModelMap modelMap)
    {
        return "definitionsimple";
    }

    @RequestMapping(value = "/definitionadvanced.do", method = RequestMethod.GET)
    public String getdefinitionadvanced(ModelMap modelMap)
    {
        return "definitionsadvanced";
    }
    @RequestMapping(value = "/definitionsjson.do", method = RequestMethod.GET)
    public String getdefinitionscript(ModelMap modelMap)
    {
        return "definitionjson";
    }




    @RequestMapping(value = "/listdefinition.do", method = RequestMethod.GET)
    public String getlistdefinitios(ModelMap modelMap)
    {
        return "listdefinition";
    }
}
