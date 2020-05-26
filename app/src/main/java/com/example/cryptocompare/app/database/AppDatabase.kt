package com.example.cryptocompare.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptocompare.app.constants.DataBase
import com.example.cryptocompare.app.model.CryptoPriceEntity

@Database(entities = [CryptoPriceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var db: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {

            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DataBase.DB_NAME
                    ).build()
                db = instance
                return instance
            }
        }
    }
    abstract fun coinPriceInfoDao(): CoinDao
}
