package com.lsantamaria.currencies.domain.repository

import com.lsantamaria.currencies.domain.model.CurrencyEvent
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface EventsRepository: ReactiveMongoRepository<CurrencyEvent, String>{
    @Tailable
    fun findWithTailableCursorBy(): Flux<CurrencyEvent>
}
