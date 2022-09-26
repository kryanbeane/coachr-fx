package org.kryanbeane.coachr.console.views

class ClientView {

    fun authMenu(): Int {
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
            "1. Log in" + "\n" +
            "2. Sign up" + "\n" +
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

    fun mainMenu(): Int {
        println(
            "1. Add a Client" + "\n" +
            "2. Client Menu" + "\n" +
            "3. Log out" + "\n" +
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

    fun clientMenu(): Int {
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

    fun viewClientsMenu(): Int {
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

    fun editClientMenu(): Int {
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

    fun editWorkoutPlanMenu(): Int {
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


    fun editWorkoutMenu(): Int {
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

}