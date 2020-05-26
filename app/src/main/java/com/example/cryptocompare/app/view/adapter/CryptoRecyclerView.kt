package com.example.cryptocompare.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocompare.R
import com.example.cryptocompare.app.base.BaseViewHolder
import com.example.cryptocompare.app.model.CryptoPriceEntity

open class CryptoRecyclerView : RecyclerView.Adapter<BaseViewHolder>() {

    private lateinit var cryptoInfoList: List<CryptoPriceEntity>

    private var onClick: ((CryptoPriceEntity) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_list, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = cryptoInfoList[position]
        holder.onBind(item, position, onClick)
    }

    override fun getItemCount() = cryptoInfoList.size

    fun clickItem(onClick: (data: CryptoPriceEntity) -> Unit?) {
        this.onClick = onClick
    }

    fun submitData(cryptoInfoList: List<CryptoPriceEntity>) {
        this.cryptoInfoList = cryptoInfoList
        notifyDataSetChanged()
    }
}