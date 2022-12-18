Compilation/execution :
rm bagoftasks/*/*.class ; javac bagoftasks/*/*.java && rmiregistry
java bagoftasks.server.Server
java bagoftasks.client.Client

Execution :
java -jar server.jar h

rmiregistry
java -jar server.jar
java -jar client.jar
