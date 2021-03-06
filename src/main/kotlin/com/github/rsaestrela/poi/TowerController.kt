package com.github.rsaestrela.poi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("/")
class TowerController(@Autowired val towerService: TowerService) {

    @RequestMapping(method = [(RequestMethod.POST)])
    fun log(@RequestBody logRequest: LogRequest): ResponseEntity<Any> {
        towerService.incLog(logRequest)
        towerService.incNumberOfPeople(logRequest)
        towerService.observePrecipitation(logRequest)
        towerService.observeWind(logRequest)
        towerService.setNumberOfPeople(logRequest)
        towerService.setTemperature(logRequest)
        return ResponseEntity.ok().build()
    }

    @Configuration
    class ObjectMapperConfiguration {
        @Bean
        @Primary
        fun objectMapper() = ObjectMapper().apply {
            registerModule(KotlinModule())
        }
    }

}

