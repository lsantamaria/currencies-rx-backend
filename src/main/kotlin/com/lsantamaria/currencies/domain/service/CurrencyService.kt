package com.lsantamaria.currencies.domain.service

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import com.lsantamaria.currencies.domain.model.Currency
import com.lsantamaria.currencies.domain.repository.EventsRepository
import com.lsantamaria.currencies.integration.*

import org.reactivestreams.Publisher
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.function.BiFunction
import java.util.stream.Collectors.toList

@Component
class CurrencyService(val coinRankingClient: ExternalClient<CoinRankingResponse>,
                      val coinloreClient: ExternalClient<CoinloreResponse>,
                      val eventsRepository: EventsRepository) {
    fun getCurrenciesStream(): Publisher<CurrencyEvent> {
        return eventsRepository.findWithTailableCursorBy()
    }

    fun getCurrenciesList(): Publisher<List<Currency>> {
        val coinRankingResponse: Mono<CoinRankingResponse> = coinRankingClient.fetch()
        val coinloreResponse: Mono<CoinloreResponse> = coinloreClient.fetch()

        val combinator = BiFunction<CoinloreResponse, CoinRankingResponse, List<Currency>> { coinlore, coinRanking ->
            val coins = coinlore.data
            val coinsWithDetails = coinRanking.data.coins

            coins.stream()
                    .map { coin ->
                        coinsWithDetails.stream()
                                .filter { details -> details.name == coin.name }
                                .findFirst()
                                .map { details ->
                                    Currency(coin.name, coin.symbol, details.description, details.iconUrl)
                                }
                                .orElse(null)
                    }
                    .filter { el -> el != null }.collect(toList())
        }

        return Mono.zip(coinloreResponse, coinRankingResponse, combinator)
    }
}
