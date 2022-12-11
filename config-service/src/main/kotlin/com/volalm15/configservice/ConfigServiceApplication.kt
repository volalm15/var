package com.volalm15.configservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConfigServiceApplication

fun main(args: Array<String>) {
    runApplication<ConfigServiceApplication>(args = args)
}
