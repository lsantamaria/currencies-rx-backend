package com.lsantamaria.currencies.reactiveweb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class CurrencyRouter{

    @Bean
    fun route(currencyHandler: CurrencyHandler): RouterFunction<ServerResponse>{
        return RouterFunctions
                .route(GET("/currencies").and(accept(MediaType.TEXT_EVENT_STREAM)),
                        HandlerFunction { request -> currencyHandler.fetchCurrencies(request)});
    }
}
