#!/usr/bin/env bash
# Start a server specifically for easyB tests
mvn -Dserver.port=8081 spring-boot:run &
SPRING_PID=$!

sleep 20s # Give the server enough time to start

mvn integration-test
RETURN_CODE=$?

# If integration test didn't succeed, exit with an error to signal
# the tests failed
if [ $RETURN_CODE != 0 ]; then
	kill $SPRING_PID;
	exit 1;
fi

# Shutdown the easyB server
kill $SPRING_PID
