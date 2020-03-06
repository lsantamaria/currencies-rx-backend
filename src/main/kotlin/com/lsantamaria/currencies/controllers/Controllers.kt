package com.lsantamaria.currencies.controllers

import com.lsantamaria.currencies.domain.Currency
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant


@RestController
@RequestMapping
@CrossOrigin
class CurrenciesController {

    @GetMapping(path = ["/currencies2"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getCurrencies(): Flux<Currency> {
        return Flux.generate<Currency> {
            movieEventSynchronousSink -> movieEventSynchronousSink.next(Currency("currency", Instant.now().toString()))
        }.delayElements(Duration.ofSeconds(3))
    }


}
