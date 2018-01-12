package com.github.rsaestrela.poi

import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import io.prometheus.client.Histogram
import io.prometheus.client.Summary
import org.springframework.stereotype.Service

@Service
class TowerService {

    private val logCounter = MetricInstrument.POI_LOG_COUNTER.collector as Counter
    private val numberOfPeopleCounter = MetricInstrument.POI_NUMBER_OF_PEOPLE_COUNTER.collector as Counter
    private val numberOfPeopleGauge = MetricInstrument.POI_NUMBER_OF_PEOPLE_GAUGE.collector as Gauge
    private val temperatureGauge = MetricInstrument.POI_TEMPERATURE_GAUGE.collector as Gauge
    private val windHistogram = MetricInstrument.POI_WIND_HISTOGRAM.collector as Histogram
    private val precipitationSummary = MetricInstrument.POI_PRECIPITATION_SUMMARY.collector as Summary

    fun incLog(request: LogRequest) {
        logCounter.labels(request.poiId).inc()
    }

    fun incNumberOfPeople(request: LogRequest) {
        numberOfPeopleCounter.labels(request.poiId).inc(request.numberOfPeople.toDouble())
    }

    fun setNumberOfPeople(request: LogRequest) {
        numberOfPeopleGauge.labels(request.poiId).set(request.numberOfPeople.toDouble())
    }

    fun setTemperature(request: LogRequest) {
        temperatureGauge.labels(request.poiId).set(request.temperature)
    }

    fun observeWind(request: LogRequest) {
        windHistogram.labels(request.poiId).observe(request.wind)
    }

    fun observePrecipitation(request: LogRequest) {
        windHistogram.labels(request.poiId).observe(request.precipitation)
    }

}

