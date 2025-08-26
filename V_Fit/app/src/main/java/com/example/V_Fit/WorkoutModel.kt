package com.example.V_Fit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel(
    val workoutID: Int,
    val workoutType: String,
    val workoutDate: String,
    val workoutDay: Int,
    val workoutMonth: Int,
    val workoutYear: Int,
    val workoutTime: String,
    val indoorOutdoor: String,
    val equipments: String,
    val distance: Double,
    val duration: Int,
    val workoutWeight: Double,
    val remark: String,
    val userID: Int?
) : Parcelable