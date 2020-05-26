package com.example.cryptocompare.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptocompare.app.api.RetrofitClient
import com.example.cryptocompare.app.database.AppDatabase
import com.example.cryptocompare.app.model.CoinPriceInfoRawData
import com.example.cryptocompare.app.model.CryptoPriceEntity
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CryptoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    fun getPriceList(): LiveData<List<CryptoPriceEntity>> {
        return database.coinPriceInfoDao().getPriceList()
    }

    fun getDetailInfo(fSym: String): LiveData<CryptoPriceEntity> {
        return database.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = RetrofitClient.instance.apiService.getTopCoinsInfo(limit = 50)
            .map {
                it.data?.map {
                    it.coinInfo?.name
                }?.joinToString(",")
            }
            .flatMap {
                RetrofitClient.instance.apiService.getFullPriceList(fSyms = it)
            }
            .map {
                getPriceListFromRawData(it)
            }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                database.coinPriceInfoDao().insertPriceList(it)
            }, {
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CryptoPriceEntity> {
        val result = ArrayList<CryptoPriceEntity>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()

        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()

            for (currencyKey in currencyKeySet) {

                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CryptoPriceEntity::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}