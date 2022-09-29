package org.kryanbeane.coachr.console.controllers

import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.ExerciseModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import org.kryanbeane.coachr.console.views.ClientView

class ClientController {
    var clients = ClientMemStore()
    var clientView = ClientView()
    private var logger = KotlinLogging.logger{}
    var currClient: ClientModel? = null
    var workoutController = WorkoutController(this)

    init {
        logger.info("Launching Coachr Console Application")
        println("Coachr App Version 1.0")
    }

    fun start() {
        populateDummyClients()
        var input: Int
        do {
            input = clientView.mainMenuView()
            when(input) {
                1 -> addNewClient()
                2 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info("Shutting Down Coachr")
    }

    fun clientMenu() {
        currClient = null
        var input: Int
        do {
            input = clientView.clientMenuView()
            when(input) {
                1 -> viewClients()
                2 -> editClients()
                3 -> chooseClientToDelete()
                4 -> start()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info("Shutting Down Coachr")
    }

    fun viewClients() {
        var input: Int
        do {
            input = clientView.viewClientsMenuView()
            when(input) {
                1 -> clientView.listClients(clients)
                2 -> println()
                3 -> println()
                4 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        System.exit(0)
        logger.info("Shutting Down Coachr")
    }

    fun editClients() {
        var input: Int
        do {
            input = clientView.editClientMenuView()
            when(input) {
                1 -> println()
                2 -> workoutController.editWorkoutPlan()
                3 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info("Shutting Down Coachr")
    }

    fun chooseClientToDelete() {
        var input: Int
        do {
            input = clientView.searchOrListMenu()
            when(input) {
                1 -> searchClientForSelection()
                2 -> listClientsForSelection()
                3 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
            // Check that currClient has been assigned a client object before deleting and resetting curr client
            if(currClient != null) {
                clients.deleteClient(currClient!!)
                currClient = null
            }
        } while (input != 0)
        logger.info("Shutting Down Coachr")
    }

    private fun addNewClient() {
        val newClient = ClientModel()
        if(clientView.clientDetailsAreValid(newClient)) {
            clients.createClient(newClient)
            logger.info("Client Added : [ ${newClient.fullName} ]")
        }
        else
            logger.error("Invalid Client Details, please try again")
    }

    private fun searchClientForSelection() {
        val foundClient = ClientModel()
        if(clientView.clientNameIsValid(foundClient)) {
            currClient = clients.findClient(foundClient.fullName)
            logger.info("Client ${currClient!!.fullName} Selected")
        }
        else
            logger.error("Invalid Client Name, please try again")
    }

    private fun listClientsForSelection() {
        val foundClient = ClientModel()
        clients.logClientNames()
        if(clientView.clientNameIsValid(foundClient)) {
            currClient = clients.findClient(foundClient.fullName)
            logger.info("Client ${currClient!!.fullName} Selected")
        }
        else
            logger.error("Invalid Client Name, please try again")
    }

    private fun populateDummyClients() {
        clients.createClient(
            ClientModel(
                fullName = "Dominik",
                phoneNumber = "0834232123".toLong(),
                emailAddress = "dominil@gmail.com",
                workoutPlan = arrayListOf(
                    WorkoutModel(
                        name = "Push A",
                        type = "Chest, Shoulders & Triceps",
                        exercises = arrayListOf(
                            ExerciseModel(
                                name = "Bench Press",
                                description = "Retract scapulae, arch your back and use leg drive. Maintain stability by engaging your lats throughout",
                                sets = 5,
                                reps = 8,
                                repsInReserve = 1
                            ),
                            ExerciseModel(
                                name = "Overhead Press",
                                description = "Brace your core, squeeze your glutes, and drive the bar upwards",
                                sets = 5,
                                reps = 15,
                                repsInReserve = 2
                            )
                        )
                    ),
                    WorkoutModel(
                        name = "Legs A",
                        type = "Quads, Hamstrings and Calves",
                        exercises = arrayListOf(
                            ExerciseModel(
                                name = "Squat",
                                description = "Rest the bar on your traps, brace your core, and begin by hinging at the hips and squatting bellow parallel before squatting the weight upwards",
                                sets = 5,
                                reps = 5,
                                repsInReserve = 8
                            )
                        )
                    )
                )
            )
        )
        clients.createClient(
            ClientModel(
                fullName = "Denis",
                phoneNumber = "08512121212".toLong(),
                emailAddress = "denis@gmail.com",
                workoutPlan = arrayListOf(
                    WorkoutModel(
                        name = "Upper Body",
                        type = "Chest, Shoulders & Back",
                        exercises = arrayListOf(
                            ExerciseModel(
                                name = "Seated High Row",
                                description = "Sit on bench, brace and pull elbow towards your back pocket to engage the iliac(lower) lat",
                                sets = 5,
                                reps = 8,
                                repsInReserve = 1
                            ),
                        )
                    ),
                )
            )
        )
    }

}