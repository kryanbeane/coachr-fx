package org.kryanbeane.coachr.console.controllers

import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.ExerciseModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import org.kryanbeane.coachr.console.views.ExerciseView
import kotlin.system.exitProcess

class ExerciseController(workoutController: WorkoutController) {
    private var logger = KotlinLogging.logger{}
    var ctrlr = workoutController
    var exerciseView = ExerciseView()
    var clientView = ctrlr.clientView
    private var clients = ctrlr.clients

    fun editWorkout(client: ClientModel, workout: WorkoutModel) {
        var input: Int
        do {
            input = exerciseView.editWorkoutMenuView()
            when(input) {
                1 -> {
                    println("Create Exercise")
                    createNewExercise(workout)
                }
                2 -> println("Edit an Exercise")
                3 -> println("Delete an Exercise")
                4 -> ctrlr.createWorkout(client)
                0 -> println("Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    private fun createNewExercise(workout: WorkoutModel) {
        val newExercise = ExerciseModel()
        if(exerciseView.newExerciseDetailsAreValid(newExercise)) {
            clients.createExercise(workout, newExercise)
            logger.info("Exercise Added: ${newExercise.name}")
        }
        else
            logger.error("Invalid Exercise Details, please try again")
    }
}