package org.kryanbeane.coachr.console.controllers.old

import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.ExerciseModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import org.kryanbeane.coachr.console.views.old.ExerciseView
import kotlin.system.exitProcess

class ExerciseController(private var clientCtrlr: ClientController, private var workoutCtrlr: WorkoutController) {
    private var clients = clientCtrlr.clients
    private var logger = KotlinLogging.logger{}
    private var exerciseView = ExerciseView()

    /**
     * menu to allow user to add exercises to a workout, edit an exercise, delete an exercise, or view exercises
     *
     * @param client who owns the workout
     * @param workout to add exercises to
     */
    fun editWorkoutMenu(client: ClientModel, workout: WorkoutModel) {
        var input: Int
        do {
            input = exerciseView.editWorkoutMenuView()
            when(input) {
                1 -> createNewExercise(client, workout)
                2 -> {
                    val exercise = setCurrentExerciseMenu(client, workout)
                    if (exercise != null)
                        updateExerciseDetails(client, workout, exercise)
                    else
                        println("No Exercise Selected")
                }
                3 -> {
                    val exercise = setCurrentExerciseMenu(client, workout)
                    if (exercise != null)
                        deleteExercise(client, workout, exercise)
                    else
                        println("No Exercise Selected")
                }
                4 -> clients.logExercises(workout)
                5 -> workoutCtrlr.editWorkoutPlanMenu(client)
                0 -> println("\n" + "Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    /**
     * menu to allow user to select an exercise from a workout
     *
     * @param client who owns the workout
     * @param workout to search for exercise in
     * @return found exercise or null
     */
    private fun setCurrentExerciseMenu(client: ClientModel, workout: WorkoutModel): ExerciseModel? {
        return when(clientCtrlr.clientView.searchOrListMenu()) {
            1 -> searchForExercise(client, workout, false)
            2 -> searchForExercise(client, workout, true)
            else -> {
                println("Invalid Option")
                return null
            }
        }
    }

    /**
     * allows users to create a new exercise and add it to workout parameter
     *
     * @param workout to add exercise to
     */
    private fun createNewExercise(client: ClientModel, workout: WorkoutModel) {
        val newExercise = ExerciseModel()
        if (exerciseView.newExerciseDetailsAreValid(newExercise)) {

            if (clients.findExercise(client.fullName, workout.name, newExercise.name) == null) {
                val successful = clients.createExercise(client, workout, newExercise)
                if (!successful)
                    logger.error("Error adding new exercise to database")
                else
                    logger.info("Exercise ${newExercise.name} added to database")
            } else
                logger.error("Exercise ${newExercise.name} already exists")

        } else
            logger.error("Invalid exercise details, please try again")
    }

    private fun updateExerciseDetails(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel) {
        val oldExerciseState = exercise.copy()
        val newExerciseState = exerciseView.updateExerciseDetails(exercise)
        val updated = clients.updateExercise(client, workout, oldExerciseState, newExerciseState)
        if (updated)
            logger.info("Exercise ${exercise.name} details successfully updated")
        else
            logger.error("Failed to update exercise details, please try again")
    }

    /**
     * allows user to delete an exercise from a workout
     *
     * @param client who owns the workout
     * @param workout in which the exercise resides
     * @param exercise to delete
     */
    private fun deleteExercise(client: ClientModel, workout: WorkoutModel, exercise: ExerciseModel) {
        val success = clients.deleteExercise(client, workout, exercise)
        val foundExercise = clients.findExercise(client.fullName, workout.name, exercise.name)
        if (!success || foundExercise != null)
            logger.error("Error deleting exercise")
        else
            logger.info("Workout ${workout.name} deleted")
    }

    /**
     * allows user to select an exercise from a workout
     *
     * @param client who owns the workout
     * @param workout to search for exercise in
     * @param listExercises boolean to list exercises or not
     * @return found exercise or null
     */
    private fun searchForExercise(client: ClientModel, workout: WorkoutModel, listExercises: Boolean): ExerciseModel? {
        val foundExercise = ExerciseModel()
        if(listExercises)
            clients.logExerciseNames(workout)
        if(exerciseView.exerciseNameIsValid(foundExercise)) {
            val selectedExercise = clients.findExercise(client.fullName, workout.name, foundExercise.name)
            return if(selectedExercise != null) {
                logger.info("Exercise Selected: ${selectedExercise.name}")
                selectedExercise
            } else {
                logger.error("Exercise not found")
                null
            }
        }
        else
            logger.error("Invalid Exercise Name, please try again")
        return null
    }

}