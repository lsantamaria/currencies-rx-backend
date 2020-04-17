package com.lsantamaria.currencies.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter



@Configuration
class AppConfig{
    @Bean
    fun corsFilter(): CorsWebFilter {
        return CorsWebFilter { exchange -> CorsConfiguration().applyPermitDefaultValues() }
    }
}
