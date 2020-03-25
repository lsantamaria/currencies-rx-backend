package com.lsantamaria.currencies

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import com.lsantamaria.currencies.domain.model.CurrencyEventType
import com.lsantamaria.currencies.domain.repository.EventsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.CountDownLatch

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
class EventStreamITest(@Autowired val webtestClient: WebTestClient, @Autowired val eventsRepository: EventsRepository) {

    private final val event1 = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.HIGH_PRICE,
            "Bitcoin has breached the high treshold")
    private final val event2 = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.LOW_PRICE,
            "Bitcoin has breached the low treshold")
    private final val event3 = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.SUSPENDED,
            "Bitcoin has been suspended")

    val testEvents = setOf(event1, event2, event3)

    @Test
    fun `test stored events are streamed`() {
        val countDownLatch = CountDownLatch(testEvents.size)
        val receivedEvents = mutableListOf<CurrencyEvent>()

        Flux.just(event1, event2, event3)
                .flatMap { eventsRepository.save(it) }
                .thenEmpty {
                    webtestClient
                            .get()
                            .uri("/events")
                            .accept(MediaType.TEXT_EVENT_STREAM)
                            .exchange()
                            .returnResult(CurrencyEvent::class.java)
                            .responseBody
                            .subscribe { event ->
                                assertThat(testEvents.contains(event))
                                receivedEvents.add(event)
                                countDownLatch.countDown()
                            }
                }
                .subscribe()

        countDownLatch.await()
        assertEquals(receivedEvents.size, testEvents.size)
    }

    @Test
    fun `test create new event`() {
        val newEvent = CurrencyEvent(UUID.randomUUID().toString(), CurrencyEventType.SUSPENDED,
                "New suspension event")
        webtestClient
                .post()
                .uri("/events")
                .body(BodyInserters.fromPublisher(Mono.just(newEvent), CurrencyEvent::class.java))
                .exchange()
                .expectStatus()
                .isCreated
    }
}
