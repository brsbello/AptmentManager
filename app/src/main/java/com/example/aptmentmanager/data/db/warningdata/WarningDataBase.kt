package com.example.aptmentmanager.data.db.warningdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aptmentmanager.data.models.WarningModel
import com.example.aptmentmanager.data.room.WarningRoom

@Database(entities = [WarningModel::class], version = 1, exportSchema = false)
abstract class WarningDataBase : RoomDatabase(){

    abstract fun WarningDao(): WarningRoom

    companion object {
        fun intance(context: Context) : WarningDataBase {
            return Room.databaseBuilder(
                context,
                WarningDataBase::class.java,
                "warnings.db"
            ).allowMainThreadQueries().build()
        }
    }
}