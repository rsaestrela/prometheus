package com.github.rsaestrela.prometheus

import io.prometheus.client.Counter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@CrossOrigin
class PrometheusInPracticeApp
fun main(args: Array<String>) {
    SpringApplication.run(PrometheusInPracticeApp::class.java, *args)
}

@Controller
@RequestMapping("/")
class Controller {

    val c : Counter = MetricInstrument.SOCIAL_NETWORKS_IN_USE.collector as Counter

    @RequestMapping(method = [(RequestMethod.GET)])
    fun indexGet(): String{
        return "index"
    }

    @RequestMapping(method = [(RequestMethod.POST)])
    fun indexPost(request: HttpServletRequest): String{
        c.labels(request.getParameter("preference")).inc()
        System.out.println("facebook " + c.labels("facebook").get())
        System.out.println("instagram " + c.labels("instagram").get())
        return "index"
    }
}

