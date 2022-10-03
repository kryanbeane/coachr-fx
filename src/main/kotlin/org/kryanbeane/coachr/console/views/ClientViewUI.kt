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
    private var fullNameField: TextField? = null
    private var emailAddressField: TextField? = null
    private var phoneNumberField: TextField? = null
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
                    id = "fullName"
                    fullNameField = textfield()
                }
                field("Email Address") {
                    id = "emailAddress"
                    emailAddressField = textfield()
                }
                field("Phone Number") {
                    id = "phoneNumber"
                    phoneNumberField = textfield()
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
                        if (controller.createClient(
                                fullNameField?.text.toString(),
                                emailAddressField?.text.toString(),
                                phoneNumberField?.text.toString()
                            )
                        ) {
                            // Send notification to say confirm client creation
                            Notifications.create()
                                .title("Client Creation")
                                .text("Client ${fullNameField?.text.toString()} successfully created!")
                                .owner(this)
                                .showInformation()
                            // Clear text fields
                            fullNameField?.clear()
                            emailAddressField?.clear()
                            phoneNumberField?.clear()
                            // Update client table
                            clientTable.items = controller.retrieveAllClients().asObservable()
                        }
                        // Send notification to say client creation failed
                        else {
                            Notifications.create()
                                .title("Client Creation")
                                .text("Client creation failed!")
                                .owner(this)
                                .showError()
                        }
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
                        fullNameField?.clear()
                        emailAddressField?.clear()
                        phoneNumberField?.clear()
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

