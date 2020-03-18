package com.lsantamaria.currencies.domain.service

import com.lsantamaria.currencies.integration.*
import org.junit.jupiter.api.Test
import io.mockk.mockk
import org.mockito.InjectMocks
import java.util.*
import com.lsantamaria.currencies.domain.model.Currency
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import reactor.core.publisher.Mono

class CurrencyServiceTest {
    private val coinRankingClient: ExternalClient<CoinRankingResponse> = mockk()
    private val coinloreClient: ExternalClient<CoinloreResponse> = mockk()

    @InjectMocks
    var currencyService: CurrencyService = CurrencyService(coinRankingClient, coinloreClient)

    @Test
    fun fetchAggregatedCurrencies() {
		val coinloreBitcoin = CoinloreDto(UUID.randomUUID().toString(), "BTC", "Bitcoin")
		val coinloreEthereum = CoinloreDto(UUID.randomUUID().toString(), "ETH", "Ethereum")
		val coinloreCoins = listOf(coinloreBitcoin, coinloreEthereum)
		val coinloreResponse = CoinloreResponse(coinloreCoins)

		val coinRankingBitcoin = CoinRankingDto(UUID.randomUUID().toString(), "Bitcoin", "Bitcoin is the first decentralized digital currency", "")
		val coinRankingRipple = CoinRankingDto(UUID.randomUUID().toString(), "Ripple", "Ripple is a global real-time settlement network that connects banks around the world", "")
		val coinRankingCoins = listOf(coinRankingBitcoin, coinRankingRipple)
		val coinRankingResponse = CoinRankingResponse("success", CoinRankingResponseData(coinRankingCoins))

		every { coinRankingClient.fetch()} returns Mono.just(coinRankingResponse)
		every { coinloreClient.fetch()} returns Mono.just(coinloreResponse)

        val currencies: Mono<List<Currency>> = currencyService.getCurrenciesList() as Mono<List<Currency>>
		val expectedAggregatedCurrency = Currency(coinloreBitcoin.name, coinloreBitcoin.symbol, coinRankingBitcoin.description, coinRankingBitcoin.iconUrl)

		currencies.subscribe { list ->
			assertEquals(1, list.size)
			assertEquals(expectedAggregatedCurrency, list[0])
		}
	}
}
