package com.volalm15.edgeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@SpringBootApplication
@EnableWebFluxSecurity
class EdgeServiceApplication

fun main(args: Array<String>) {
    runApplication<EdgeServiceApplication>(args = args)
}
