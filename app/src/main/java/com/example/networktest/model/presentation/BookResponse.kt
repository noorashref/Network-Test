package com.example.networktest.model.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Serialicable vs parcelable
 *
 * Serializable its defined in java.lannf=g
 * Serializable is not customizable
 * Serializable uses reflection to decompose / recreate the object
 * Serializable a lot of temporary objects.
 *
 *
 * Parcelable its defined in Android framwork
 * it is customizable
 * it doen't use reflaction
 *
 *
 * Marshall and unMarshall.
 *
 */


@Parcelize
data class BookResponse(
    val items: List<BookItem>
) : Parcelable


@Parcelize
data class BookItem(
    val volumeInfo: VolumeItem
) : Parcelable

@Parcelize
data class VolumeItem(
    val title: String,
    val authors: List<String>,
    val imageLinks : ImageItem
) : Parcelable

@Parcelize
data class ImageItem(
    val thumbnail: String
) : Parcelable



