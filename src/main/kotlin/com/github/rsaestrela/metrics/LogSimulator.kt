package com.github.rsaestrela.metrics

import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import io.prometheus.client.Histogram
import io.prometheus.client.Summary
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@Component
class LogSimulator {

    @Scheduled(fixedRate = 500, initialDelay = 500)
    fun reportPayIn() {
        val rand = kotlin.random.Random.nextLong(5)
        println("reporting payment IN triggered in $rand")
        val scheduled: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduled.schedule({
            (MetricInstrument.PAY_COUNTER.collector as Counter).labels("in").inc()
            (MetricInstrument.PAY_PROCESSED_GAUGE.collector as Gauge).labels("in").set(kotlin.random.Random.nextDouble(20.0, 30.0))
            (MetricInstrument.PAY_AMOUNT.collector as Histogram).labels("in").observe(kotlin.random.Random.nextDouble(0.0, 1000.0))
        }, rand, TimeUnit.SECONDS)
        scheduled.shutdown()
    }

    @Scheduled(fixedRate = 500, initialDelay = 1000)
    fun reportPayOut() {
        val rand = kotlin.random.Random.nextLong(5)
        println("reporting payment OUT triggered in $rand")
        val scheduled: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduled.schedule({
            (MetricInstrument.PAY_COUNTER.collector as Counter).labels("out").inc()
            (MetricInstrument.PAY_PROCESSED_GAUGE.collector as Gauge).labels("out").set(kotlin.random.Random.nextDouble(15.0, 30.0))
            (MetricInstrument.PAY_AMOUNT.collector as Histogram).labels("out").observe(kotlin.random.Random.nextDouble(0.0, 1000.0))
        }, rand, TimeUnit.SECONDS)
        scheduled.shutdown()
    }

    @Scheduled(fixedRate = 500, initialDelay = 1500)
    fun reportAccountCreateTime() {
        val rand = kotlin.random.Random.nextLong(5)
        println("reporting account created in $rand")
        val scheduled: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduled.schedule({
            (MetricInstrument.CREATE_ACCOUNT_TIME.collector as Summary).labels("website").observe(kotlin.random.Random.nextDouble(0.0, 15.0))
            MetricInstrument.CREATE_ACCOUNT_TIME.collector.labels("app").observe(kotlin.random.Random.nextDouble(0.0, 10.0))
            MetricInstrument.CREATE_ACCOUNT_TIME.collector.labels("platform").observe(kotlin.random.Random.nextDouble(0.0, 5.0))
        }, rand, TimeUnit.SECONDS)
        scheduled.shutdown()
    }

}

@Configuration
class AppContext : WebMvcConfigurationSupport() {
    @Bean
    fun taskScheduler(): TaskScheduler {
        return ConcurrentTaskScheduler()
    }
}
