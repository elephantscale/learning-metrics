package com.elephantscale.learn_metrics;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

public class MyMetricsRegistry {

	public final static String APP_ID = "my-metrics";

	public final static String GRAPHITE_HOST = "localhost";
	public final static int GRAPHITE_PORT = 2003;
	public final static int REPORTING_INTERVAL = 10; // 10 secs

	static final public MetricRegistry metrics;

	static {
		metrics = new MetricRegistry();
		registerConsoleReporter();
		registerGraphiteReporter();
		registerJMXReporter();
	}

	static void registerConsoleReporter() {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(30, TimeUnit.SECONDS);
	}

	static void registerGraphiteReporter() {
		final Graphite graphite = new Graphite(new InetSocketAddress(GRAPHITE_HOST, GRAPHITE_PORT));
		final GraphiteReporter reporter = GraphiteReporter.forRegistry(metrics).prefixedWith(APP_ID)
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL)
				.build(graphite);
		reporter.start(REPORTING_INTERVAL, TimeUnit.SECONDS);
	}

	static final void registerJMXReporter() {
		final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
		reporter.start();
	}
}
