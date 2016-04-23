#!/usr/bin/env bash
# Start a server specifically for easyB tests
mvn -Dserver.port=8081 spring-boot:run > /dev/null 2>&1 &
SPRING_PID=$!

sleep 20s # Give the server enough time to start

#mvn integration-test
mvn integration-test | grep -v INFO | grep -v "c.g.htmlunit.DefaultCssErrorHandler" | grep -v "DEBUG org.apache.http.wire"
RETURN_CODE=$?

# If integration test didn't succeed, exit with an error to signal
# the tests failed
if [ $RETURN_CODE != 0 ]; then
	kill $SPRING_PID;
	exit 1;
fi

# Shutdown the easyB server
kill $SPRING_PID
