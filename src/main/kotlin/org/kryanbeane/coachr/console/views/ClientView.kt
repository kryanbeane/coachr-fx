package org.kryanbeane.coachr.console.views

import org.kryanbeane.coachr.console.models.ClientModel


class ClientView {
    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    /**
     * main menu with add clients, client menu
     *
     * @return user option
     */
    fun mainMenuView(): Int {
        println(
            "\n" +
                    "█▀▄▀█ ▄▀█ █ █▄░█   █▀▄▀█ █▀▀ █▄░█ █░█\n" +
                    "█░▀░█ █▀█ █ █░▀█   █░▀░█ ██▄ █░▀█ █▄█"
        )
        println()
        println(
            "1. Client Menu" + "\n" +
                    "2. View All" + "\n" +
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
            "1. Add a Client" + "\n" +
                    "2. Edit a Client" + "\n" +
                    "3. Delete a Client" + "\n" +
                    "4. View Clients" + "\n" +
                    "5. Go Back" + "\n" +
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
                    "2. Edit Workout Plan" + "\n" +
                    "3. View Workout Plan" + "\n" +
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
     * search or list menu with search clients, list clients
     *
     * @return user option
     */
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

    /**
     * validate new client details based on user input
     *
     * @param newClient
     * @return true if valid details false if not
     */
    fun newClientDetailsAreValid(newClient: ClientModel): Boolean {
        println(
            "\n" + "\n" +
                    "▄▀█ █▀▄ █▀▄   █▀▀ █░░ █ █▀▀ █▄░█ ▀█▀\n" +
                    "█▀█ █▄▀ █▄▀   █▄▄ █▄▄ █ ██▄ █░▀█ ░█░" + "\n"
        )
        println("Enter Client Name: ")
        newClient.fullName = readLine()!!

        // Includes email address validation using regex
        println("Enter Client Email Address: ")
        val email = readLine()!!
        if (emailIsValid(email))
            newClient.emailAddress = email
        else {
            println("Invalid Email Address")
            return false
        }

        // Includes phone number validation
        println("Enter Phone Number: ")
        val number = readLine()!!
        if (phoneNumberIsValid(number))
            newClient.phoneNumber = number.toLong()
        else {
            println("Invalid Phone Number")
            return false
        }

        return newClient.fullName.isNotEmpty() && newClient.emailAddress.isNotEmpty() && newClient.phoneNumber.toString().isNotEmpty()
    }

    /**
     * validate client name based on user input
     *
     * @param client
     * @return
     */
    fun clientNameIsValid(client: ClientModel): Boolean {
        print("\n" + "Enter Client Name: ")
        client.fullName = readLine()!!

        return client.fullName.isNotEmpty()
    }

    /**
     * updates client details based on user input & validates email address and phone number
     *
     * @param client to be updated
     */
    fun updateClientDetails(client: ClientModel): ClientModel {
        println()
        println(
            "\n" +
                    "█░█ █▀█ █▀▄ ▄▀█ ▀█▀ █▀▀   █▀▀ █░░ █ █▀▀ █▄░█ ▀█▀\n" +
                    "█▄█ █▀▀ █▄▀ █▀█ ░█░ ██▄   █▄▄ █▄▄ █ ██▄ █░▀█ ░█░" + "\n"
        )

        println("\n" + "Current Client Name: " + client.fullName)
        println("Enter New Name: ")
        val name = readLine()!!
        if (name.isNotEmpty())
            client.fullName = name
        else
            println("Client Name ${client.fullName} Unchanged" + "\n")

        // Check for empty email address, then check for valid email address format
        println("\n" + "Current Client Email Address: " + client.emailAddress)
        println("Enter New Email Address: ")
        val email = readLine()!!
        if (email.isEmpty())
            println("Client Email Address ${client.emailAddress} Unchanged" + "\n")
        else if (emailIsValid(email))
            client.emailAddress = email
        else
            println("Invalid Email Address, Email ${client.emailAddress} Unchanged" + "\n")

        // Check for valid phone number
        println("\n" + "Current Client Phone Number: " + client.phoneNumber)
        println("Enter New Phone Number: ")
        val phoneNumber = readLine()!!
        if (phoneNumberIsValid(phoneNumber))
            client.phoneNumber = phoneNumber.toLong()
        else
        println("Client Phone Number ${client.phoneNumber} Unchanged" + "\n")
        println()
        println("Client ${client.fullName} Updated")
        return client
    }

    /**
     * validate email address based on regex pattern to include the pattern <text>@<text>.<text>
     *
     * @reference https://roytuts.com/validate-email-address-with-regular-expression-using-kotlin/
     * @param email to validate
     * @return true if valid email false if not
     */
    private fun emailIsValid(email: String): Boolean {
        return emailRegex.toRegex().matches(email)
    }

    /**
     * validate phone number
     *
     * @param phoneNumber to validate
     * @return true if valid phone number false if not
     */
    private fun phoneNumberIsValid(phoneNumber: String): Boolean {
        return phoneNumber.length in 10..17
    }
}