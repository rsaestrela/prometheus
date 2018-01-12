package com.github.rsaestrela.poi

import khttp.post
import org.json.JSONObject
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import java.util.*


@Component
class LogSimulator {

    companion object {
        val poiIds: Array<String> = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    }

    @Scheduled(fixedRate = 5000)
    fun reportCurrentTime() {
        poiIds.forEach { id ->
            val poiRequest = LogRequest(id, randInt(0, 100), randDouble(20, 30), randDouble(0, 10), randDouble(0, 5))
            val jsonObject = JSONObject(poiRequest)
            println(jsonObject)
            post("http://localhost:8080/", json = jsonObject)
        }
    }

    private val random = Random()

    private fun randInt(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    private fun randDouble(from: Int, to: Int): Double {
        return from + (to - from) * random.nextDouble()
    }

}

@Configuration
class AppContext : WebMvcConfigurationSupport() {
    @Bean
    fun taskScheduler(): TaskScheduler {
        return ConcurrentTaskScheduler()
    }
}
