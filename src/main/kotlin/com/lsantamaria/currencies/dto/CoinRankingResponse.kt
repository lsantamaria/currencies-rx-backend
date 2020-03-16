package com.lsantamaria.currencies.dto

import lombok.*

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@Data
data class CoinRankingDto(val id:String, val symbol:String, val name:String, val description:String?, val iconUrl:String?)

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@Data
data class CoinRankingResponseData(val coins:List<CoinRankingDto>)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
data class CoinRankingResponse(var status:String, var data:CoinRankingResponseData)
