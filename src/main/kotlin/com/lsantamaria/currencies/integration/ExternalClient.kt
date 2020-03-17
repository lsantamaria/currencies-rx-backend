package com.lsantamaria.currencies.integration

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import org.springframework.web.util.UriBuilder
import reactor.core.scheduler.Schedulers
import java.net.URI


abstract class ExternalClient<T>(url:String) {
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
        return { args ->
            println("SUCCESS, response ${args.toString()}")
        }
    }

    abstract fun getURI():(t: UriBuilder) -> URI

    abstract fun getClazz(): Class<T>
}
