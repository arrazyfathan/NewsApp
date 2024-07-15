package com.arrazyfathan.home_data.model

import com.google.gson.annotations.SerializedName

data class SourceDto(
    @SerializedName("name")
    val name: String? = "",
)
