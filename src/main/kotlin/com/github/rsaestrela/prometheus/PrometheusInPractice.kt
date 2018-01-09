package com.github.rsaestrela.prometheus

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter
import io.prometheus.client.SimpleCollector
import io.prometheus.client.exporter.MetricsServlet
import io.prometheus.client.hotspot.DefaultExports
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@SpringBootApplication
@CrossOrigin
class SocialQuestionnaireApp

fun main(args: Array<String>) {
    SpringApplication.run(SocialQuestionnaireApp::class.java, *args)
}

data class QuestionnaireRequest(var useSocialNetworkSites: Boolean,
                                var socialSitesMemberOf: ArrayList<String>,
                                var manyHoursSpendSocialSitesAWeek: Int,
                                var informationIncludedSocialSites: ArrayList<String>)

@Controller
@RequestMapping("/")
class SocialQuestionnaireController(@Autowired val service: SocialQuestionnaireService) {

    @RequestMapping(method = [(RequestMethod.POST)])
    fun indexPost(@RequestBody request: QuestionnaireRequest): ResponseEntity<String> {
        return ResponseEntity.ok("Success")
    }
}

@Service
class SocialQuestionnaireService

@Service
class PrometheusMetrics {}

@Configuration
class ObjectMapperConfiguration {
    @Bean
    @Primary
    fun objectMapper() = ObjectMapper().apply {
        registerModule(KotlinModule())
    }
}

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

    USE_SOCIAL_SITES(Counter.build().name("use_social_sites_counter")
            .labelNames(MetricInstrumentLabel.USE_SOCIAL_SITES.desc)
            .help("Use social sites counter")
            .create());

    companion object {
        fun allCollectors(): List<SimpleCollector<*>> {
            return MetricInstrument.values().map { c -> c.collector }.toList()
        }
    }

}

enum class MetricInstrumentLabel constructor(val desc: String) {
    USE_SOCIAL_SITES("use_social_sites")
}