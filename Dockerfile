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
# Build the WAR file
# Deploy the WAR file to /var/lib/jetty/
RUN git clone https://github.com/omerio/graphviz-webapp /opt/graphviz-webapp \
		&& cd /opt/graphviz-webapp \
		&& mvn package \
		&& cp /opt/graphviz-webapp/target/graphviz-webapp.war /var/lib/jetty/webapps/ROOT.war


# Enable Jetty logging
COPY logging.ini /var/lib/jetty/start.d/