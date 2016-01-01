# Use the latest jetty image from hub.docker.com
FROM jetty:latest

MAINTAINER Omer Dawelbeit "omer.dawelbeit@gmail.com"

# Update aptitude with new repo
# Install other software
RUN apt-get -y update && apt-get install -y \
		default-jdk \
        maven \
        git

# Clone the graphviz-webapp github repo
RUN git clone https://github.com/omerio/graphviz-webapp /opt/graphviz-webapp

# Set the current work directory
WORKDIR /opt/graphviz-webapp

# Build the WAR file
RUN mvn package

# Deploy the WAR file to /usr/local/jetty/webapps
COPY target/graphviz-webapp.war /usr/local/jetty/webapps/ROOT.war
