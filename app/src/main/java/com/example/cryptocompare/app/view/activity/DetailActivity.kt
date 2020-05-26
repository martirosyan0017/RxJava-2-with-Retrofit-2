package com.example.cryptocompare.app.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.cryptocompare.R
import com.example.cryptocompare.app.base.BaseActivity
import com.example.cryptocompare.app.constants.SendData
import com.example.cryptocompare.app.model.CryptoPriceEntity
import com.example.cryptocompare.app.viewmodel.CryptoViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity(R.layout.activity_detail) {

    private lateinit var viewModel: CryptoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        getData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
    }

    private fun getData() {
        if (!intent.hasExtra(SendData.EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(SendData.EXTRA_FROM_SYMBOL)
        val observer = Observer<CryptoPriceEntity> {
            loadData(it)
        }
        viewModel.getDetailInfo(fromSymbol!!).observe(this, observer)
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(modelCrypto: CryptoPriceEntity) {
        modelCrypto.let {
            tvPrice.text = it.price + " " + resources.getString(R.string.dollar)
            tvMinPrice.text = it.lowDay + " " + resources.getString(R.string.dollar)
            tvMaxPrice.text = it.highDay + " " + resources.getString(R.string.dollar)
            tvLastMarket.text = it.lastMarket
            lastUpdate.text = it.getFormattedTime()
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol

            // Coil (image loader)
            imageCoin.load(it.getFullImageUrl())
        }
    }
}
