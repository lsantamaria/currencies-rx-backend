package com.lsantamaria.currencies.reactiveweb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class CurrencyRouter{

    @Bean
    fun route(currencyHandler: CurrencyHandler, eventHandler: EventHandler): RouterFunction<ServerResponse>{
        return RouterFunctions
                .route(GET("/currencies").and(accept(MediaType.APPLICATION_JSON)),
                        HandlerFunction { request -> currencyHandler.fetchCurrenciesList(request)})
                .andRoute(GET("/events").and(accept(MediaType.TEXT_EVENT_STREAM)),
                        HandlerFunction { request -> eventHandler.getEventsStream(request)})
                .andRoute(POST("/events").and(accept(MediaType.APPLICATION_JSON)),
                        HandlerFunction { request -> eventHandler.addEvent(request)})
    }
}
