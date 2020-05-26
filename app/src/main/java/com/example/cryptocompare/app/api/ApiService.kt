package com.example.cryptocompare.app.api
import com.example.cryptocompare.app.model.CoinInfoListOfData
import com.example.cryptocompare.app.model.CoinPriceInfoRawData
import com.example.cryptocompare.app.constants.QueryParams
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QueryParams.QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QueryParams.QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QueryParams.QUERY_PARAM_TO_SYMBOL) tSym: String = QueryParams.CURRENCY
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QueryParams.QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QueryParams.QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QueryParams.QUERY_PARAM_TO_SYMBOLS) tSyms: String = QueryParams.CURRENCY
    ): Single<CoinPriceInfoRawData>

}