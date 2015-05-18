package dcc.agent.server.service.siebog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by teo on 27/04/15.
 */
public class AgentInitArgs  implements Serializable
{
    public static class StringWrapper implements Serializable
    {
        private static final long serialVersionUID=1L;

        public String value;

    }

    private static final long SerialVersionUID=1L;

    private Map<String,StringWrapper> args;
    public AgentInitArgs()
    {
        args =new HashMap();
    }
public AgentInitArgs(String... KeyValues)
{
    args=new HashMap(KeyValues.length);
    for (String str: KeyValues)
    {
        String[] kv=str.split("=");
        StringWrapper arg=new StringWrapper();
        arg.value=kv[1];
        args.put(kv[0],arg);

    }
}

    public void put(String key, String value) {
        StringWrapper arg = new StringWrapper();
        arg.value = value;
        args.put(key, arg);
    }

    public String get(String key) {
        StringWrapper arg = args.get(key);
        String str = arg != null ? arg.value : null;
        if (str == null)
            throw new IllegalArgumentException("No such argument: " + key);
        return str;
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public Map<String, String> toStringMap() {
        Map<String, String> map = new HashMap(args.size());
        for (Entry<String, StringWrapper> e : args.entrySet())
            map.put(e.getKey(), e.getValue().value);
        return map;
    }

}
