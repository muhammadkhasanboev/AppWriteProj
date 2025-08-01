package io.appwrite.starterkit.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val id: Int, val name: String) : Parcelable