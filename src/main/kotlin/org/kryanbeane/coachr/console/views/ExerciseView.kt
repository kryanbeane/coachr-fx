package org.kryanbeane.coachr.console.views

import org.kryanbeane.coachr.console.models.ExerciseModel

class ExerciseView {

    fun exerciseDetailsAreValid(newExercise: ExerciseModel): Boolean {
        println()
        println("Enter Exercise Name: ")
        newExercise.name = readLine()!!
        println("Enter Exercise Description: ")
        newExercise.description = readLine()!!
        println("Enter Exercise Sets: ")
        newExercise.sets = readLine()!!.toInt()
        println("Enter Exercise Reps: ")
        newExercise.reps = readLine()!!.toInt()
        println("Enter Desired Reps In Reserve: ")
        newExercise.repsInReserve = readLine()!!.toInt()

        return newExercise.name.isNotEmpty() &&
                newExercise.description.isNotEmpty() &&
                newExercise.sets.toString().isNotEmpty() &&
                newExercise.reps.toString().isNotEmpty() &&
                newExercise.repsInReserve.toString().isNotEmpty()
    }
}