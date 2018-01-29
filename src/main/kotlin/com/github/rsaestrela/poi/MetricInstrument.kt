package com.github.rsaestrela.poi

import io.prometheus.client.*

enum class MetricInstrument constructor(val collector: SimpleCollector<*>) {

    LOG_COUNTER(Counter.build().name("log_counter")
            .labelNames(MetricInstrumentLabel.ID.d)
            .help("log counter")
            .create()),

    NUMBER_OF_PEOPLE_COUNTER(Counter.build().name("number_of_people_counter")
            .labelNames(MetricInstrumentLabel.ID.d)
            .help("number of people counter")
            .create()),

    NUMBER_OF_PEOPLE_GAUGE(Gauge.build().name("number_of_people_gauge")
            .labelNames(MetricInstrumentLabel.ID.d)
            .help("number of people gauge")
            .create()),

    TEMPERATURE_GAUGE(Gauge.build().name("temperature_gauge")
            .labelNames(MetricInstrumentLabel.ID.d)
            .help("temperature gauge")
            .create()),

    WIND_HISTOGRAM(Histogram.build().name("wind_histogram").linearBuckets(0.0, 10.0, 10)
            .labelNames(MetricInstrumentLabel.ID.d)
            .help("wind histogram")
            .create()),

    PRECIPITATION_SUMMARY(Summary.build().name("precipitation_summary").quantile(0.91, 0.0).quantile(0.95, 0.0).quantile(0.99, 0.0)
            .labelNames(MetricInstrumentLabel.ID.d)
            .help("precipitation summary")
            .create());

    companion object {
        fun allCollectors(): List<SimpleCollector<*>> {
            return MetricInstrument.values().map { c -> c.collector }.toList()
        }
    }

}
