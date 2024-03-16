package com.android_labs.photogallery

import com.google.gson.annotations.SerializedName

data class GalleryItem (
    var id: String = "",
    var title: String = "",
    @SerializedName("url_s") var url: String = "",
)