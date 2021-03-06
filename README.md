﻿# A Flexible and Scalable Platform for Multi-Agent Systems: 
---
*Plataform Agent Server*
---
The  Agent Server  platform
incorporates relevant information and functions for managing agents from the behavior and
life cycle point of view. The architecture of this platform includes the Message Transport Protocol Module for reliable processing of messages, which uses the  Agent Communication
Language  (ACL) specified by FIPA and managed by the methods GET, POST and PUT of the HTTP communication protocol. Likewise Agent Manager incorporates
the module responsible for the management of the platform and agents. All agents in the
 Agent Server  platform have the same actions implemented, thus allowing to develop their
capabilities in a uniform manner.
 Agent Server is an extension of ["Agent-Server-Stage-0"](http://basetechnology.blogspot.com/2012_03_01_archive.html)
 
 # Architecture
 
<p align="center">
  <img src="https://github.com/tchambil/agent-server/blob/master/architecture.png" width="350"/> 
</p>

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

+ Imports agents from other servers

+ Suscribe to groups

+ More informations Tutorial Plataform Agent Server
 ->Tutorial Menu 


# Case Studio NautiLOD Distributed:
+ To acces http://agentserver.herokuapp.com/nautilodindex.do
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

# Configure message for Delegate task:
the sintaxis for delegate is:
```shell
Delegate(A, B, Task), where
A=Agent delegator,
B=Agent Contractor,
Task= Set and sequence actions (eg, putTo, exec, join, message, result)
Example:
Delegate(A,B, putTo(C, exec(Q, const))), where
Q=Query sql,
const= constrains for execution actions
```
In content have actions ::putTo and ::exec

```shell
{ 
"conversationId": "geonames-123581844", 
"sender": "agent1@dbpedias.cloudapp.net", 
"receiver": "agent2@geonames.cloudapp.net", 
"replyTo": "agent3@yagos.cloudapp.net", 
"content": "::putTo(agent3@yagos.cloudapp.net, ::exec(http://sws.geonames.org/3165322/ -p 
			<http://www.w3.org/2000/01/rdf-schema#isDefinedBy> 
			[ASK {?ctx <http://www.geonames.org/ontology#population> 
			?pop. FILTER (?pop >10000).}] -f files.rdf))", 
"language": "", 
"encoding": "123525235", 
"ontology": "2", 
"protocol": "REQUEST", 
"replyWith": "",
"inReplyTo": "",
"replyBy": "", 
"performative": "REQUEST" 
}
```


For more information contact tchambil@dcc.uchile.cl

