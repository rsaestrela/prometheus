package com.github.rsaestrela.election

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.CrossOrigin

@SpringBootApplication
@CrossOrigin
class ElectionApplication

fun main(args: Array<String>) {
    SpringApplication.run(ElectionApplication::class.java, *args)
}