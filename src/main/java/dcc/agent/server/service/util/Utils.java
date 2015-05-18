package dcc.agent.server.service.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class Utils {

    public JSONObject getJsonRequest(HttpServletRequest request) throws IOException, JSONException {
        BufferedReader reader = null;
        JSONObject requestJson = null;
        int contentLength = -1;
        contentLength = request.getContentLength();
        reader = request.getReader();

        if (contentLength <= 0) {
            requestJson = new JSONObject();
        } else {
            requestJson = new JSONObject(new JSONTokener(reader));
        }

        return requestJson;


    }
}
