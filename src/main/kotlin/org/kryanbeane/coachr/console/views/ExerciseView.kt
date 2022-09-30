package org.kryanbeane.coachr.console.views

import org.kryanbeane.coachr.console.models.ExerciseModel

class ExerciseView {

    /**
     * edit workout menu with add exercise to workout, edit exercise, delete exercise
     *
     * @return user option
     */
    fun editWorkoutMenuView(): Int {
        println()
        println(
            "\n" +
                    "█▀▀ █▀▄ █ ▀█▀   █░█░█ █▀█ █▀█ █▄▀ █▀█ █░█ ▀█▀   █▀▄▀█ █▀▀ █▄░█ █░█\n" +
                    "██▄ █▄▀ █ ░█░   ▀▄▀▄▀ █▄█ █▀▄ █░█ █▄█ █▄█ ░█░   █░▀░█ ██▄ █░▀█ █▄█"
        )
        println()
        println(
            "1. Add Exercise to Workout" + "\n" +
            "2. Edit an Exercise" + "\n" +
            "3. Delete an Exercise" + "\n" +
            "4. Go Back" + "\n" +
            "0. Exit" + "\n" + "\n" +
            "Enter an option: "
        )
        val input: String? = readLine()!!
        return if (input?.toIntOrNull() != null && input.isNotEmpty())
            input.toInt()
        else
            -9
    }

    fun newExerciseDetailsAreValid(newExercise: ExerciseModel): Boolean {
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