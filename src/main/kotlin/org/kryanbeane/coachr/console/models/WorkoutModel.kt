package org.kryanbeane.coachr.console.models

import java.util.*
import kotlin.collections.ArrayList

data class WorkoutModel(
    var _id: UUID = UUID.randomUUID(),
    var name: String = "",
    var type: String = "",
    var exercises: ArrayList<ExerciseModel> = arrayListOf()
)