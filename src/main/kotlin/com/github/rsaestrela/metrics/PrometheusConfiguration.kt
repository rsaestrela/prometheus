package com.github.rsaestrela.metrics

import io.micrometer.prometheus.PrometheusMeterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class PrometheusConfiguration {

    @Autowired
    lateinit var prometheusMeterRegistry: PrometheusMeterRegistry

    @PostConstruct
    fun init() {
        val collectorRegistry = prometheusMeterRegistry.prometheusRegistry
        collectorRegistry.clear()
        MetricInstrument.allCollectors().forEach { c ->
            collectorRegistry.register(c)
        }

    }

}