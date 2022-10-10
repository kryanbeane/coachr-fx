package org.kryanbeane.coachr.console.controllers

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.Dotenv
import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.*
import org.kryanbeane.coachr.console.views.ClientView
import kotlin.system.exitProcess

class ClientController {
    private var logger = KotlinLogging.logger{}
    var clients = ClientJSONStore()
    var clientView = ClientView()
    private var workoutController = WorkoutController(this)

    init {
        logger.info("Launching Coachr Console Application")
        println("Coachr App v1.0.0")
    }

    /**
     * main menu, allows user to enter client menu, list all clients, or exit
     *
     */
    fun start() {
        var input: Int
        do {
            input = clientView.mainMenuView()
            when(input) {
                1 -> clientMenu()
                2 -> clients.logAll()
                0 -> println("\n" + "Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info("\n" + "Shutting Down Coachr")
    }

    /**
     * allows user to add, update, delete, list clients, go back and exit
     *
     */
    private fun clientMenu() {
        do {
            val input = clientView.clientMenuView()
            when(input) {
                1 -> addNewClient()
                2 -> {
                    val client = setCurrentClientMenu()
                    if (client != null)
                        updateClientMenu(client)
                    else
                        println("No Client Selected")
                }
                3 -> {
                    val client = setCurrentClientMenu()
                    if (client != null)
                        deleteClient(client)
                    else
                        println("No Client Selected")
                }
                4 -> clients.logClients()
                5 -> start()
                0 -> println("\n" + "Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    /**
     * allows user to update client parameter details, edit workout plan, view workouts, go back, and exit
     *
     * @param client
     */
    fun updateClientMenu(client: ClientModel) {
        var input: Int
        do {
            input = clientView.editClientMenuView()
            when(input) {
                1 -> clients.updateClientDetails(clientView.updateClientDetails(client))
                2 -> workoutController.editWorkoutPlanMenu(client)
                3 -> clients.logWorkouts(client)
                4 -> clientMenu()
                0 -> println("\n" + "Shutting Down Coachr")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        exitProcess(0)
    }

    /**
     * menu to allow users to select a client from a list or search for clients by name and returns found client or null
     *
     * @return ClientModel or Null
     */
    private fun setCurrentClientMenu(): ClientModel? {
        return when(clientView.searchOrListMenu()) {
            1 -> searchForClient(false)
            2 -> searchForClient(true)
            else -> {
                println("Invalid Option")
                return null
            }
        }
    }

    /**
     * allows user to create a new client
     *
     */
    private fun addNewClient() {
        val newClient = ClientModel()
        if(clientView.newClientDetailsAreValid(newClient)) {
            clients.createClient(newClient)
            logger.info("Client Added: ${newClient.fullName}")
        }
        else
            logger.error("Invalid Client Details, Please Try Again")
    }

    /**
     * allows user to delete the parameter client
     *
     * @param client to be deleted
     */
    private fun deleteClient(client: ClientModel) {
        clients.deleteClient(client)
        if(clients.findClientByName(client.fullName) == null)
            logger.info("Client Successfully Deleted: ${client.fullName}")
        else
            logger.error("Client Deletion Failed, Please Try Again")
    }

    /**
     * allows user to search for a client based on listing or searching
     *
     * @param listClients boolean option
     * @return found client or null
     */
    private fun searchForClient(listClients: Boolean): ClientModel? {
        val foundClient = ClientModel()
        if(listClients) clients.logClientNames()
        if(clientView.clientChoiceIsValid(foundClient)) {
            val selectedClient = clients.findClientByName(foundClient.fullName)
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
}