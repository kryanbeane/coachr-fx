package org.kryanbeane.coachr.console.models

import java.util.*
import kotlin.collections.ArrayList

data class ClientModel(
    var id: UUID = UUID.randomUUID(),
    var fullName: String = "",
    var phoneNumber: Long = 0,
    var emailAddress: String = "",
    var workoutPlan: ArrayList<WorkoutModel> = arrayListOf()
)