package com.volalm15.dataservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@SpringBootApplication
class DataServiceApplication

fun main(args: Array<String>) {
	runApplication<DataServiceApplication>(args = args)
}

@RestController
class DataServiceController {
	@GetMapping("books")
	fun getBooks(): Flux<String> {
		return Flux.just("Book 1", "Book 2", "Book 3")
	}
}
