package com.github.rsaestrela.poi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.CrossOrigin


@SpringBootApplication
@EnableScheduling
@CrossOrigin
class TowerApplication

fun main(args: Array<String>) {
    SpringApplication.run(TowerApplication::class.java, *args)
}