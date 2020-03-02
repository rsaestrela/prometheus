package com.github.rsaestrela.metrics

import io.prometheus.client.*

enum class MetricInstrument constructor(val collector: SimpleCollector<*>) {

    PAY_COUNTER(Counter.build()
            .name("pay_counter")
            .labelNames("type")
            .help("pay_counter")
            .create()),

    PAY_PROCESSED_GAUGE(Gauge.build()
            .name("pay_processed_gauge")
            .labelNames("type")
            .help("pay_processed_gauge")
            .create()),

    PAY_AMOUNT(Histogram.build()
            .name("pay_amount")
            .linearBuckets(0.0, 200.0, 5)
            .labelNames("type")
            .help("pay_amount")
            .create()),

    CREATE_ACCOUNT_TIME(Summary.build()
            .name("create_account_time")
            .quantile(0.91, 0.0)
            .quantile(0.95, 0.0)
            .quantile(0.99, 0.0)
            .labelNames("source")
            .help("create_account_time")
            .create());

    companion object {
        fun allCollectors(): List<SimpleCollector<*>> {
            return values().map { c -> c.collector }.toList()
        }
    }

}
