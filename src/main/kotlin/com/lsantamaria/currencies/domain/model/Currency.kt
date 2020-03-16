package com.lsantamaria.currencies.domain.model

import lombok.Builder

@Builder
data class Currency(val name:String, val symbol:String)
