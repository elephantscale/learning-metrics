# Learning Metrics

This repo is obsolete.

For latest please see [elephantscale/metrics-in-docker](https://github.com/elephantscale/metrics-in-docker)

---


Examples to learn Metrics library.

We have 2 sections
- [MetricsDemo]() - a simple demo program that showcases metrics
- [Streaming App]() - This simulates a simple pub-sub queue and collects metrics

## Build
```bash
    # to build
    $   mvn clean package
```

### To import to Eclipse
Import -> Existing Maven Projects --> Select POM.xml

Should import cleanly.

## Metrics Demo App
This is a simple app that showcases how to use various metrics.  

**Class:** com.elephantscale.learn_metrics.MetricsDemo

to run the demo app in command line:
```bash
    $   ./run-metrics-demo.sh

    # or
    $   mvn exec:java -Dexec.mainClass="com.elephantscale.learn_metrics.MetricsDemo"
```

#### Sample Screenshot


## Streaming App
Simulates a pub-sub message system with multiple publishers and multiple consumers.  Collects and pushes metrics.

Package : `com.elephantscale.learn_metrics.streaming`

To run the demo app in command line:
```bash
    $   ./run-streaming-app.sh

    # or
    $   mvn exec:java -Dexec.mainClass="com.elephantscale.learn_metrics.streaming.RunApp"
```
