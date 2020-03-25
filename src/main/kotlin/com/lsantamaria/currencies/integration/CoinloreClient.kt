package com.lsantamaria.currencies.integration

import com.lsantamaria.currencies.common.COINLORE_BASE_URL
import org.springframework.stereotype.Component
import org.springframework.web.util.UriBuilder
import java.net.URI

@Component
class CoinloreClient(url:String = COINLORE_BASE_URL) : ExternalClient<CoinloreResponse>(url) {

    override fun getResponseClass(): Class<CoinloreResponse> {
        return CoinloreResponse::class.java
    }

    override fun getURI(): (t: UriBuilder) -> URI {
        return { uriBuilder ->
            uriBuilder.path("/tickers/")
                    .queryParam("start", 0)
                    .queryParam("limit", 100)
                    .build()
        }
    }
}
