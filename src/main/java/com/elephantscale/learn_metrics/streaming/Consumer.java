package com.elephantscale.learn_metrics.streaming;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.Utils;

public class Consumer implements Runnable {
	private static final Logger LOG = LogManager.getLogger();

	// metrics
	private final Timer metricsTimerTimeToDeQ = MyMetricsRegistry.metrics.timer("consumer.time_to_dq");
	private final Timer metricsTimerTimeToProcess = MyMetricsRegistry.metrics.timer("consumer.time_to_process");
	private final Meter metricsMeterEventsConsumedAll = MyMetricsRegistry.metrics.meter("consumer.events_consumed");
	private final Meter metricsMeterEventsConsumedMe ;

	static final AtomicLong totalEventsConsumed = new AtomicLong();

	String id;
	boolean keepRunning;
	MyQueue queue;

	public Consumer(String id, MyQueue q) {
		this.id = id;
		this.queue = q;
		keepRunning = true;
	    metricsMeterEventsConsumedMe = MyMetricsRegistry.metrics.meter("consumer." + id  + ".events_consumed");
		LOG.info("Consumer:" + this.id + " started...");
	}

	public void stop() {
		this.keepRunning = false;
	}

	@Override
	public void run() {
		while (this.keepRunning) {
			try {
				process();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void process() throws Exception {
		try {
			final Timer.Context timerContext = metricsTimerTimeToDeQ.time();
			Payload payload = this.queue.deQueue();
			long dequeTime = timerContext.stop();
			LOG.debug("Consumer:" + this.id + ": payload:" + payload.id + " time to deque: " + dequeTime);

			// process
			final Timer.Context timerContext2 = metricsTimerTimeToProcess.time();
			Utils.randomDelay(300,800); // simulate processing
			payload.processedTime = System.nanoTime(); // done processing
			payload.timeToProcess = timerContext2.stop();
			LOG.debug("Consumer:" + this.id + " : payload:" + payload.id + " time to process: " + payload.timeToProcess
					+ " ns");

			totalEventsConsumed.incrementAndGet();
			metricsMeterEventsConsumedAll.mark();
			metricsMeterEventsConsumedMe.mark();
			payload.reportMetrics();
		} catch (InterruptedException e) {
		}

	}

}
