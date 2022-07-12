package com.example.aptmentmanager.ui.warnings.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aptmentmanager.data.models.WarningModel

@Database(entities = [WarningModel::class], version = 1, exportSchema = false)
abstract class WarningDataBase : RoomDatabase(){

    abstract fun WarningDao(): WarningRoom

    companion object {
        fun intance(context: Context) : WarningDataBase{
            return Room.databaseBuilder(
                context,
                WarningDataBase::class.java,
                "warnings.db"
            ).allowMainThreadQueries().build()
        }
    }
}