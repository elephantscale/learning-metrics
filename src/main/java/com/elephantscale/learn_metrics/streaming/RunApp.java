package com.elephantscale.learn_metrics.streaming;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Counter;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.MyUtils;

public class RunApp {
	private static final Logger logger = LogManager.getLogger();
	static int numProducers = 10;
	static int numConsumers = 10;
	private static Producer[] producers;
	private static Consumer[] consumers;
	private final static ExecutorService producerExecutor = Executors.newFixedThreadPool(numProducers);
	private final static ExecutorService consumerExecutor = Executors.newFixedThreadPool(numConsumers);

	private final static Counter metricsCounterProducers = MyMetricsRegistry.metrics.counter("num_producers");
	private final static Counter metricsCounterConsumers = MyMetricsRegistry.metrics.counter("num_consumers");

	public static void main(String[] args) {
		MyQueue queue = new MyQueue();

		logger.info("producers :" + numProducers + ", consumers: " + numConsumers);

		// create producers
		logger.info("starting producers...");
		producers = new Producer[numProducers];
		for (int i = 0; i < numProducers; i++) {
			producers[i] = new Producer("" + i, queue);
			producerExecutor.execute(producers[i]);
			metricsCounterProducers.inc();
		}

		// create consumers
		logger.info("starting consumers...");
		consumers = new Consumer[numConsumers];
		for (int i = 0; i < numConsumers; i++) {
			consumers[i] = new Consumer("" + i, queue);
			consumerExecutor.execute(consumers[i]);
			metricsCounterConsumers.inc();
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("terminating...");
				for (Producer p : producers) {
					p.stop();
				}
				for (Consumer c : consumers) {
					c.stop();
				}
			}
		});

		logger.info("Hit Ctrl+C to terminate program");

		while (true) {
			MyUtils.sleepFor(10 * 1000);
			logger.info("== Producers: total events produced : " + Producer.totalEventsProduced);
			logger.info("== Consumers: total events consumed : " + Consumer.totalEventsConsumed);
			logger.info("== Q: total events queued : " + MyQueue.totalEventsQueued);
		}
	}

}
