package com.lsantamaria.currencies.reactiveweb

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import com.lsantamaria.currencies.domain.service.EventService
import com.lsantamaria.currencies.integration.EventDto
import org.reactivestreams.Publisher
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*

@Component
class EventHandler(private val eventService: EventService) {

    fun addEvent(request: ServerRequest): Mono<ServerResponse> {
        val eventMono =
                request.bodyToMono(EventDto::class.java)
                        .map {CurrencyEvent(UUID.randomUUID().toString(), it.type, it.info)}
                        .flatMap { eventService.saveNewEvent(it) }

        return defaultResponse(eventMono)
    }

    fun getEventsStream(request:ServerRequest): Mono<ServerResponse> {
        return streamingResponse(eventService.getEventsStream())
    }

    private fun streamingResponse(currencyFlux: Publisher<CurrencyEvent>): Mono<ServerResponse>{
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(currencyFlux, CurrencyEvent::class.java)
    }

    private fun defaultResponse(eventMono: Mono<CurrencyEvent>): Mono<ServerResponse> {
        return eventMono
                .flatMap { event ->
                    ServerResponse.created(URI.create("/events/${event.id}"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .build()
                }
    }
}

