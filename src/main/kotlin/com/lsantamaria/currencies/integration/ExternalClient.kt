package com.lsantamaria.currencies.integration

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import org.springframework.web.util.UriBuilder
import reactor.core.scheduler.Schedulers
import java.net.URI

abstract class ExternalClient<T>(url:String) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client: WebClient = WebClient
            .builder()
            .baseUrl(url)
            .build()

    fun fetch(): Mono<T> {
        return client
                .get()
                .uri(getURI())
                .retrieve()
                .bodyToMono(getClazz())
                .doOnSuccess(onSuccess())
                .onErrorResume(onError())
                .subscribeOn(Schedulers.elastic())
    }

    private fun onError(): (t: Throwable) -> Mono<out T> {
        return { e ->
            var errorResponse = ""
            if (e is WebClientResponseException) {
                errorResponse = e.responseBodyAsString
            }
            Mono.error(RuntimeException("Error fetching data from coins api ${getURI()} $errorResponse", e))
        }
    }

    private fun onSuccess(): (t: T) -> Unit {
        return { response ->
            logger.info("Fetch data from ${getURI()}, successful, response ${response.toString()}")
        }
    }

    abstract fun getURI():(t: UriBuilder) -> URI

    abstract fun getClazz(): Class<T>
}
