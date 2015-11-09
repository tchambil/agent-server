# Introduction to agent at web servers: 
---
*AgentServer*
---
The proyect is contribute and improve ["Agent-Server-Stage-0"](http://basetechnology.blogspot.com/2012_03_01_archive.html), with framework "spring boot" (http://projects.spring.io/spring-boot/) and run directly from RESTFUL. 

Our work is implement the comunication between web servers with message queue of high concurrency. For the  delegation of tasks between web servers.

Actually we are working in the implement of RabbitMQ broker.

For install:
execute from terminal: java -jar agent-rest-1.0.jar

For start agent:

http://localhost:8080/index.html 
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
# Requirements
+ - RabbitMQ server (installation instructions below). RabbitMQ is an AMQP server. The server is freely available at http://www.rabbitmq.com/download.html. You can download it manually.

# For Install RabbitMQ in Ubuntu
```shell
sudo apt-get remove rabbitmq-server
sudo apt-get install python-software-properties
sudo add-apt-repository "deb http://www.rabbitmq.com/debian/ testing main"
wget http://www.rabbitmq.com/rabbitmq-signing-key-public.asc
sudo apt-key add rabbitmq-signing-key-public.asc
sudo apt-get update
sudo apt-get install rabbitmq-server -y
sudo service rabbitmq-server start
sudo rabbitmq-plugins enable rabbitmq_management
sudo service rabbitmq-server restart
```
# For configure RabbitMQ in Ubuntu
```shell
sudo rabbitmqctl add_user agent 159753
sudo rabbitmqctl set_user_tags agent administrator
sudo rabbitmqctl set_permissions -p / agent ".*" ".*" ".*"
sudo rabbitmqctl delete_user guest

sudo service rabbitmq-server restart

test connection with following code

curl -i -u heartbeat:alive http://127.0.0.1:15672/

HTTP/1.1 200 OK
Server: MochiWeb/1.0 (Any of you quaids got a smint?)
last-modified: Sat, 16 May 2015 05:32:19 GMT
Date: Sat, 16 May 2015 05:45:42 GMT
Content-Type: text/html
Content-Length: 1353


```