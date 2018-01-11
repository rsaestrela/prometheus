package com.github.rsaestrela.election

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
class ElectionController(@Autowired val electionService: ElectionService) {

    @RequestMapping(method = [(RequestMethod.POST)])
    fun voteRequest(@RequestBody voteRequest: VoteRequest): ResponseEntity<Any> {
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

