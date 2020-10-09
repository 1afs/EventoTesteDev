package com.example.eventotestedev.activity.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel(
    @SerializedName("people") val pessoas : List<People>,
    @SerializedName("description") val descricao : String,
    @SerializedName("title") val titulo : String,
    @SerializedName("image") val imagem : String,
    @SerializedName("price") val valor : String,
    @SerializedName("date") val data : Int,
    @SerializedName("longitude") val longitude : Float,
    @SerializedName("latitude") val latitude : Float,
    @SerializedName("id") val id : Short,

    ) : Parcelable

@Parcelize
data class People(
    @SerializedName("picture") val foto : String,
    @SerializedName("name") val nome : String,
    @SerializedName("eventId") val evento : Int,
    @SerializedName("id") val id : Int,

    ) : Parcelable