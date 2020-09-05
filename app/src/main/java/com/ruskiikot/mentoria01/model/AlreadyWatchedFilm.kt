package com.ruskiikot.mentoria01.model

import android.os.Parcel
import android.os.Parcelable

data class AlreadyWatchedFilm(
    val imdbID: String?,
    val timestamp: Long,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        imdbID = parcel.readString(),
        timestamp = parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imdbID)
        parcel.writeLong(timestamp)
    }

    override fun describeContents() = 0

    private val CREATOR = object : Parcelable.Creator<AlreadyWatchedFilm> {
        override fun createFromParcel(parcel: Parcel) = AlreadyWatchedFilm(parcel)
        override fun newArray(size: Int): Array<AlreadyWatchedFilm?> = arrayOfNulls(size)
    }
}
