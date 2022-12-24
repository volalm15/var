package com.volalm15.dataservice

import jakarta.annotation.security.RolesAllowed
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@SpringBootApplication
class DataServiceApplication

fun main(args: Array<String>) {
    runApplication<DataServiceApplication>(args = args)
}

@RestController
class DataServiceController {

    data class Book(val id: String, val title: String, val author: String)

    @GetMapping("books")
    fun getBooks(): Flux<Book> {
        return Flux.just(
            Book("1", "Book 1", "Author 1"),
            Book("2", "Book 2", "Author 2"),
            Book("3", "Book 3", "Author 3")
        )
    }

    @RolesAllowed("ROLE_VIEWER")
    @GetMapping("whoami")
    @ResponseBody
    fun getUserInfo(@AuthenticationPrincipal user: Jwt): MutableMap<String, Any> {
        val model: MutableMap<String, Any> = HashMap()
        model["userName"] = user
        return model
    }
}
