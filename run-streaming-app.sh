#!/bin/bash
## Runs metrics demo

echo "==== compiling ... === "
mvn package

echo "==== running ... === "
./run.sh   com.elephantscale.learn_metrics.streaming.RunApp
