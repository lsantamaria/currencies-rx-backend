package com.lsantamaria.currencies.config

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import com.lsantamaria.currencies.domain.model.CurrencyEventType
import com.lsantamaria.currencies.domain.repository.EventsRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.UUID
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener

@Component
class EventLoader(private val eventsRepository: EventsRepository) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val high = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.HIGH_PRICE, "Existing test event")

        Flux.just(high)
                .flatMap { eventsRepository.save(it) }
                .thenMany(eventsRepository.findAll())
                .subscribe { println(it) }
    }
}

