package com.lsantamaria.currencies.integration

data class CoinRankingDto(val id:String,
                          val name:String,
                          val description:String?,
                          val iconUrl:String?)

data class CoinRankingResponseData(val coins:List<CoinRankingDto>)

data class CoinRankingResponse(var status:String, var data:CoinRankingResponseData)
