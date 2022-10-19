package org.kryanbeane.coachr.console.views.old

import org.kryanbeane.coachr.console.models.WorkoutModel

class WorkoutView {

    /**
     * edit workout plan menu with add workout, edit workout, delete workout
     *
     * @return user option
     */
    fun editWorkoutPlanMenuView(): Int {
        println()
        println(
            "\n" +
                    "█▀▀ █▀▄ █ ▀█▀   █░█░█ █▀█ █▀█ █▄▀ █▀█ █░█ ▀█▀   █▀█ █░░ ▄▀█ █▄░█   █▀▄▀█ █▀▀ █▄░█ █░█\n" +
                    "██▄ █▄▀ █ ░█░   ▀▄▀▄▀ █▄█ █▀▄ █░█ █▄█ █▄█ ░█░   █▀▀ █▄▄ █▀█ █░▀█   █░▀░█ ██▄ █░▀█ █▄█"
        )
        println()
        println(
            "1. Create a Workout" + "\n" +
            "2. Update Workout Details" + "\n" +
            "3. Edit a Workout" + "\n" +
            "4. Delete a Workout" + "\n" +
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
     * validate details of a workout, change param workout to new input details
     *
     * @param newWorkout
     * @return true if valid, false if not
     */
    fun newWorkoutDetailsAreValid(newWorkout: WorkoutModel): Boolean {
        println()
        println("Enter Workout Name: ")
        newWorkout.name = readLine()!!

        println("Enter Workout Type: ")
        newWorkout.type = readLine()!!

        return newWorkout.name.isNotEmpty() || newWorkout.type.isNotEmpty()
    }

    /**
     * validate workout name based on user input
     *
     * @param workout to be validated
     * @return true if valid, false if not
     */
    fun workoutNameIsValid(workout: WorkoutModel): Boolean {
        println()
        println("Enter Workout Name: ")
        workout.name = readLine()!!

        return workout.name.isNotEmpty()
    }

    /**
     * update workout details based on user input
     *
     * @param workout to be updated
     */
    fun updateWorkoutDetails(workout: WorkoutModel): WorkoutModel {
        println()
        println(
            "\n" +
                    "█░█ █▀█ █▀▄ ▄▀█ ▀█▀ █▀▀   █░█░█ █▀█ █▀█ █▄▀ █▀█ █░█ ▀█▀   █▀▄ █▀▀ ▀█▀ ▄▀█ █ █░░ █▀\n" +
                    "█▄█ █▀▀ █▄▀ █▀█ ░█░ ██▄   ▀▄▀▄▀ █▄█ █▀▄ █░█ █▄█ █▄█ ░█░   █▄▀ ██▄ ░█░ █▀█ █ █▄▄ ▄█"
        )
        println()

        println("Current Workout Name: ${workout.name}")
        println("Enter New Workout Name: ")
        val name = readLine()!!
        if (name.isNotEmpty())
            workout.name = name
        else
            println("Workout Name ${workout.name} Unchanged")
        println()

        println("Current Workout Type: ${workout.type}")
        println("Enter New Workout Type: ")
        val type = readLine()!!
        if (type.isNotEmpty())
            workout.type = type
        else
            println("Workout Type ${workout.type} Unchanged")
        println()
        println("Client ${workout.name} Updated")
        return workout
    }
}