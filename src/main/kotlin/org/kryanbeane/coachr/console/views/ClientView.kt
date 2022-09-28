package org.kryanbeane.coachr.console.views

import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
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
                    "░╚════╝░░╚════╝░╚═╝░░╚═╝░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝"

        )
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

    /**
     * edit workout plan menu with add workout, edit workout, delete workout
     *
     * @return user option
     */
    fun editWorkoutPlanMenuView(): Int {
        println(
            "1. Add Workout to Plan" + "\n" +
            "2. Edit a Workout" + "\n" +
            "3. Delete a Workout" + "\n" +
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
     * edit workout menu with add exercise to workout, edit exercise, delete exercise
     *
     * @return user option
     */
    fun editWorkoutMenuView(): Int {
        println(
            "1. Add Exercise to Workout" + "\n" +
            "2. Edit an Exercise" + "\n" +
            "3. Delete an Exercise" + "\n" +
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

    fun searchOrListMenu(): Int {
        println(
            "1. Add Exercise to Workout" + "\n" +
            "2. Edit an Exercise" + "\n" +
            "3. Delete an Exercise" + "\n" +
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

    fun listClients(clients: ClientMemStore) {
        println("List All Clients" + "\n" + "\n")
        clients.logClients()
        println()
    }

    fun clientDataIsValid(newClient: ClientModel): Boolean {
        println()
        println("Enter Client Name: ")
        newClient.fullName = readLine()!!
        println("Enter Client Email Address: ")
        newClient.emailAddress = readLine()!!
        println("Enter Client Phone Number: ")
        newClient.phoneNumber = readLine()!!.toLong()

        return newClient.fullName.isNotEmpty() && newClient.emailAddress.isNotEmpty() && newClient.phoneNumber.toString().isNotEmpty()
    }

    fun workoutDataIsValid(newWorkout: WorkoutModel): Boolean {
        println()
        println("Enter Workout Name: ")
        newWorkout.name = readLine()!!
        println("Enter Workout Type: ")
        newWorkout.type = readLine()!!

        return newWorkout.name.isNotEmpty() && newWorkout.type.isNotEmpty()
    }



}