package com.android_labs.videoplayer

import android.os.Parcel
import android.os.Parcelable

data class MediaFiles(
    val id: String?,
    val title: String?,
    val displayName: String?,
    val size: String?,
    val duration: String?,
    val path: String?,
    val dateAdded: String?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(displayName)
        parcel.writeString(size)
        parcel.writeString(duration)
        parcel.writeString(path)
        parcel.writeString(dateAdded)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaFiles> {
        override fun createFromParcel(parcel: Parcel): MediaFiles {
            return MediaFiles(parcel)
        }

        override fun newArray(size: Int): Array<MediaFiles?> {
            return arrayOfNulls(size)
        }
    }
}