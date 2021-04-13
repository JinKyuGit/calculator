package com.app.calculator.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArenaInfo (
    var df_1_character_id : String? = "",
    var df_2_character_id : String? = "",
    var df_3_character_id : String? = "",
    var df_4_character_id : String? = "",
    var df_5_character_id : String? = "",

    val of_1_character_id : String? = "",
    val of_2_character_id : String? = "",
    val of_3_character_id : String? = "",
    val of_4_character_id : String? = "",
    val of_5_character_id : String? = "",
    val regNick : String? = "",
    val regDate : String? = ""
) : Parcelable

