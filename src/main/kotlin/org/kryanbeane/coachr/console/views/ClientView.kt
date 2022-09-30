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
        println()
        println(
            "\n" +
                    "█▀▄▀█ ▄▀█ █ █▄░█   █▀▄▀█ █▀▀ █▄░█ █░█\n" +
                    "█░▀░█ █▀█ █ █░▀█   █░▀░█ ██▄ █░▀█ █▄█"
        )
        println()
        println(
            "1. Add a Client" + "\n" +
            "2. Client Menu" + "\n" +
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
     * client menu with view clients, edit clients, delete clients
     *
     * @return user option
     */
    fun clientMenuView(): Int {
        println()
        println(
            "\n" +
                    "█▀▀ █░░ █ █▀▀ █▄░█ ▀█▀   █▀▄▀█ █▀▀ █▄░█ █░█\n" +
                    "█▄▄ █▄▄ █ ██▄ █░▀█ ░█░   █░▀░█ ██▄ █░▀█ █▄█"
        )
        println()
        println(
            "1. View Clients" + "\n" +
            "2. Update a Client" + "\n" +
            "3. Delete a Client" + "\n" +
            "4. Go Back" + "\n" +
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
     * view clients menu with view all clients, view workout, view clients workouts
     *
     * @return user option
     */
    fun viewClientsMenuView(): Int {
        println()
        println(
            "\n" +
                    "█░█ █ █▀▀ █░█░█   █▀▀ █░░ █ █▀▀ █▄░█ ▀█▀ █▀   █▀▄▀█ █▀▀ █▄░█ █░█\n" +
                    "▀▄▀ █ ██▄ ▀▄▀▄▀   █▄▄ █▄▄ █ ██▄ █░▀█ ░█░ ▄█   █░▀░█ ██▄ █░▀█ █▄█"
        )
        println()
        println(
            "1. View All Clients" + "\n" +
            "2. View a Workout" + "\n" +
            "3. View a Client's Workouts" + "\n" +
            "4. Go Back" + "\n" +
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
     * edit client menu with edit client details, edit client workout
     *
     * @return user option
     */
    fun editClientMenuView(): Int {
        println()
        println(
            "\n" +
                    "█░█ █▀█ █▀▄ ▄▀█ ▀█▀ █▀▀   █▀▀ █░░ █ █▀▀ █▄░█ ▀█▀   █▀▄▀█ █▀▀ █▄░█ █░█\n" +
                    "█▄█ █▀▀ █▄▀ █▀█ ░█░ ██▄   █▄▄ █▄▄ █ ██▄ █░▀█ ░█░   █░▀░█ ██▄ █░▀█ █▄█"
        )
        println()
        println(
            "1. Update Client Details" + "\n" +
            "2. Edit Client Workout Plan" + "\n" +
            "3. Go Back" + "\n" +
            "0. Exit" + "\n" + "\n" +
            "Enter an option: "
        )
        val input: String? = readLine()!!
        return if (input?.toIntOrNull() != null && input.isNotEmpty())
            input.toInt()
        else
            -9
    }

    fun searchOrListMenu(): Int {
        println()
        println(
            "\n" +
                    "█▀ █▀▀ ▄▀█ █▀█ █▀▀ █░█   █▀█ █▀█   █░░ █ █▀ ▀█▀\n" +
                    "▄█ ██▄ █▀█ █▀▄ █▄▄ █▀█   █▄█ █▀▄   █▄▄ █ ▄█ ░█░"
        )
        println()
        println(
            "1. Search By Name" + "\n" +
            "2. List All" + "\n" +
            "Enter an option: "
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

    fun newClientDetailsAreValid(newClient: ClientModel): Boolean {
        println(
            "\n" + "\n" +
                    "▄▀█ █▀▄ █▀▄   █▀▀ █░░ █ █▀▀ █▄░█ ▀█▀\n" +
                    "█▀█ █▄▀ █▄▀   █▄▄ █▄▄ █ ██▄ █░▀█ ░█░" + "\n"
        )
        println("Enter Client Name: ")
        newClient.fullName = readLine()!!

        println("Enter Client Email Address: ")
        newClient.emailAddress = readLine()!!

        // Workaround for empty phone number strings cannot be converted to a long
        println("Enter Client Phone Number: ")
        val number = readLine()!!
        if(number != "")
            newClient.phoneNumber = number.toLong()
        else {
            println("Invalid Phone Number")
            return false
        }

        return newClient.fullName.isNotEmpty() && newClient.emailAddress.isNotEmpty() && newClient.phoneNumber.toString().isNotEmpty()
    }

    fun clientNameIsValid(client: ClientModel): Boolean {
        print("\n" + "Enter Client Name: ")
        client.fullName = readLine()!!

        return client.fullName.isNotEmpty()
    }

    fun updateClientDetails(client: ClientModel) {
        println()
        println(
            "\n" +
                    "█░█ █▀█ █▀▄ ▄▀█ ▀█▀ █▀▀   █▀▀ █░░ █ █▀▀ █▄░█ ▀█▀\n" +
                    "█▄█ █▀▀ █▄▀ █▀█ ░█░ ██▄   █▄▄ █▄▄ █ ██▄ █░▀█ ░█░" + "\n"
        )

        println("\n" + "Current Client Name: " + client.fullName)
        println("Enter New Client Name: ")
        val name = readLine()!!
        if (name.isNotEmpty())
            client.fullName = name
        else
            println("Client Name ${client.fullName} Unchanged" + "\n")

        println("\n" + "Current Client Email Address: " + client.emailAddress)
        println("Enter New Client Email Address: ")
        val email = readLine()!!
        if (email.isNotEmpty())
            client.emailAddress = email
        else
            println("Client Email Address ${client.emailAddress} Unchanged")

        println("\n" + "Current Client Phone Number: " + client.phoneNumber)
        println("Enter New Client Phone Number: ")
        val phoneNumber = readLine()!!
        if (phoneNumber.isNotEmpty())
            client.phoneNumber = phoneNumber.toLong()
        else
            println("Client Phone Number ${client.phoneNumber} Unchanged" + "\n")

        println("Client ${client.fullName} Updated Successfully")
    }
}