package com.aliasadi.clean.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey(autoGenerate = false)
        @SerializedName("id")
        @ColumnInfo(name = "id")
        val id: Int,

        @Expose
        @SerializedName("description")
        @ColumnInfo(name = "description")
        val description: String,

        @Expose
        @SerializedName("image")
        @ColumnInfo(name = "image")
        val image: String,

        @Expose
        @SerializedName("title")
        @ColumnInfo(name = "title")
        val title: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}