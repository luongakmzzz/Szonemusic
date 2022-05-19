package com.example.test_main_music_app.Model

import android.accounts.AuthenticatorDescription
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.concurrent.TimeUnit

@Parcelize
data class AllCategory(
    var musicid: String? = null,
    var namemusic: String? = null,
    var author: String? = null,
    var imageUrl: String? = null,
    var musicUrl: String? = null,
    var description: String? = null,
    var durationtime: Long = 0,
    var uid: String? = null
) : Parcelable

