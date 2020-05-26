package com.example.cryptocompare.app.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocompare.app.constants.SendData

open class BaseActivity(contentView: Int) : AppCompatActivity(contentView) {

    fun newIntent(activity: Class<out AppCompatActivity>, fromSymbol: String): Intent {
        val intent = Intent(this, activity)
        intent.putExtra(SendData.EXTRA_FROM_SYMBOL, fromSymbol)
        return intent
    }
}
