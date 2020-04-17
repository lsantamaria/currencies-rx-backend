package com.lsantamaria.currencies.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurerComposite
import org.springframework.web.reactive.config.WebFluxConfigurer




@Configuration
class AppConfig {
    @Bean
    fun corsConfigurer(): WebFluxConfigurer {
        return object : WebFluxConfigurerComposite() {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry!!.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("*")
            }
        }
    }
}
