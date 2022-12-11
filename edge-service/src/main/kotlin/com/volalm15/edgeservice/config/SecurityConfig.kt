package com.volalm15.edgeservice.config

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository

/**
 * @author : Alois Vollmaier (A199165)
 * @since : 07.09.2022, Wed
 **/

@EnableWebFluxSecurity
@Configuration
class SecurityConfig(private val clientRegistrationRepository: ReactiveClientRegistrationRepository) {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.csrf().disable().authorizeExchange { exchange ->
            exchange.matchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .pathMatchers("/", "/*.css", "/*.js", "/favicon.ico").permitAll().anyExchange()
                .authenticated()
        }.oauth2Login(Customizer.withDefaults()).logout { logout ->
            logout.logoutSuccessHandler(
                logoutSuccessHandler(
                    clientRegistrationRepository
                )
            )
        }.csrf { csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()) }
            .build()
    }

    private fun logoutSuccessHandler(repository: ReactiveClientRegistrationRepository): ServerLogoutSuccessHandler {
        val oidcLogoutSuccessHandler =
            OidcClientInitiatedServerLogoutSuccessHandler(repository)
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}")
        return oidcLogoutSuccessHandler
    }
}
