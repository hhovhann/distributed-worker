#!/bin/sh
mvn clean package -Dmaven.test.skip=true && java -jar ./target/distributed-worker-1.0.0-SNAPSHOT.jar