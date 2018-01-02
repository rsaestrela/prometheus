package com.github.rsaestrela.prometheus

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter
import io.prometheus.client.SimpleCollector
import io.prometheus.client.exporter.MetricsServlet
import io.prometheus.client.hotspot.DefaultExports
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PrometheusConfiguration {

    @Value("\${prometheus.metrics.endpoint}")
    val metricsEndpoint: String? = null

    @Bean
    fun initCollectorRegistry(): CollectorRegistry {
        val collectorRegistry = CollectorRegistry.defaultRegistry
        collectorRegistry.clear()
        DefaultExports.initialize()
        MetricInstrument.allCollectors().forEach { c ->
            run {
                c.register<SimpleCollector<*>>()
            }
        }
        return collectorRegistry
    }

    @Bean
    fun registerPrometheusExporterServlet(metricRegistry: CollectorRegistry): ServletRegistrationBean {
        return ServletRegistrationBean(MetricsServlet(metricRegistry), metricsEndpoint)
    }

}

enum class MetricInstrument constructor(val collector: SimpleCollector<*>) {

    SOCIAL_NETWORKS_IN_USE(Counter.build().name("social_network_in_use_counter")
            .labelNames(MetricInstrumentLabel.SOCIAL_NETWORK.desc)
            .help("Social network in use counter")
            .create());

    companion object {
        fun allCollectors(): List<SimpleCollector<*>> {
            return MetricInstrument.values().map { c -> c.collector }.toList()
        }
    }

}

enum class MetricInstrumentLabel constructor(val desc: String) {
    SOCIAL_NETWORK("social_network")
}