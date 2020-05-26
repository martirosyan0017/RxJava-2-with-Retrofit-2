package com.example.cryptocompare.app.view.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocompare.R
import com.example.cryptocompare.app.base.BaseActivity
import com.example.cryptocompare.app.model.CryptoPriceEntity
import com.example.cryptocompare.app.view.adapter.CryptoRecyclerView
import com.example.cryptocompare.app.viewmodel.CryptoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private lateinit var viewModel: CryptoViewModel
    private lateinit var adapterCrypto: CryptoRecyclerView

    private val adapterCoin by lazy {
        adapterCrypto = CryptoRecyclerView()
        rvCoinPriceList.setHasFixedSize(true)
        adapterCrypto.clickItem {
            goTo(it)
        }
        rvCoinPriceList.adapter = adapterCrypto
        return@lazy adapterCrypto
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        getData()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
    }

    private fun goTo(model: CryptoPriceEntity) {
        val intent = newIntent(DetailActivity::class.java, model.fromSymbol)
        startActivity(intent)
    }

    private fun getData() {
        progressBar.visibility = View.VISIBLE
        val observer = Observer<List<CryptoPriceEntity>> {
            progressBar.visibility = View.INVISIBLE
            adapterCoin.submitData(it)
        }
        viewModel.getPriceList().observe(this, observer)
    }
}
