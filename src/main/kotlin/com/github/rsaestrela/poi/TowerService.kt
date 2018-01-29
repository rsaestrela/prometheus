package com.github.rsaestrela.poi

import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import io.prometheus.client.Histogram
import io.prometheus.client.Summary
import org.springframework.stereotype.Service

@Service
class TowerService {

    private val logCounter = MetricInstrument.LOG_COUNTER.collector as Counter
    private val numberOfPeopleCounter = MetricInstrument.NUMBER_OF_PEOPLE_COUNTER.collector as Counter
    private val numberOfPeopleGauge = MetricInstrument.NUMBER_OF_PEOPLE_GAUGE.collector as Gauge
    private val temperatureGauge = MetricInstrument.TEMPERATURE_GAUGE.collector as Gauge
    private val windHistogram = MetricInstrument.WIND_HISTOGRAM.collector as Histogram
    private val precipitationSummary = MetricInstrument.PRECIPITATION_SUMMARY.collector as Summary

    fun incLog(request: LogRequest) {
        logCounter.labels(request.id).inc()
    }

    fun incNumberOfPeople(request: LogRequest) {
        numberOfPeopleCounter.labels(request.id).inc(request.numberOfPeople.toDouble())
    }

    fun setNumberOfPeople(request: LogRequest) {
        numberOfPeopleGauge.labels(request.id).set(request.numberOfPeople.toDouble())
    }

    fun setTemperature(request: LogRequest) {
        temperatureGauge.labels(request.id).set(request.temperature)
    }

    fun observeWind(request: LogRequest) {
        windHistogram.labels(request.id).observe(request.wind)
    }

    fun observePrecipitation(request: LogRequest) {
        precipitationSummary.labels(request.id).observe(request.precipitation)
    }

}

