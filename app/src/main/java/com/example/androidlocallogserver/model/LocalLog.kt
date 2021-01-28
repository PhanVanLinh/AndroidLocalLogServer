package com.example.androidlocallogserver.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class LocalLog(val type: String, val message: String) : Parcelable {

}