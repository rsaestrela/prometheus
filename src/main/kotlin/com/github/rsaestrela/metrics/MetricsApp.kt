package com.github.rsaestrela.metrics

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.CrossOrigin


@SpringBootApplication
@EnableScheduling
@CrossOrigin
class MetricsApp

fun main(args: Array<String>) {
    SpringApplication.run(MetricsApp::class.java, *args)
}