package com.lsantamaria.currencies.integration

data class CoinloreDto(val id: String,
                       val symbol: String,
                       val name: String,
                       val description: String?,
                       val iconUrl: String?)

data class CoinloreResponse(val data: List<CoinloreDto>)
