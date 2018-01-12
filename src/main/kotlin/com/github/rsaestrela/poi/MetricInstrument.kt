package com.github.rsaestrela.poi

import io.prometheus.client.*

enum class MetricInstrument constructor(val collector: SimpleCollector<*>) {

    POI_LOG_COUNTER(Counter.build().name("poi_log_counter")
            .labelNames(MetricInstrumentLabel.POI_ID.d)
            .help("POI log counter")
            .create()),

    POI_NUMBER_OF_PEOPLE_COUNTER(Counter.build().name("poi_number_of_people_counter")
            .labelNames(MetricInstrumentLabel.POI_ID.d)
            .help("POI number of people counter")
            .create()),

    POI_NUMBER_OF_PEOPLE_GAUGE(Gauge.build().name("poi_number_of_people_gauge")
            .labelNames(MetricInstrumentLabel.POI_ID.d)
            .help("POI number of people gauge")
            .create()),

    POI_TEMPERATURE_GAUGE(Gauge.build().name("poi_temperature_gauge")
            .labelNames(MetricInstrumentLabel.POI_ID.d)
            .help("POI temperature gauge")
            .create()),

    POI_WIND_HISTOGRAM(Histogram.build().name("poi_wind_histogram").linearBuckets(0.0, 10.0, 10)
            .labelNames(MetricInstrumentLabel.POI_ID.d)
            .help("POI wind histogram")
            .create()),

    POI_PRECIPITATION_SUMMARY(Summary.build().name("poi_wind_summary")
            .labelNames(MetricInstrumentLabel.POI_ID.d)
            .help("POI precipitation summary")
            .create());

    companion object {
        fun allCollectors(): List<SimpleCollector<*>> {
            return MetricInstrument.values().map { c -> c.collector }.toList()
        }
    }

}
