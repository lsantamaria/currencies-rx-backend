package com.lsantamaria.currencies.domain.model

import org.springframework.data.mongodb.core.mapping.Document

enum class CurrencyEventType{
    HIGH_PRICE, LOW_PRICE, SUSPENDED
}

@Document(collection = "currencyEvent")
data class CurrencyEvent(val id:String, val type: CurrencyEventType, val info:String)
