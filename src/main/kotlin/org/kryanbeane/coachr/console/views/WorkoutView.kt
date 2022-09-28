package org.kryanbeane.coachr.console.views

import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.WorkoutModel

class WorkoutView {

    /**
     * edit workout plan menu with add workout, edit workout, delete workout
     *
     * @return user option
     */
    fun editWorkoutPlanMenuView(): Int {
        println(
            "1. Add Workout to Plan" + "\n" +
            "2. Edit a Workout" + "\n" +
            "3. Delete a Workout" + "\n" +
            "4. Go Back" + "\n" +
            "0. Exit" + "\n" + "\n" +
            "Enter an option:" + "\n" +
            "\n"
        )
        val input: String? = readLine()!!
        return if (input?.toIntOrNull() != null && input.isNotEmpty())
            input.toInt()
        else
            -9
    }

    /**
     * edit workout menu with add exercise to workout, edit exercise, delete exercise
     *
     * @return user option
     */
    fun editWorkoutMenuView(): Int {
        println(
            "1. Add Exercise to Workout" + "\n" +
            "2. Edit an Exercise" + "\n" +
            "3. Delete an Exercise" + "\n" +
            "4. Go Back" + "\n" +
            "0. Exit" + "\n" + "\n" +
            "Enter an option:" + "\n" +
            "\n"
        )
        val input: String? = readLine()!!
        return if (input?.toIntOrNull() != null && input.isNotEmpty())
            input.toInt()
        else
            -9
    }

    /**
     * validate details of a workout, change param workout to new input details
     *
     * @param newWorkout
     * @return true if valid, false if not
     */
    fun workoutDetailsAreValid(newWorkout: WorkoutModel): Boolean {
        println()
        println("Enter Workout Name: ")
        newWorkout.name = readLine()!!
        println("Enter Workout Type: ")
        newWorkout.type = readLine()!!

        return newWorkout.name.isNotEmpty() && newWorkout.type.isNotEmpty()
    }

    fun workoutNameIsValid(workout: WorkoutModel): Boolean {
        println()
        print("Enter Workout Name: ")
        workout.name = readLine()!!

        return workout.name.isNotEmpty()
    }
}