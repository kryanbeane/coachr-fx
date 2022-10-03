package org.kryanbeane.coachr.console.controllers

import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
import tornadofx.*

class ClientUIController: Controller() {
    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    val clients = ClientMemStore(
        false,
        "coachr-clients-db",
        "coach-clients",
    )

    fun retrieveAllClients(): ArrayList<ClientModel> {
        return clients.findAllClients()
    }

    /**
     * Create new client from user input
     *
     * @param nameEntry client name
     * @param emailEntry client email
     * @param phoneEntry client phone number
     * @return true if client was created successfully, false otherwise
     */
    fun createClient(nameEntry: String, emailEntry: String, phoneEntry: String): Boolean {
        // Phone number validation
        val phoneNumber = phoneNumberIsValid(phoneEntry)

        // Validate new client details and if they're okay, create a new client
        return if (phoneEntry.length < 10 || phoneNumber == null || !emailIsValid(emailEntry)) {
            false
        } else {
            clients.createClient(
                ClientModel(
                    fullName = nameEntry,
                    emailAddress = emailEntry,
                    phoneNumber = phoneNumber,
                )
            )
        }
    }

    /**
     * Delete client from database
     *
     * @param client client to delete
     * @return true if client was deleted successfully, false otherwise
     */
    fun deleteClient(client: ClientModel): Boolean {
        return clients.deleteClient(client)
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
    private fun phoneNumberIsValid(phoneNumber: String): Long? {
        return if (phoneNumber.length in 10..17 && phoneNumber.all { it.isDigit() })
            phoneNumber.toLong()
        else
            null
    }
}