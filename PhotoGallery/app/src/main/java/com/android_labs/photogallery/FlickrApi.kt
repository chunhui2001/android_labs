package com.android_labs.photogallery

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    @GET("/")
    fun fetchContents(): Call<String>

    @GET("/services/rest/?method=flickr.interestingness.getList&api_key=5e1428ad1828de40e0969cae48143af8&format=json&nojsoncallback=1&extras=url_s")
    fun fetchPhotos(): Call<FlickrResponse>
}