package org.kryanbeane.coachr.console.controllers

import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import org.kryanbeane.coachr.console.views.WorkoutView

class WorkoutController(private var clientContr: ClientController) {
    private var logger = KotlinLogging.logger{}
    private var clients = clientContr.clients
    private var workoutView = WorkoutView()
    var workoutPlan = clientContr.currClient?.workoutPlan
    var currClient = clientContr.currClient
    var currWorkout: WorkoutModel? = null
    var clientView = clientContr.clientView

    fun editWorkoutPlan() {
        var input: Int
        do {
            input = workoutView.editWorkoutPlanMenuView()
            println(currClient)
            when(input) {
                1 -> addWorkoutToClient(currClient!!)
                2 -> editWorkout()
                3 -> println()
                4 -> println()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info("Shutting Down Coachr")
    }


    fun editWorkout() {
        var input: Int
        do {
            input = workoutView.editWorkoutMenuView()
            when(input) {
                1 -> println()
                2 -> println()
                3 -> println()
                4 -> editWorkoutPlan()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info("Shutting Down Coachr")
    }

    private fun addWorkoutToClient(client: ClientModel?) {
        if(client != null) {
            val newWorkout = WorkoutModel()
            if(workoutView.workoutDetailsAreValid(newWorkout)) {
                clients.createClientWorkout(client, newWorkout)
                logger.info("Workout Added: ${newWorkout.name}")
            }
            else
                logger.error("Invalid Workout Details, please try again")
        }
        else
            logger.error("No Client Selected")
    }

     fun viewWorkout() {
        var input: Int
        do {
            input = clientView.searchOrListMenu()
            when (input) {
                1 -> searchWorkoutForSelection()
                2 -> listWorkoutsForSelection()
                3 -> clientContr.viewClients()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
            if(currWorkout != null)
                workoutView.listWorkout(currWorkout!!)
        } while (input != 0)
        logger.info("Shutting Down Coachr")
    }

    private fun searchWorkoutForSelection() {
        val foundWorkout = WorkoutModel()
        if(workoutView.workoutNameIsValid(foundWorkout)) {
            currWorkout = clients.findWorkout(currClient!!.fullName, foundWorkout.name)
            logger.info("Workout ${currWorkout!!.name} Selected")
        }
        else
            logger.error("Invalid Workout Name, please try again")
    }

    private fun listWorkoutsForSelection() {
        val foundWorkout = WorkoutModel()
        clients.logWorkoutNames(currClient!!)
        if(workoutView.workoutNameIsValid(foundWorkout)) {
            currWorkout = clients.findWorkout(currClient!!.fullName, foundWorkout.name)
            logger.info("Client ${currWorkout!!.name} Selected")
        }
        else
            logger.error("Invalid Workout Name, please try again")
    }
}