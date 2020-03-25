package com.lsantamaria.currencies.integration

import com.lsantamaria.currencies.domain.model.CurrencyEventType

data class EventDto(val id:String?, val type:CurrencyEventType, val info:String?)
