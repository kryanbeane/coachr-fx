package org.kryanbeane.coachr.console.controllers

import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
import tornadofx.*

class ClientUIController: Controller() {
    private val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
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
    fun createClient(nameEntry: String, emailEntry: String, phoneEntry: String): Int {
        // Phone number validation
        val phoneNumber = validatePhoneNumber(phoneEntry)

        // Validate new client details and if they're okay, create a new client
        return if (!emailIsValid(emailEntry))
            1
        else if (validatePhoneNumber(phoneEntry) == null)
            2
        else {
            if (clients.createClient(ClientModel(fullName = nameEntry, emailAddress = emailEntry, phoneNumber = phoneNumber!!)))
                0
            else
                3
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
     * Update client details
     *
     * @param oldClient old client details
     * @param updatedNameEntry updated client name
     * @param updatedEmailEntry updated client email
     * @param updatedPhoneEntry updated client phone number
     * @return 0 if client was updated successfully, 1 if not
     */
    fun updateClient(oldClient: ClientModel?, updatedNameEntry: String?, updatedEmailEntry: String?, updatedPhoneEntry: String?): Int {
        if (oldClient != null) {
            val newClient = oldClient.copy()

            // Update client details after validation
            if (updatedNameEntry!!.isNotEmpty())
                newClient.fullName = updatedNameEntry

            // Email validation
            if (updatedEmailEntry!!.isNotEmpty()) {
                if (emailIsValid(updatedEmailEntry))
                    newClient.emailAddress = updatedEmailEntry
                else
                    return 1
            }

            // Number validation
            if (updatedPhoneEntry!!.isNotEmpty()) {
                val newNum = validatePhoneNumber(updatedPhoneEntry)

                if (newNum != null)
                    newClient.phoneNumber = newNum
                else
                    return 2
            }

            return if (clients.updateClientDetails(oldClient.fullName, newClient))
                0
            else
                3
        }
        return 3
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
    private fun validatePhoneNumber(phoneNumber: String?): Long? {
        if (phoneNumber != null) {
            return if (phoneNumber.length in 10..17 && phoneNumber.all { it.isDigit() })
                phoneNumber.toLong()
            else
                null
        }
        return null    }
}