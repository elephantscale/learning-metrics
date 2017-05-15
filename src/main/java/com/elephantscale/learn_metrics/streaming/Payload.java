package com.elephantscale.learn_metrics.streaming;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Timer;
import com.elephantscale.learn_metrics.MyMetricsRegistry;

public class Payload {
	private static AtomicInteger autoInc = new AtomicInteger();
	private static final Logger LOG = LogManager.getLogger();

	// metrics
	private final Timer timerTimeInQ = MyMetricsRegistry.metrics.timer("payload.time_in_q");
	private final Timer timerWaiting = MyMetricsRegistry.metrics.timer("payload.time_waiting_to_be_processed");
	private final Timer timerProcessing = MyMetricsRegistry.metrics.timer("payload.time_for_processing");
	private final Timer timerLatency = MyMetricsRegistry.metrics.timer("payload.time_total_latency");
	private final Histogram metricsHistMessageSize = MyMetricsRegistry.metrics
			.histogram("payload.message_size_bytes");
	
	
	public long queuedTime, dequeedTime, processedTime; // these times are in ns
	public long timeToProcess; // ns
	public long size; // bytes
	public long id;
	
	public Payload () {
		this.id = autoInc.incrementAndGet();
	}
	
	
	public long getTimeInQueue() {
		return  dequeedTime - queuedTime;
	}
	
	
	public long getWaitingTime() {
		return processedTime - dequeedTime;
	}
	
	public long getLatency () {
		return processedTime - queuedTime;
	}


	public void reportMetrics() {
		timerLatency.update(getLatency(), TimeUnit.NANOSECONDS);
		timerProcessing.update(timeToProcess, TimeUnit.NANOSECONDS);
		timerTimeInQ.update(getTimeInQueue(), TimeUnit.NANOSECONDS);
		timerWaiting.update(getWaitingTime(), TimeUnit.NANOSECONDS);
		metricsHistMessageSize.update(size);
		
	}

}
