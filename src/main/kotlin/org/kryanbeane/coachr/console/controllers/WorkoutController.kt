package org.kryanbeane.coachr.console.controllers

import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import org.kryanbeane.coachr.console.views.WorkoutView
import kotlin.system.exitProcess

class WorkoutController(private var clientCtrlr: ClientController) {
    private var exerciseController = ExerciseController(clientCtrlr, this)
    private var logger = KotlinLogging.logger{}
    private var workoutView = WorkoutView()
    private var clients = clientCtrlr.clients

    /**
     * menu to allow users to create a new workout, update workout details, edit a workout, delete a workout, or view workouts
     *
     * @param client who owns the workout
     */
    fun editWorkoutPlanMenu(client: ClientModel) {
        var input: Int
        do {
            input = workoutView.editWorkoutPlanMenuView()
            when(input) {
                1 -> createNewWorkout(client)
                2 -> {
                    val workout = setCurrentWorkoutMenu(client)
                    if(workout != null)
                        workoutView.updateWorkoutDetails(workout)
                    else
                        println("No Workout Selected")
                }
                3 -> {
                    val workout = setCurrentWorkoutMenu(client)
                    if (workout != null)
                        exerciseController.editWorkoutMenu(client, workout)
                    else
                        println("No Workout Selected")
                }
                4 -> {
                    val workout = setCurrentWorkoutMenu(client)
                    if (workout != null)
                        deleteWorkout(client, workout)
                    else
                        println("No Workout Selected")

                }
                5 -> clientCtrlr.updateClientMenu(client)
                0 -> println("\n" + "Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    /**
     * menu to allow users to search for a workout by listing all workouts or searching by workout name
     *
     * @param client who owns the workout
     * @return found workout or null
     */
    private fun setCurrentWorkoutMenu(client: ClientModel): WorkoutModel? {
        return when(clientCtrlr.clientView.searchOrListMenu()) {
            1 -> searchForWorkout(client, false)
            2 -> searchForWorkout(client, true)
            else -> {
                println("Invalid Option")
                return null
            }
        }
    }

    /**
     * allows users to create a net workout
     *
     * @param client who owns the workout plan
     */
    private fun createNewWorkout(client: ClientModel) {
        val newWorkout = WorkoutModel()
        if(workoutView.newWorkoutDetailsAreValid(newWorkout)) {
            clients.createClientWorkout(client, newWorkout)
            logger.info("Client Added: ${newWorkout.name}")
        }
        else
            logger.error("Invalid Client Details, please try again")
    }

    /**
     * allows users to delete a workout
     *
     * @param client who owns the workout plan
     * @param workout to delete
     */
    private fun deleteWorkout(client: ClientModel, workout: WorkoutModel) {
        clients.deleteWorkout(client, workout)
        if(clients.findWorkout(client.fullName, workout.name) == null)
            logger.info("Workout Successfully Deleted: ${workout.name}")
        else
            logger.error("Workout Deletion Failed, Please Try Again")
    }

    /**
     * allows users to search for a workout by name or by listing
     *
     * @param client who owns the workout plan
     * @param listWorkouts if true, list all workouts, else search for a workout by name
     * @return found workout or null
     */
    private fun searchForWorkout(client: ClientModel, listWorkouts: Boolean): WorkoutModel? {
        val foundWorkout = WorkoutModel()
        if(listWorkouts)
            clients.logWorkoutNames(client)
        if(workoutView.workoutNameIsValid(foundWorkout)) {
            val selectedWorkout = clients.findWorkout(client.fullName, foundWorkout.name)
            return if(selectedWorkout != null) {
                logger.info("Workout Selected: ${selectedWorkout.name}")
                selectedWorkout
            } else {
                logger.error("Workout not found")
                null
            }
        }
        else
            logger.error("Invalid Workout Name, please try again")
            return null
    }

}