package com.elephantscale.learn_metrics.archive;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Counter;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.MyUtils;

public class Counters {

	private static final Counter metricsCounterMsgsInQ = MyMetricsRegistry.metrics.counter("messages_in_q");
	private static final Logger LOG = LogManager.getLogger();

	public static void main(String[] args) {

		// initial count
		metricsCounterMsgsInQ.inc(100L);

		Random random = new Random();
		while (true) {
			int i = random.nextInt(10);
			if (i < 5)
				addToQueue();
			else if (i < 7)
				removeFromQueue();

			// replinish, so we don't go below zero
			if (metricsCounterMsgsInQ.getCount() <= 0)
				metricsCounterMsgsInQ.inc(10L);

			MyUtils.randomDelay(300);

		}

	}

	public static void addToQueue() {
		metricsCounterMsgsInQ.inc();

	}

	public static void removeFromQueue() {
		metricsCounterMsgsInQ.dec();

	}

}
