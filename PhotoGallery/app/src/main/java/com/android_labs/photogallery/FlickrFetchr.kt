package com.android_labs.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG ="FlickrFetchr"
const val FLICKR_BASE_URL = "https://api.flickr.com"

class FlickrFetchr {

    private val flickrApi: FlickrApi

    init {

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(FLICKR_BASE_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchContents(): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val flickrHomePageRequest: Call<String> = flickrApi.fetchContents()

        flickrHomePageRequest.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }
        })

        return responseLiveData
    }

    fun fetchPhotos(): LiveData<List<GalleryItem>> {
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        val flickrPhotoPageRequest: Call<FlickrResponse> = flickrApi.fetchPhotos()

        flickrPhotoPageRequest.enqueue(object: Callback<FlickrResponse> {
            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                val apiUrl = flickrPhotoPageRequest.request().url().toString()
                val galleryItems = response.body()?.photos?.galleryItems ?: mutableListOf()
                responseLiveData.value = galleryItems

                Log.d(TAG, "Response-received: apiUrl=$apiUrl, response=${galleryItems.size}")
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }
        })

        return responseLiveData
    }

}