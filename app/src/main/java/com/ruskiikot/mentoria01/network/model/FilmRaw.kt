package com.ruskiikot.mentoria01.network.model

import android.os.Parcel
import android.os.Parcelable

data class FilmRaw(
    val imdbID: String?,
    val Title: String?,
    val Year: String?,
    val Plot: String?,
    val Poster: String?,
    val Rated: String?,
    val Runtime: String?,
    val Director: String?,
    val Actors: String?,
    val Language: String?,
    val Country: String?,
    val Type: String?
) : Parcelable {

    // Constructor that receives a Parcel and use it to initialize their fields
    constructor(parcel: Parcel) : this(
        imdbID = parcel.readString(),
        Title = parcel.readString(),
        Year = parcel.readString(),
        Plot = parcel.readString(),
        Poster = parcel.readString(),
        Rated = parcel.readString(),
        Runtime = parcel.readString(),
        Director = parcel.readString(),
        Actors = parcel.readString(),
        Language = parcel.readString(),
        Country = parcel.readString(),
        Type = parcel.readString()
    )

    // Method implementation that writes the content of this object to the Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imdbID)
        parcel.writeString(Title)
        parcel.writeString(Year)
        parcel.writeString(Plot)
        parcel.writeString(Poster)
        parcel.writeString(Rated)
        parcel.writeString(Runtime)
        parcel.writeString(Director)
        parcel.writeString(Actors)
        parcel.writeString(Language)
        parcel.writeString(Country)
        parcel.writeString(Type)
    }

    override fun describeContents(): Int {
        return 0
    }

    val CREATOR = object : Parcelable.Creator<FilmRaw> {
        override fun createFromParcel(source: Parcel): FilmRaw {
            return FilmRaw(source)
        }

        override fun newArray(size: Int): Array<FilmRaw?> {
            return arrayOfNulls(size)
        }
    }

    // Alternative implementation using companion object
    /*companion object CREATOR : Parcelable.Creator<FilmRaw> {
        override fun createFromParcel(parcel: Parcel): FilmRaw {
            return FilmRaw(parcel)
        }

        override fun newArray(size: Int): Array<FilmRaw?> {
            return arrayOfNulls(size)
        }
    }*/

}
