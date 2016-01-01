# Use the jre7 jetty 9.2 image from hub.docker.com
FROM jetty:jre7

MAINTAINER Omer Dawelbeit "omer.dawelbeit@gmail.com"

# Update aptitude with new repo
# Install other software
RUN apt-get -y update && apt-get install -y \
		default-jdk \
        maven \
        git

# Clone the graphviz-webapp github repo
RUN git clone https://github.com/omerio/graphviz-webapp /opt/graphviz-webapp

# Build the WAR file
RUN cd /opt/graphviz-webapp && mvn package

# Deploy the WAR file to /usr/local/jetty/webapps
RUN cp /opt/graphviz-webapp/target/graphviz-webapp.war /usr/local/jetty/webapps/ROOT.war
