package com.lsantamaria.currencies

import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry

@Configuration
@EnableWebFlux
class WebFluxConfig : WebFluxConfigurer{
    override fun addCorsMappings(registry: CorsRegistry)
    {
        registry.addMapping("/**")
                .allowedOrigins("*") // any host or put domain(s) here
                .allowedMethods("GET, POST", "PUT", "DELETE", "OPTIONS") // put the http verbs you want allow
                .allowedHeaders("x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN") // put the http headers you want allow
    }
}
