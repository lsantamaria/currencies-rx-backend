package com.lsantamaria.currencies.controllers

import com.lsantamaria.currencies.domain.model.Currency
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant


@RestController
@RequestMapping
@CrossOrigin
class CurrenciesController {

    @GetMapping(path = ["/currencies-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getCurrencies(): Flux<Currency> {
        return Flux.generate<Currency> {
            movieEventSynchronousSink -> movieEventSynchronousSink.next(Currency("currency", Instant.now().toString(), "", ""))
        }.delayElements(Duration.ofSeconds(3))
    }
}
