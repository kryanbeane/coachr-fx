package org.kryanbeane.coachr.console.models

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import java.util.*
import kotlin.collections.ArrayList

data class ClientModel(
    var id: UUID = UUID.randomUUID(),
    var fullName: String = "",
    var phoneNumber: PhoneNumber = PhoneNumber(),
    var emailAddress: String = "",
    var workoutPlan: ArrayList<WorkoutModel> = arrayListOf()
)