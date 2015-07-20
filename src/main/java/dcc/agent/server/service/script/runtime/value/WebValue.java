/**
 * Copyright 2012 John W. Krupansky d/b/a Base Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.script.intermediate.StringTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;
import dcc.agent.server.service.script.intermediate.WebTypeNode;
import dcc.agent.server.service.script.runtime.ScriptState;
import dcc.agent.server.service.swget.multithread.Navigator;
import dcc.agent.server.service.swget.regExpression.parser.ParseException;
import dcc.agent.server.service.webaccessmanager.WebAccessException;
import dcc.agent.server.service.webaccessmanager.WebPage;

import java.util.ArrayList;
import java.util.List;

public class WebValue extends Value {
    public WebPage webPage;
    public boolean wait = true;
    public Navigator navigator;
    public WebValue(){
    }

    public TypeNode getType(){
        return WebTypeNode.one;
    }

    public Object getValue(){
        // TODO: Return response
        return NullValue.one;
    }

    public Value getNamedValue(ScriptState scriptState, String name) throws RuntimeException {
        if (name.equals("length") || name.equals("size"))
            // TODO
            return new IntegerValue(0);
        else if (name.equals("clear")){
            // TODO
            return NullValue.one;
        } else if (name.equals("keys")){
            // TODO

            // Generate and return the new value node for list of the key strings for map
            return new ListValue(StringTypeNode.one, new ArrayList<Value>());
        } else if (name.equals("reasonPhrase") || name.equals("reason")){
            if (webPage == null)
                return NullValue.one;
            else
                return new StringValue(webPage.reasonPhrase);
        } else if (name.equals("statusCode") || name.equals("status")){
            if (webPage == null)
                return NullValue.one;
            else
                return new StringValue(Integer.toString(webPage.statusCode));
        } else
            return super.getNamedValue(scriptState, name);
    }

    public Value getMethodValue(ScriptState scriptState, String name, List<Value> arguments) throws RuntimeException {
        int numArguments = arguments.size();
        if ((name.equals("length") || name.equals("size")) && numArguments == 0)
            // TODO
            return new IntegerValue(0);
        else if ((name.equals("add") || name.equals("put") || name.equals("set")) && numArguments == 2){
            // TODO
            // TODO: Find out what this Java return value is really all about
            return TrueValue.one;
        } else if (name.equals("clear") && numArguments == 0){
            // TODO

            // No return value
            return NullValue.one;
            // TODO: Add "contains"
        } else if (name.equals("nautilod") && (numArguments >= 1 || numArguments <= 4)){
            // Get the URL of the web page to fetch
            String command = arguments.get(0).getStringValue();
            String comment = null;
            // Get the optional useCache flag
            boolean useCache = true;
            if (numArguments >= 2)
               comment = arguments.get(1).getStringValue();

            // Get the optional page refresh interval
            long refreshInterval = -1;
            if (numArguments >= 3)
                refreshInterval = arguments.get(2).getLongValue();

            // Get the optional wait flag
            boolean wait = this.wait;
            if (numArguments >= 4)
                wait = arguments.get(3).getBooleanValue();

            // Fetch the specified web page
            String s = fetchNatilod(scriptState, command, comment);
            if (s == null || s.length() == 0)
                return NullValue.one;
            else
                return new StringValue(s);

        } else if (name.equals("get") && (numArguments >= 1 || numArguments <= 4)){
            // Get the URL of the web page to fetch
            String url = arguments.get(0).getStringValue();

            // Get the optional useCache flag
            boolean useCache = true;
            if (numArguments >= 2)
                useCache = arguments.get(1).getBooleanValue();

            // Get the optional page refresh interval
            long refreshInterval = -1;
            if (numArguments >= 3)
                refreshInterval = arguments.get(2).getLongValue();

            // Get the optional wait flag
            boolean wait = this.wait;
            if (numArguments >= 4)
                wait = arguments.get(3).getBooleanValue();

            // Fetch the specified web page
            String s = fetchWebPage(scriptState, url, useCache, refreshInterval, wait);
            if (s == null || s.length() == 0)
                return NullValue.one;
            else
                return new StringValue(s);
        } else if (name.equals("post") && (numArguments >= 1 || numArguments <= 4)){
            // Get the URL of the web page to fetch
            String url = arguments.get(0).getStringValue();

            // Get the optional data to post
            String data = null;
            if (numArguments >= 2)
                data = arguments.get(1).getStringValue();

            // Get the optional page refresh interval
            long refreshInterval = -1;
            if (numArguments >= 3)
                refreshInterval = arguments.get(2).getLongValue();

            // Get the optional wait flag
            boolean wait = this.wait;
            if (numArguments == 4)
                wait = arguments.get(3).getBooleanValue();

            // Post to the specified URL
            String s = postUrl(scriptState, url, data, refreshInterval, wait);
            if (s == null || s.length() == 0)
                return NullValue.one;
            else
                return new StringValue(s);
        } else if (name.equals("isAccessible") && numArguments == 1){
            // Get the URL of the web page to fetch
            String url = arguments.get(0).getStringValue();

            // Determine if the specified web page is accessible
            boolean urlIsAccessible = isAccessible(scriptState, url);
            return BooleanValue.create(urlIsAccessible);
        } else if (name.equals("keys") && numArguments == 0){
            // TODO
            // Build list of key string values
            List<Value> keysList = new ArrayList<Value>();

            // Generate and return the new value node for list of the key strings for map
            return new ListValue(StringTypeNode.one, keysList);
        } else if (name.equals("remove") && numArguments == 1){
            // TODO
            // Get the key of element to remove
            String key = arguments.get(0).getStringValue();

            // Remove the value
            // TODO
            Value removedValue = NullValue.one;

            // Return the removed value
            return removedValue;

        } else
            return super.getMethodValue(scriptState, name, arguments);
    }

    private String fetchNatilod(ScriptState scriptState, String command, String comment)  {
        this.navigator = new Navigator();
        try {
           String[] s= navigator.runCommand(scriptState,  command,comment);

            return ("NautilodInit");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Value getSubscriptedValue(ScriptState scriptState, List<Value> subscriptValues) throws RuntimeException {
        int numSubscripts = subscriptValues.size();
        if (numSubscripts == 1){
            // Fetch element with that key
            String key = subscriptValues.get(0).getStringValue();

            // TODO
            return NullValue.one;
        } else
            throw new RuntimeException("Web does not support " + numSubscripts + " subscripts");
    }

    public Value putSubscriptedValue(ScriptState scriptState, List<Value> subscriptValues, Value newValue) throws RuntimeException {
        int numSubscripts = subscriptValues.size();
        if (numSubscripts == 1){
            // TODO
            // Modify element with that key
            String key = subscriptValues.get(0).getStringValue();

            // TODO

            return newValue;
        } else
            throw new RuntimeException("Web does not support " + numSubscripts + " subscripts for assignment");
    }

    public String fetchWebPage(ScriptState scriptState, String url) throws RuntimeException {
        return fetchWebPage(scriptState, url, true, -1, true);
    }

    public String fetchWebPage(ScriptState scriptState, String url, boolean useCache, long refreshInterval, boolean wait) throws RuntimeException {
        try {
            webPage = scriptState.agentServer.getWebPage(scriptState.getUserId(), url, useCache, refreshInterval, wait);
            if (webPage == null)
                return null;
            else
                return webPage.text;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Web GET exception: " + e);
        }
    }

    public String postUrl(ScriptState scriptState, String url, String data) throws RuntimeException {
        return postUrl(scriptState, url, data, -1, true);
    }

    public String postUrl(ScriptState scriptState, String url, String data, long refreshInterval, boolean wait) throws RuntimeException {
        try {
            webPage = scriptState.agentServer.postUrl(scriptState.getUserId(), url, data, refreshInterval, wait);
            if (webPage == null)
                return null;
            else
                return webPage.text;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Web GET exception: " + e);
        }
    }

    public boolean isAccessible(ScriptState scriptState, String url) throws RuntimeException {
        try {
            webPage = scriptState.agentServer.getWebPage(scriptState.getUserId(), url);
            return webPage != null;
        } catch (WebAccessException e) {
            return false;
        }
    }

    public String toJson(){
        return "\"<web>\"";
    }

    public String toString(){
        return "web";
    }

    public String getTypeString(){
        return "web";
    }

}
/*
public class WebValue extends Value {

    public boolean wait = true;

    public WebValue() {
    }

    public TypeNode getType() {
        return WebTypeNode.one;
    }

    public Object getValue() {
        // TODO: Return response
        return NullValue.one;
    }

    public Value getNamedValue(ScriptState scriptState, String name) throws RuntimeException {
        if (name.equals("length") || name.equals("size"))
            // TODO
            return new IntegerValue(0);
        else if (name.equals("clear")) {
            // TODO
            return NullValue.one;
        } else if (name.equals("keys")) {
            // TODO

            // Generate and return the new value node for list of the key strings for map
            return new ListValue(StringTypeNode.one, new ArrayList<Value>());
        } else
            return super.getNamedValue(scriptState, name);
    }

    public Value getMethodValue(ScriptState scriptState, String name, List<Value> arguments) throws RuntimeException {
        int numArguments = arguments.size();
        if ((name.equals("length") || name.equals("size")) && numArguments == 0)
        // TODO
        {
            return new IntegerValue(0);
        } else if ((name.equals("add") || name.equals("put") || name.equals("set")) && numArguments == 2) {
            // TODO
            // TODO: Find out what this Java return value is really all about
            return TrueValue.one;
        } else if (name.equals("clear") && numArguments == 0) {
            // TODO

            // No return value
            return NullValue.one;
            // TODO: Add "contains"
        } else if (name.equals("get") && (numArguments >= 1 || numArguments <= 4)) {
            // Get the URL of the web page to fetch
            String url = arguments.get(0).getStringValue();

            // Get the optional useCache flag
            boolean useCache = true;
            if (numArguments >= 2)
                useCache = arguments.get(1).getBooleanValue();

            // Get the optional page refresh interval
            long refreshInterval = -1;
            if (numArguments >= 3)
                refreshInterval = arguments.get(2).getLongValue();

            // Get the optional wait flag
            boolean wait = this.wait;
            if (numArguments >= 4)
                wait = arguments.get(3).getBooleanValue();

            return NullValue.one;
        } else if (name.equals("post") && (numArguments >= 1 || numArguments <= 4)) {
            // Get the URL of the web page to fetch
            String url = arguments.get(0).getStringValue();

            // Get the optional data to post
            String data = null;
            if (numArguments >= 2)
                data = arguments.get(1).getStringValue();

            // Get the optional page refresh interval
            long refreshInterval = -1;
            if (numArguments >= 3)
                refreshInterval = arguments.get(2).getLongValue();

            // Get the optional wait flag
            boolean wait = this.wait;
            if (numArguments == 4)
                wait = arguments.get(3).getBooleanValue();

            // Post to the specified URL
            return NullValue.one;
        } else if (name.equals("isAccessible") && numArguments == 1) {
            // Get the URL of the web page to fetch
            String url = arguments.get(0).getStringValue();

            // Determine if the specified web page is accessible
            return NullValue.one;
        } else if (name.equals("keys") && numArguments == 0) {
            // TODO
            // Build list of key string values
            List<Value> keysList = new ArrayList<Value>();

            // Generate and return the new value node for list of the key strings for map
            return new ListValue(StringTypeNode.one, keysList);
        } else if (name.equals("remove") && numArguments == 1) {
            // TODO
            // Get the key of element to remove
            String key = arguments.get(0).getStringValue();

            // Remove the value
            // TODO
            Value removedValue = NullValue.one;

            // Return the removed value
            return removedValue;

        } else {
            return super.getMethodValue(scriptState, name, arguments);

        }
    }

    public Value getSubscriptedValue(ScriptState scriptState, List<Value> subscriptValues) throws RuntimeException {
        int numSubscripts = subscriptValues.size();
        if (numSubscripts == 1) {
            // Fetch element with that key
            String key = subscriptValues.get(0).getStringValue();

            // TODO
            return NullValue.one;
        } else
            throw new RuntimeException("Web does not support " + numSubscripts + " subscripts");
    }

    public Value putSubscriptedValue(ScriptState scriptState, List<Value> subscriptValues, Value newValue) throws RuntimeException {
        int numSubscripts = subscriptValues.size();
        if (numSubscripts == 1) {
            // TODO
            // Modify element with that key
            String key = subscriptValues.get(0).getStringValue();

            // TODO

            return newValue;
        } else
            throw new RuntimeException("Web does not support " + numSubscripts + " subscripts for assignment");
    }


    public String toJson() {
        return "\"<web>\"";
    }

    public String toString() {
        return "web";
    }

    public String getTypeString() {
        return "web";
    }

}
*/

