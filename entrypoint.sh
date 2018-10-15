#!/bin/sh
while ! nc -z config-service 8888 ; do
       echo "Waiting for upcoming Config Server"
       sleep 2
done
java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$PROFILE -jar /app.jar