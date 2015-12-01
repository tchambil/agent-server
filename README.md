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

+ Edit file /var/lib/tomcat/conf/server.xml 

```shell
<Host name="localhost" >
<value …/>
     <Context path="" docBase="/var/lib/tomcat7/webapps/agent-server-1.0"
      Reloable=”true”/>
</Host>
```

+ Create directory for data

 ```shell  
   cd /var/lib/tomcat7
   mkdir  persistent_store 
   chmod 777 persistent_store 
```

+ Download file war 
 ```shell    
   cd /var/lib/tomcat7/webapps
   
   sudo service tomcat7 stop(optional)

   sudo wget -O agent-server-1.0.war https://github.com/tchambil/agent-server/blob/master/target/agent-server-1.0.war?raw=true
   
   sudo service tomcat7 restart(optional)

```
---

# For start agent


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

# Basic Configurations: (STEPS)

+ Create user with id "test-user-1" This is mandatory

+ Create Agent Definition (for read messages)
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
```
+ Create agents 

+ Create groups

+ Imports agents from other serves

+ Suscribe to groups

+ More informations Tutorial Plataform Agent Server
 ->Tutorial Menu 


# Case Studio NautiLOD Distributed:
+ To acces http://agentserver.herokuapp.com/nautilod.do
  -> Run Script Menu ->  into Expression NautiLOD

```shell
	Seed URI ->  http://dbpedia.org/resource/Italy
	
	NautiLOD expression->

<http://dbpedia.org/ontology/hometown>[ASK {?ctx <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> 
<http://dbpedia.org/ontology/Person>. ?ctx <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> 
<http://dbpedia.org/ontology/MusicalArtist>.}]/<http://dbpedia.org/ontology/birthPlace>/
<http://www.w3.org/2002/07/owl#sameAs>/<http://www.w3.org/2000/01/rdf-schema#isDefinedBy>
[ASK {?ctx <http://www.geonames.org/ontology#population> ?pop. FILTER (?pop <15000).}] -f files.rdf

```

+ For get Result -> Result Menu with Task ID 

For more information contact tchambil@dcc.uchile.cl

