# A Flexible and Scalable Platform for Multi-Agent Systems: 
---
*Plataform Agent Server*
---
 Plataform Agent Server, is a Web application agents and is made with a REST API features. It includes new features such as messaging management following the Agent Communication Language (ACL) specified by FIPA, delegation of tasks between agents, new features in the scripting language for the Semantic Web support.
 Agent Server is an extension of ["Agent-Server-Stage-0"](http://basetechnology.blogspot.com/2012_03_01_archive.html)
# Requirements
+ Apache Tomcat 7+
+ Java SE Development Kit (JDK) 7

# Download(Optional for Deploy in Tomcat) 
+ War distribution of AgentServer is [available for download](https://github.com/tchambil/agent-server/blob/master/target/agent-server-1.0.war)

# Install manual
```shell
1. Edit file /var/lib/tomcat/conf/server.xml 

<Host name="localhost" >
<value …/>
     <Context path="" docBase="/var/lib/tomcat7/webapps/agent-server-1.0"
      Reloable=”true”/>
</Host>

2. Create directory for data
   
   cd /var/lib/tomcat7
   mkdir  persistent_store 
   chmod 777 persistent_store 

3. Download file war 
   
   cd /var/lib/tomcat7/webapps
   
   sudo service tomcat7 stop(optional)

   sudo wget -O agent-server-1.0.war https://github.com/tchambil/agent-server/blob/master/target/agent-server-1.0.war?raw=true
   
   sudo service tomcat7 restart(optional)

```

# For start agent: 

http://localhost:8080/ 
```shell
Steps for run:
1)Plataform (Agent Server)
->For running Plataform with button "start"
->For pause Plataform with button "pause"
->For Restart Plafaform with button "Restart"
->For stop Plataform with button "stop"
->For get status of Plataform with button "status"
->For get configuration of Plataform with button "config"
->For get about of the Plataform with button "about"
2)Users
->For add users with button "add"
->For update data of users with button "update"
->For get list of users with button "list all"
->For get specification of a users with button "get" 
3)Agent Definition
->For add agent Definition with button "add definition" 
->For get agent Definition with button "get definition" 
4)Agent
->For add agents with button "add agent" 
->For get all agents with button "get all"
-> . . . .
```
Example(http://agentserver.herokuapp.com/)

# Basic Configurations: 

1. Create Agent Definition (for read messages)
   ->AgentDefinition Menu -> JSON

```shell
{
    "name": "definitions1",
     "timers": [
        {
            "name": "message",
            "interval": "seconds(4)",
            "script": "message w; return w.read('only');",
        }
    ]
}
2. More informations Tutorial Plataform Agent Server
 ->Tutorial Menu 
```