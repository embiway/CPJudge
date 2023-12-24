rm ~/tools/apache-tomcat-9.0.83/webapps/$1.war
mvn clean install
mv ~/Documents/Projects/CPJudge/$1/target/$1.war ~/tools/apache-tomcat-9.0.83/webapps/
 ~/tools/apache-tomcat-9.0.83/bin/startup.sh
