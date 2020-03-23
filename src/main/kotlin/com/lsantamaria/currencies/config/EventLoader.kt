package com.lsantamaria.currencies.config

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import com.lsantamaria.currencies.domain.model.CurrencyEventType
import com.lsantamaria.currencies.domain.repository.EventsRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.*
import java.util.stream.Stream





@Component
class EventLoader(private val eventsRepository: EventsRepository): CommandLineRunner {
    override fun run(vararg args: String?) {

        val high = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.HIGH_PRICE, "Bitcoin has breached the high treshold")
        val low = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.LOW_PRICE, "Ethereum has breached the low treshold")
        val suspended = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.SUSPENDED, "Ripple currency has been suspended")

        Flux.fromStream(Stream.of(high, low, suspended))
                .flatMap {  event -> eventsRepository.save(event)}
                .subscribe{
                    eventsRepository.findAll().subscribe{event -> println(event)}
                }

    }
}
