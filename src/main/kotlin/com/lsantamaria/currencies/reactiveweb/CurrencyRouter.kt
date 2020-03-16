package com.lsantamaria.currencies.reactiveweb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class CurrencyRouter{

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<String> {
        println("error from handler3")
        return ResponseEntity.status(ex.rawStatusCode).body(ex.responseBodyAsString)
    }

    @Bean
    fun route(currencyHandler: CurrencyHandler): RouterFunction<ServerResponse>{
        return RouterFunctions
                .route(GET("/currencies-stream").and(accept(MediaType.TEXT_EVENT_STREAM)),
                        HandlerFunction { request -> currencyHandler.fetchCurrenciesStream(request)})
                .andRoute(GET("/currencies").and(accept(MediaType.APPLICATION_JSON)),
                        HandlerFunction { request -> currencyHandler.fetchCurrenciesList(request)});
    }
}
