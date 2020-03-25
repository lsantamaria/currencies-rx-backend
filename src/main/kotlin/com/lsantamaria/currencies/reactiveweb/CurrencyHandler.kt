package com.lsantamaria.currencies.reactiveweb

import com.lsantamaria.currencies.domain.model.Currency
import com.lsantamaria.currencies.domain.service.CurrencyService
import org.reactivestreams.Publisher
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class CurrencyHandler(private val currencyService: CurrencyService) {

    fun fetchCurrenciesList(request:ServerRequest): Mono<ServerResponse> {
        return defaultResponse(currencyService.getCurrenciesList())
    }

    private fun defaultResponse(currencyFlux: Publisher<List<Currency>>): Mono<ServerResponse>{
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(currencyFlux, Currency::class.java)
    }
}

