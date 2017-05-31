package com.elephantscale.learn_metrics.streaming;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Counter;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.MyUtils;

public class MyQueue {
	private static final Logger LOG = LogManager.getLogger();
	
	// metrics
	private final Counter metricsCounterMsgsInQ = MyMetricsRegistry.metrics.counter("queue.messages_in_q");
	private final Counter metricsTotalMessages = MyMetricsRegistry.metrics.counter("queue.total_messages");

	private LinkedBlockingDeque<Payload> queue = new LinkedBlockingDeque<>();
	
	static AtomicLong totalEventsQueued = new AtomicLong();

	public void addToQ(Payload payload) {
		MyUtils.randomDelay(20);
		payload.queuedTime = System.nanoTime();
		this.queue.add(payload);
		metricsCounterMsgsInQ.inc();
		metricsTotalMessages.inc();
		
		totalEventsQueued.incrementAndGet();
	}

	public Payload deQueue() throws InterruptedException {
		MyUtils.randomDelay(30);
		Payload payload = this.queue.take();
		payload.dequeedTime = System.nanoTime();
		metricsCounterMsgsInQ.dec();
		return payload;
	}

}
