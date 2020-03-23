package com.lsantamaria.currencies.integration

import com.lsantamaria.currencies.common.COINRANKING_BASE_URL
import org.springframework.stereotype.Component
import org.springframework.web.util.UriBuilder
import java.net.URI

@Component
class CoinRankingClient(url:String = COINRANKING_BASE_URL) : ExternalClient<CoinRankingResponse>(url) {
    override fun getClazz(): Class<CoinRankingResponse> {
        return CoinRankingResponse::class.java
    }

    override fun getURI(): (t: UriBuilder) -> URI {
        return { uriBuilder ->
            uriBuilder.path("/coins").queryParam("limit", 100).build()
        }
    }
}
