package org.kryanbeane.coachr.console.controllers

import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.ExerciseModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import org.kryanbeane.coachr.console.views.ClientView
import kotlin.system.exitProcess

class ClientController {
    var clients = ClientMemStore()
    var clientView = ClientView()
    private var logger = KotlinLogging.logger{}
    var workoutController = WorkoutController(this)

    init {
        logger.info("Launching Coachr Console Application")
        println("Coachr App Version 1.0")
    }

    private fun initObjects() {
        populateDummyClients()
    }

    fun start() {
        initObjects()
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
        do {
            val input = clientView.clientMenuView()
            when(input) {
                1 -> viewClients()
                2 -> {
                    val client = setCurrentClient()
                    if (client != null)
                        updateClient(client)
                    else
                        println("No Client Selected")
                }
                3 -> println("Delete a Client")
                4 -> start()
                0 -> println("Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    fun viewClients() {
        do {
            val input = clientView.viewClientsMenuView()
            when(input) {
                1 -> clientView.listClients(clients)
                2 -> println("View a Workout")
                3 -> println("View a Client's Workouts")
                4 -> clientMenu()
                0 -> println("Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    fun updateClient(client: ClientModel) {
        var input: Int
        do {
            input = clientView.editClientMenuView()
            when(input) {
                1 -> clientView.updateClientDetails(client)
                2 -> workoutController.createWorkout(client)
                3 -> clientMenu()
                0 -> println("Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    fun setCurrentClient(): ClientModel? {
        return when(clientView.searchOrListMenu()) {
            1 -> searchForClient(false)
            2 -> searchForClient(true)
            else -> {
                println("Invalid Option")
                return null
            }
        }
    }

    private fun addNewClient() {
        val newClient = ClientModel()
        if(clientView.newClientDetailsAreValid(newClient)) {
            clients.createClient(newClient)
            logger.info("Client Added: ${newClient.fullName}")
        }
        else
            logger.error("Invalid Client Details, please try again")
    }

    private fun searchForClient(listClients: Boolean): ClientModel? {
        val foundClient = ClientModel()
        if(listClients) clients.logClientNames()
        if(clientView.clientNameIsValid(foundClient)) {
            val selectedClient = clients.findClient(foundClient.fullName)
            return if(selectedClient != null) {
                logger.info("Client Selected: ${selectedClient.fullName}")
                selectedClient
            } else {
                logger.error("Client not found")
                null
            }
        }
        else
            logger.error("Invalid Client Name, please try again")
            return null
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