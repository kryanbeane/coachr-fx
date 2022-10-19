package org.kryanbeane.coachr.console.views.old

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
            "4. View Exercises" + "\n" +
            "5. Go Back" + "\n" +
            "0. Exit" + "\n" + "\n" +
            "Enter an option: "
        )
        val input: String? = readLine()!!
        return if (input?.toIntOrNull() != null && input.isNotEmpty())
            input.toInt()
        else
            -9
    }

    /**
     * validate exercise details fro user input
     *
     * @param newExercise exercise object
     * @return true if valid, false if not
     */
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

    /**
     * update exercise details based on user input
     *
     * @param exercise exercise object
     */
    fun updateExerciseDetails(exercise: ExerciseModel): ExerciseModel {
        println(
            "\n" + "\n" +
                    "█░█ █▀█ █▀▄ ▄▀█ ▀█▀ █▀▀   █▀▀ ▀▄▀ █▀▀ █▀█ █▀▀ █ █▀ █▀▀   █▀▄ █▀▀ ▀█▀ ▄▀█ █ █░░ █▀\n" +
                    "█▄█ █▀▀ █▄▀ █▀█ ░█░ ██▄   ██▄ █░█ ██▄ █▀▄ █▄▄ █ ▄█ ██▄   █▄▀ ██▄ ░█░ █▀█ █ █▄▄ ▄█" + "\n"
        )

        println("\n" + "Current Exercise Name: " + exercise.name)
        println("Enter New Exercise Name: ")
        val name = readLine()!!
        if (name.isNotEmpty())
            exercise.name = name
        else
            println("Exercise Name ${exercise.name} Unchanged" + "\n")

        println("\n" + "Current Exercise Description: " + exercise.description)
        println("Enter New Exercise Description: ")
        val desc = readLine()!!
        if (desc.isNotEmpty())
            exercise.description = desc
        else
            println("Exercise Description Unchanged")

        println("\n" + "Current Exercise Sets: " + exercise.sets)
        println("Enter New Exercise Sets: ")
        val sets = readLine()!!
        if (sets.isNotEmpty())
            exercise.sets = sets.toInt()
        else
            println("Exercise Sets ${exercise.sets} Unchanged" + "\n")

        println("\n" + "Current Exercise Reps: " + exercise.sets)
        println("Enter New Exercise Reps: ")
        val reps = readLine()!!
        if (reps.isNotEmpty())
            exercise.sets = reps.toInt()
        else
            println("Exercise Reps ${exercise.reps} Unchanged" + "\n")

        println("\n" + "Current Exercise Reps in Reserve: " + exercise.repsInReserve)
        println("Enter New Exercise Reps in Reserve: ")
        val rir = readLine()!!
        if (rir.isNotEmpty())
            exercise.sets = rir.toInt()
        else
            println("Exercise Reps in Reserve ${exercise.repsInReserve} Unchanged" + "\n")
        println()
        println("Exercise ${exercise.name} Updated")
        return exercise
    }

    /**
     * validate exercise name from user input
     *
     * @param exercise exercise object
     * @return true if valid, false if not
     */
    fun exerciseNameIsValid(exercise: ExerciseModel): Boolean {
        println()
        println("Enter Exercise Name: ")
        exercise.name = readLine()!!

        return exercise.name.isNotEmpty()
    }
}