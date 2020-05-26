package com.example.cryptocompare.app.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocompare.app.model.CryptoPriceEntity
import com.example.cryptocompare.app.view.adapter.viewholder.CryptoListViewHolder

open class BaseViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(data: CryptoPriceEntity?, position: Int = -1, onClick:((CryptoPriceEntity) -> Unit?)?) {
        when (data) {
            is CryptoPriceEntity -> {
                CryptoListViewHolder(itemView).bind(data, position, onClick)
            }
        }
    }
}