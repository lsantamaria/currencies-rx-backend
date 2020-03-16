package com.lsantamaria.currencies.domain.service

import com.lsantamaria.currencies.common.COINRANKING_BASE_URL
import com.lsantamaria.currencies.common.FETCH_CURRENCIES_URL
import com.lsantamaria.currencies.domain.model.Currency
import com.lsantamaria.currencies.dto.CoinRankingResponse

import org.reactivestreams.Publisher
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.util.UriBuilder
import java.net.URI

@Component
class CurrencyService {

    fun getCurrenciesStream(): Publisher<Currency> {
        return Flux.generate<Currency> { movieEventSynchronousSink ->
            movieEventSynchronousSink.next(Currency("currency", Instant.now().toString()))
        }.delayElements(Duration.ofSeconds(3))
    }

    fun getCurrenciesList(): Publisher<Currency> {
        println("Get currencies")

        return WebClient
                .builder()
                .baseUrl(COINRANKING_BASE_URL)
                .build()
                .get()
                .uri(buildUriParams())
                .retrieve()
                .bodyToMono(CoinRankingResponse::class.java)
                .doOnSuccess(onSuccess())
                .onErrorResume(onError())
                .flatMapIterable { t -> t.data.coins }
                .map { Currency(it.name, it.symbol) }
    }

    private fun buildUriParams(): (t: UriBuilder) -> URI {
        return { uriBuilder ->
            uriBuilder
                    .path("/coins")
                    .queryParam("limit", 1000)
                    .build()
        }
    }

    private fun onSuccess(): (t: CoinRankingResponse) -> Unit {
        return { args ->
            println(args.toString())
        }
    }

    private fun onError(): (t: Throwable) -> Mono<out CoinRankingResponse> {
        return { e ->
            var errorResponse = ""
            if (e is WebClientResponseException) {
                errorResponse = e.responseBodyAsString
            }
            Mono.error(RuntimeException("Error fetching data from coins api $errorResponse"))
        }
    }

}
