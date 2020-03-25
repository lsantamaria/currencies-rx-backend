package com.lsantamaria.currencies.common

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.CollectionOptions
import java.util.Arrays.asList
import javax.annotation.PostConstruct

@Configuration
class MongoConfig : AbstractReactiveMongoConfiguration() {
    @PostConstruct
    fun config(){
        reactiveMongoTemplate().dropCollection("currencyEvent")
                .then(reactiveMongoTemplate().createCollection("currencyEvent",
                                CollectionOptions.empty().capped().size(4096)))
                .subscribe()
    }
    override fun getMappingBasePackages(): MutableCollection<String> {
        return asList("com.lsantamaria.currencies.domain")
    }

    override fun getDatabaseName(): String {
        return "currencies"
    }

    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create()
    }
}
