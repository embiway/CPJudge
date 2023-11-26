rm ~/tools/apache-tomcat-9.0.83/webapps/code-executor.war
mvn clean install
mv ~/Documents/Projects/CPJudge/code-executor/target/code-executor.war ~/tools/apache-tomcat-9.0.83/webapps/
 ~/tools/apache-tomcat-9.0.83/bin/startup.sh
