#!/bin/sh

exec java $JVM_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $APP_OPTS