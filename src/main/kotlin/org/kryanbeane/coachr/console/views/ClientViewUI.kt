package org.kryanbeane.coachr.console.views

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Pos
import org.kryanbeane.coachr.console.controllers.ClientUIController
import org.kryanbeane.coachr.console.models.ClientModel
import org.controlsfx.control.Notifications
import tornadofx.*
import javafx.scene.control.TextField

class MainClientUI : View() {
    private val controller: ClientUIController by inject()
    private var fullNameField: TextField = textfield()
    private var emailAddressField: TextField = textfield()
    private var phoneNumberField: TextField = textfield()
    private var phonePrefixField: TextField = textfield()
    private var currentClient: ClientModel? = null

    private var clientTable = tableview() {
        columnResizePolicy = SmartResize.POLICY
        column("Name", ClientModel::fullName)
        column("Email", ClientModel::emailAddress)
        column("Phone", ClientModel::phoneNumber)
    }

    override val root = borderpane {
        prefWidth = 823.0

        top = menubar {
            style {
                alignment = Pos.CENTER_LEFT
                padding = box(5.px)
            }
            menu().graphic = imageview("coachr-logo.png") {
                fitHeight = 50.0
                fitWidth = 150.0
            }
            menu("Clients") {
                item("Edit").action {
                    replaceWith<MainClientUI>()
                }
                item("List and Search").action {
                    replaceWith<ClientListUI>()
                }
            }
            menu("Workouts") {
                item("Edit").action {

                }
                item("List and Search").action {

                }
            }
        }

        left = form {

            fieldset("Client Information", FontAwesomeIconView(FontAwesomeIcon.USER)) {
                field("Full Name") {
                    fullNameField = textfield()
                    fullNameField.maxWidth = 175.0
                }
                field("Email Address") {
                    emailAddressField = textfield()
                    emailAddressField.maxWidth = 175.0
                }
                field("Code / Number") {
                    phonePrefixField = textfield()
                    phonePrefixField.maxWidth = 40.0
                    phoneNumberField = textfield()
                    phoneNumberField.maxWidth = 125.0
                }
            }

            vbox {

                button("Create") {
                    vboxConstraints {
                        fitToParentWidth()
                        maxWidth = 270.0
                        marginTop = 4.0
                        marginBottom = 4.0
                        marginLeft = 12.0
                        marginRight = 12.0
                        alignment = Pos.CENTER
                    }

                    setOnAction {
                        // Create the new client
                        val createResponseCode = controller.createClient(
                            fullNameField.text.toString(),
                            emailAddressField.text.toString(),
                            phonePrefixField.text.toString() + phoneNumberField.text.toString(),
                        )

                        when (createResponseCode) {
                            0 -> Notifications.create().title("Client Creation Info").text("Client created successfully!").owner(this).showInformation()
                            1 -> {
                                Notifications.create().title("Client Creation Error").text("Please enter a valid email address!").owner(this).showError()
                                emailAddressField.clear()
                            }
                            2 -> {
                                Notifications.create().title("Client Creation Error").text("Please enter a valid phone number!").owner(this).showError()
                                phoneNumberField.clear()
                                phonePrefixField.clear()
                            }
                            3 -> Notifications.create().title("Client Creation Error").text("An error occurred while creating the client!").owner(this).showError()
                        }

                        if (createResponseCode == 0) {
                            // Clear text fields
                            fullNameField.clear()
                            emailAddressField.clear()
                            phoneNumberField.clear()
                            phonePrefixField.clear()
                        }

                        // Update client table
                        clientTable.items = controller.retrieveAllClients().asObservable()
                    }
                }

                button("Update") {
                    vboxConstraints {
                        fitToParentWidth()
                        maxWidth = 270.0
                        marginTop = 4.0
                        marginBottom = 4.0
                        marginLeft = 12.0
                        marginRight = 12.0
                        alignment = Pos.CENTER
                    }

                    // TODO Update func
                }

                button("Delete") {
                    vboxConstraints {
                        fitToParentWidth()
                        maxWidth = 270.0
                        marginTop = 4.0
                        marginBottom = 4.0
                        marginLeft = 12.0
                        marginRight = 12.0
                        alignment = Pos.CENTER
                    }
                    setOnAction {
                        if (clientTable.selectedItem != null) {
                            if (controller.deleteClient(clientTable.selectedItem!!)) {
                                // Send notification to confirm client deletion
                                Notifications.create()
                                    .title("Client Deletion")
                                    .text("Client ${clientTable.selectedItem!!.fullName} successfully deleted!")
                                    .owner(this)
                                    .showInformation()
                                // Update client table
                                clientTable.items = controller.retrieveAllClients().asObservable()
                            } else {
                                // Send notification to say client deletion failed
                                Notifications.create()
                                    .title("Client Deletion")
                                    .text("Client deletion failed!")
                                    .owner(this)
                                    .showError()
                            }
                        }
                    }
                }

                button("Clear") {
                    vboxConstraints {
                        fitToParentWidth()
                        maxWidth = 270.0
                        marginTop = 4.0
                        marginBottom = 4.0
                        marginLeft = 12.0
                        marginRight = 12.0
                        alignment = Pos.CENTER
                    }
                    setOnAction {
                        // Clear text fields
                        fullNameField.clear()
                        emailAddressField.clear()
                        phoneNumberField.clear()
                        phonePrefixField.clear()
                    }
                }
            }
        }

        // Init client table
        clientTable.items = controller.retrieveAllClients().asObservable()
        center = clientTable
    }
}

class ClientListUI : View() {
    private val controller: ClientUIController by inject()

    override val root = borderpane {
        prefWidth = 823.0

        top = menubar {
            style {
                alignment = Pos.CENTER_LEFT
                padding = box(5.px)
            }
            menu().graphic = imageview("coachr-logo.png") {
                fitHeight = 50.0
                fitWidth = 150.0
            }
            menu("Clients") {
                item("Edit").action {
                    replaceWith<MainClientUI>()
                }
                item("List and Search").action {
                    replaceWith<ClientListUI>()
                }
            }
            menu("Workouts") {
                item("Edit").action {

                }
                item("List and Search").action {

                }
            }
        }

        center = tableview {
            items = controller.retrieveAllClients().asObservable()

            column("Full Name", ClientModel::fullName)
            column("Email Address", ClientModel::emailAddress)
            column("Phone Number", ClientModel::phoneNumber)
        }
    }
}

