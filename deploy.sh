rm ~/tools/apache-tomcat-9.0.83/webapps/cp-judge.war
mvn clean install
mv ~/Documents/Projects/CPJudge/cp-judge/target/cp-judge.war ~/tools/apache-tomcat-9.0.83/webapps/
 ~/tools/apache-tomcat-9.0.83/bin/startup.sh
