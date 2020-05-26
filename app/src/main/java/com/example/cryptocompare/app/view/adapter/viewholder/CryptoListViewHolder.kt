package com.example.cryptocompare.app.view.adapter.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cryptocompare.R
import com.example.cryptocompare.app.model.CryptoPriceEntity
import kotlinx.android.synthetic.main.items_list.view.*

class CryptoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var onClick: ((CryptoPriceEntity) -> Unit?)? = null

    private val imageLogo = itemView.logo_crypto as ImageView
    private val tvSymbols = itemView.symbols_crypto as TextView
    private val tvPrice = itemView.price_crypto as TextView
    private val tvLastUpdate = itemView.last_update_crypto as TextView
    private val rootItem = itemView.root_item as CardView
    private val symbolsTemplate = itemView.resources.getString(R.string.symbols_template)
    private val lastUpdateTemplate = itemView.resources.getString(R.string.last_update_template)

    @SuppressLint("SetTextI18n")
    fun bind(model: CryptoPriceEntity?, position: Int = -1, onClick: ((CryptoPriceEntity) -> Unit?)?) {

        this.onClick = onClick

        rootItem.setOnClickListener {
            if (onClick != null)
                onClick(model!!)
        }
        model.let {
            tvSymbols.text = String.format(symbolsTemplate, it?.fromSymbol, it?.toSymbol)
            tvPrice.text = it?.price + " " + itemView.resources.getString(R.string.dollar)
            tvLastUpdate.text = String.format(lastUpdateTemplate, it?.getFormattedTime())

            // Coil image loader
            imageLogo.load(it?.getFullImageUrl())
        }
    }
}