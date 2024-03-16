package com.android_labs.moviesapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenresModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}