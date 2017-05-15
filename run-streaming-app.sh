#!/bin/bash
## Runs metrics demo

mvn package

./run.sh   com.elephantscale.learn_metrics.streaming.RunApp
