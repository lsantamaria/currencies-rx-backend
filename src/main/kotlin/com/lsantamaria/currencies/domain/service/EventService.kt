package com.lsantamaria.currencies.domain.service

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import com.lsantamaria.currencies.domain.repository.EventsRepository
import org.reactivestreams.Publisher

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class EventService(val eventsRepository: EventsRepository) {

    fun getEventsStream(): Publisher<CurrencyEvent> {
        return eventsRepository.findWithTailableCursorBy()
    }

    fun saveNewEvent(currencyEvent: CurrencyEvent):Mono<CurrencyEvent>{
        return eventsRepository.save(currencyEvent)
    }
}
