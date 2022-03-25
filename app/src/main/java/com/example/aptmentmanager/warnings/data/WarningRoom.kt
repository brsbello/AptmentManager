package com.example.aptmentmanager.warnings.data

import androidx.room.*
import com.example.aptmentmanager.models.WarningModel

@Dao
interface WarningRoom {

    @Query("SELECT * FROM WarningModel")
    fun searchAll(): List<WarningModel>

    @Insert
    fun save(warning: WarningModel)

    @Delete
    fun delete(warning: WarningModel)

    @Update
    fun update(warning: WarningModel)

    //@Query("SELECT * FROM WarningModel WHERE id = :id")
    //fun searchForId(id: Long): WarningModel?

    //@Query("SELECT * FROM WarningModel ORDER BY date ASC")
    //fun searchAllOrderByDescAsc(): List<WarningModel>
}