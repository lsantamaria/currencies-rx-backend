package com.lsantamaria.currencies.config

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import com.lsantamaria.currencies.domain.model.CurrencyEventType
import com.lsantamaria.currencies.domain.repository.EventsRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.UUID
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile


@Component
@Profile("bootstrap")
class EventLoader(private val eventsRepository: EventsRepository) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val high = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.HIGH_PRICE, "Bitcoin has breached the high treshold")
        val low = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.LOW_PRICE, "Ethereum has breached the low treshold")
        val suspended = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.SUSPENDED, "Ripple currency has been suspended")

        Flux.just(high, low, suspended).flatMap{ eventsRepository.save(it) }
                .thenMany(eventsRepository.findAll())
                .subscribe { println(it)}
    }
}

