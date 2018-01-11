package com.github.rsaestrela.election

import io.prometheus.client.Counter
import io.prometheus.client.SimpleCollector

enum class MetricInstrument constructor(val collector: SimpleCollector<*>) {

    VOTING_COUNTER(Counter.build().name("voting_counter")
            .labelNames(MetricInstrumentLabel.AGE.d, MetricInstrumentLabel.GENDER.d, MetricInstrumentLabel.COUNTRY.d,
                    MetricInstrumentLabel.VOTE.d, MetricInstrumentLabel.VOTING_FIRST_TIME.d)
            .help("Voting counter")
            .create()),

    VOTING_GAUGE(Counter.build().name("voting_gauge")
            .labelNames(MetricInstrumentLabel.AGE.d, MetricInstrumentLabel.GENDER.d, MetricInstrumentLabel.COUNTRY.d,
                    MetricInstrumentLabel.VOTE.d, MetricInstrumentLabel.VOTING_FIRST_TIME.d)
            .help("Voting gauge")
            .create());

    companion object {
        fun allCollectors(): List<SimpleCollector<*>> {
            return MetricInstrument.values().map { c -> c.collector }.toList()
        }
    }

}