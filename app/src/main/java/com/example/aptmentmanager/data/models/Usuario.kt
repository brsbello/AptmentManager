package com.example.aptmentmanager.data.models

import androidx.room.Entity

@Entity
data class Usuario(

    var classe: Int? = null,
    var name: String = "",
    var email: String = "",
    var photo: String? = null
)