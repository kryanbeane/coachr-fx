package org.kryanbeane.coachr.console.models

import java.util.*

data class ExerciseModel(
    var id: UUID = UUID.randomUUID(),
    var name: String = "",
    var description: String = "",
    var sets: Int = 0,
    var reps: Int = 0,
    var repsInReserve: Int = 0
)