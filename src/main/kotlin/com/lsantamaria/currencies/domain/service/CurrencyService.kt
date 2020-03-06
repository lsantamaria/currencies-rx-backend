package com.lsantamaria.currencies.domain.service

import com.lsantamaria.currencies.domain.Currency
import org.reactivestreams.Publisher
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant

@Component
class CurrencyService{
    fun getAllCurrencies(): Publisher<Currency> {

        val bitcoin = Currency("bitcoin", "BTC")
        val ethereum = Currency("ethereum", "ETH")

        return Flux.generate<Currency> {
            movieEventSynchronousSink -> movieEventSynchronousSink.next(Currency("currency", Instant.now().toString()))
        }.delayElements(Duration.ofSeconds(3))
    }

}
