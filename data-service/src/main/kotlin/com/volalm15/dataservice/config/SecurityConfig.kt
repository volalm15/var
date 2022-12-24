package com.volalm15.dataservice.config

import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.stream.Collectors

@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        return http.authorizeExchange { exchanges: AuthorizeExchangeSpec ->
            exchanges.pathMatchers("/books").hasRole("moderator").anyExchange().authenticated()
        }.oauth2ResourceServer { oauth2: OAuth2ResourceServerSpec ->
            oauth2.jwt { jwt: OAuth2ResourceServerSpec.JwtSpec ->
                jwt.jwtAuthenticationConverter(
                    keycloakAuthConverter()
                )
            }
        }.build()
    }

    /**
     * By default, Keycloak assigns user roles to a "roles" object within the "realm_access" claim.
     * This converter extracts the list of user roles from "realm.access.roles" and builds
     * a list of GrantedAuthority using the "ROLE_" prefix.
     */
    private fun keycloakAuthConverter(): ReactiveJwtAuthenticationConverter {
        val jwtGrantedAuthConv = Converter<Jwt, Collection<GrantedAuthority>> { jwt: Jwt ->
            val realmAccess = jwt.getClaim<Map<String, Collection<String>>>("realm_access")
            val roles = realmAccess["roles"]!!
            roles.stream().map { role: String -> SimpleGrantedAuthority("ROLE_$role") }
                .collect(Collectors.toList())
        }
        val jwtAuthConv = ReactiveJwtAuthenticationConverter()
        jwtAuthConv.setJwtGrantedAuthoritiesConverter(
            ReactiveJwtGrantedAuthoritiesConverterAdapter(jwtGrantedAuthConv)
        )
        return jwtAuthConv
    }
}
