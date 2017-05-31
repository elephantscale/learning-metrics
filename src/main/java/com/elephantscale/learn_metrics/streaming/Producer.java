package com.elephantscale.learn_metrics.streaming;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.MyUtils;

public class Producer implements Runnable {
	static AtomicLong totalEventsProduced = new AtomicLong();

	private static final Logger LOG = LogManager.getLogger();

	// metrics
	private final Timer metricsTimerTimeToQ = MyMetricsRegistry.metrics.timer("producer.time_to_q");
	// tracks events this producer produced
	private final Meter metricsMeterEventsProducedMe;
	// tracks all events produced by all producers
	private final Meter metricsMeterEventsProducedTotal = MyMetricsRegistry.metrics.meter("producer.events_produced");

	private Random random = new Random();

	String id;
	boolean keepRunning;
	MyQueue queue;

	public Producer(String id, MyQueue q) {
		this.id = id;
		this.queue = q;
		keepRunning = true;
		metricsMeterEventsProducedMe = MyMetricsRegistry.metrics.meter("producer." + id + ".events_produced");
		LOG.info("Producer:" + this.id + " started...");
	}

	public void stop() {
		this.keepRunning = false;
	}

	@Override
	public void run() {
		while (this.keepRunning) {
			try {
				addToQ();
				MyUtils.randomDelay(250,500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void addToQ() {
		final Timer.Context timerContext = metricsTimerTimeToQ.time();
		MyUtils.randomDelay(10);
		Payload payload = new Payload();
		payload.size = 100 + random.nextInt(500 - 100 + 1); // size between 100
															// - 500
		this.queue.addToQ(payload);
		long qTime = timerContext.stop();
		LOG.debug("Producer:" + this.id + ": queued payload:" + payload.id + " in " + qTime + " ns");
		totalEventsProduced.incrementAndGet();
		metricsMeterEventsProducedTotal.mark();
		metricsMeterEventsProducedMe.mark();
	}

}
