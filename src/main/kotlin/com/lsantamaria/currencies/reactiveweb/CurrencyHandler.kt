package com.lsantamaria.currencies.reactiveweb

import com.lsantamaria.currencies.domain.Currency
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

    fun fetchCurrencies(request:ServerRequest): Mono<ServerResponse> {
        return streamingResponse(currencyService.getAllCurrencies())
    }

    private fun streamingResponse(currencyFlux: Publisher<Currency>): Mono<ServerResponse>{
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(currencyFlux, Currency::class.java)
    }
}

