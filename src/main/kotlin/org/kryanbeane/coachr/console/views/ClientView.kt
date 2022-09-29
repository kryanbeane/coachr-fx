package org.kryanbeane.coachr.console.views

import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.ExerciseModel
import org.kryanbeane.coachr.console.models.WorkoutModel

class ClientView {

    /**
     * main menu with add clients, client menu
     *
     * @return user option
     */
    fun mainMenuView(): Int {
        println(
            "\n" +
                    "░█████╗░░█████╗░░█████╗░░█████╗░██╗░░██╗██████╗░\n" +
                    "██╔══██╗██╔══██╗██╔══██╗██╔══██╗██║░░██║██╔══██╗\n" +
                    "██║░░╚═╝██║░░██║███████║██║░░╚═╝███████║██████╔╝\n" +
                    "██║░░██╗██║░░██║██╔══██║██║░░██╗██╔══██║██╔══██╗\n" +
                    "╚█████╔╝╚█████╔╝██║░░██║╚█████╔╝██║░░██║██║░░██║\n" +
                    "░╚════╝░░╚════╝░╚═╝░░╚═╝░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝" +
                    "\n"

        )
        println()
        println("M A I N   M E N U")
        println()
        println(
            "1. Add a Client" + "\n" +
            "2. Client Menu" + "\n" +
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
     * client menu with view clients, edit clients, delete clients
     *
     * @return user option
     */
    fun clientMenuView(): Int {
        println()
        println("C L I E N T   M E N U")
        println()
        println(
            "1. View Clients" + "\n" +
            "2. Edit a Client" + "\n" +
            "3. Delete a Client" + "\n" +
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
     * view clients menu with view all clients, view workout, view clients workouts
     *
     * @return user option
     */
    fun viewClientsMenuView(): Int {
        println()
        println("V I E W   C L I E N T   M E N U")
        println()
        println(
            "1. View All Clients" + "\n" +
            "2. View a Workout" + "\n" +
            "3. View a Client's Workouts" + "\n" +
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
     * edit client menu with edit client details, edit client workout
     *
     * @return user option
     */
    fun editClientMenuView(): Int {
        println()
        println("E D I T   C L I E N T   M E N U")
        println()
        println(
            "1. Edit Client Details" + "\n" +
            "2. Edit Client Workout" + "\n" +
            "3. Go Back" + "\n" +
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

    fun searchOrListMenu(): Int {
        println()
        println("S E A R C H   O R   L I S T")
        println()
        println(
            "1. Search By Name" + "\n" +
            "2. List All" + "\n" +
            "3. Go Back" + "\n" +
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

    fun listClients(clients: ClientMemStore) {
        println("Listing All Clients" + "\n" + "\n")
        clients.logClients()
        println()
    }

    fun clientDetailsAreValid(newClient: ClientModel): Boolean {
        println()
        println("A D D I N G   N E W   C L I E N T")
        println()
        println("Enter Client Name: ")
        newClient.fullName = readLine()!!
        println("Enter Client Email Address: ")
        newClient.emailAddress = readLine()!!
        println("Enter Client Phone Number: ")
        newClient.phoneNumber = readLine()!!.toLong()

        return newClient.fullName.isNotEmpty() && newClient.emailAddress.isNotEmpty() && newClient.phoneNumber.toString().isNotEmpty()
    }

    fun clientNameIsValid(client: ClientModel): Boolean {
        println()
        print("Enter Client Name: ")
        client.fullName = readLine()!!

        return client.fullName.isNotEmpty()
    }

}